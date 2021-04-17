package com.vlad1m1r.watchface.settings.ticks

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.config.HOUR_TICKS_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.MINUTE_TICKS_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.base.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsWithSwitchViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder
import com.vlad1m1r.watchface.settings.ticks.viewholders.TicksLayoutPickerViewHolder
import java.lang.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_TICKS_AMBIENT_MODE = 1
private const val TYPE_TICKS_INTERACTIVE_MODE = 2
private const val TYPE_HOUR_TICKS_COLOR = 3
private const val TYPE_MINUTE_TICKS_COLOR = 4
private const val TYPE_TICKS_LAYOUT_PICKER = 5
private const val TYPE_ADJUST_TO_SQUARE_SCREEN = 6

class TicksAdapter(
    private val res: Resources,
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage,
    @StringRes private val title: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var ticksLayoutPickerViewHolder: TicksLayoutPickerViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            TYPE_TITLE -> viewHolder =
                TitleViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_title,
                            parent,
                            false
                        )
                )
            TYPE_TICKS_LAYOUT_PICKER -> {
                ticksLayoutPickerViewHolder = TicksLayoutPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_ticks_layout_picker,
                            parent,
                            false
                        ),
                    dataStorage
                )
                viewHolder = ticksLayoutPickerViewHolder
            }
            TYPE_TICKS_INTERACTIVE_MODE,
            TYPE_TICKS_AMBIENT_MODE,
            TYPE_ADJUST_TO_SQUARE_SCREEN -> viewHolder = SettingsWithSwitchViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_switch,
                        parent,
                        false
                    )
            )

            TYPE_HOUR_TICKS_COLOR,
            TYPE_MINUTE_TICKS_COLOR -> viewHolder =
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
        return if(res.configuration.isScreenRound) 6 else 7
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_TICKS_LAYOUT_PICKER
            2 -> TYPE_TICKS_AMBIENT_MODE
            3 -> TYPE_TICKS_INTERACTIVE_MODE
            4 -> TYPE_HOUR_TICKS_COLOR
            5 -> TYPE_MINUTE_TICKS_COLOR
            6 -> TYPE_ADJUST_TO_SQUARE_SCREEN
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when(viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    title
                )
            TYPE_TICKS_AMBIENT_MODE ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_ticks_in_ambient_mode,
                    dataStorage.hasTicksInAmbientMode()
                ) {
                    dataStorage.setHasTicksInAmbientMode(it)
                }
            TYPE_TICKS_INTERACTIVE_MODE ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_ticks_in_interactive_mode,
                    dataStorage.hasTicksInInteractiveMode()
                ) {
                    dataStorage.setHasTicksInInteractiveMode(it)
                }
            TYPE_HOUR_TICKS_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_hour_ticks_color,
                    HOUR_TICKS_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getHourTicksColor(),
                    false
                )
            TYPE_MINUTE_TICKS_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_minute_ticks_color,
                    MINUTE_TICKS_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getMinuteTicksColor(),
                    false
                )
            TYPE_ADJUST_TO_SQUARE_SCREEN ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_ticks_adjust_to_square_screen,
                    dataStorage.shouldAdjustToSquareScreen()
                ) {
                    dataStorage.setShouldAdjustToSquareScreen(it)
                }
        }
    }

    fun updateWatchFacePicker() {
        if (::ticksLayoutPickerViewHolder.isInitialized) {
            ticksLayoutPickerViewHolder.refreshImage()
        }
    }
}