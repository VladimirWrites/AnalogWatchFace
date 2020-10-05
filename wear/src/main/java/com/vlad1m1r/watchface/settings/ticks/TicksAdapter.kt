package com.vlad1m1r.watchface.settings.ticks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.config.HOUR_TICKS_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.MINUTE_TICKS_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.ticks.viewholders.TicksAmbientViewHolder
import com.vlad1m1r.watchface.settings.ticks.viewholders.TicksInteractiveViewHolder
import com.vlad1m1r.watchface.settings.ticks.viewholders.TicksLayoutPickerViewHolder
import java.lang.IllegalArgumentException

const val TYPE_TICKS_AMBIENT_MODE = 0
const val TYPE_TICKS_INTERACTIVE_MODE = 1
const val TYPE_HOUR_TICKS_COLOR = 2
const val TYPE_MINUTE_TICKS_COLOR = 3
const val TYPE_TICKS_LAYOUT_PICKER = 4

class TicksAdapter(private val dataStorage: DataStorage, private val colorStorage: ColorStorage) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var ticksLayoutPickerViewHolder: TicksLayoutPickerViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            TYPE_TICKS_LAYOUT_PICKER -> {
                ticksLayoutPickerViewHolder = TicksLayoutPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_pick_watch_face,
                            parent,
                            false
                        ),
                    dataStorage
                )
                viewHolder = ticksLayoutPickerViewHolder
            }
            TYPE_TICKS_AMBIENT_MODE -> viewHolder = TicksAmbientViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_switch,
                        parent,
                        false
                    ),
                dataStorage
            )

            TYPE_TICKS_INTERACTIVE_MODE -> viewHolder =
                TicksInteractiveViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_switch,
                            parent,
                            false
                        ),
                    dataStorage
                )
            TYPE_HOUR_TICKS_COLOR, TYPE_MINUTE_TICKS_COLOR -> viewHolder =
                ColorPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_text,
                            parent,
                            false
                        )
                )
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TICKS_LAYOUT_PICKER
            1 -> TYPE_TICKS_AMBIENT_MODE
            2 -> TYPE_TICKS_INTERACTIVE_MODE
            3 -> TYPE_HOUR_TICKS_COLOR
            4 -> TYPE_MINUTE_TICKS_COLOR
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when(viewHolder.itemViewType) {
            TYPE_HOUR_TICKS_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.hour_ticks_color,
                    HOUR_TICKS_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getHourTicksColor(),
                    false
                )
            TYPE_MINUTE_TICKS_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.minute_ticks_color,
                    MINUTE_TICKS_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getMinuteTicksColor(),
                    false
                )
        }
    }

    fun updateWatchFacePicker() {
        if (::ticksLayoutPickerViewHolder.isInitialized) {
            ticksLayoutPickerViewHolder.refreshImage()
        }
    }
}