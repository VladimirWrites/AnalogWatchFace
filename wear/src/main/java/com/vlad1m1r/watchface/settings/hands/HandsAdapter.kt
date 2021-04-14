package com.vlad1m1r.watchface.settings.hands

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.config.HOURS_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.MINUTES_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.SECONDS_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.CENTRAL_CIRCLE_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.config.viewholders.SettingsWithSwitchViewHolder
import java.lang.IllegalArgumentException

const val TYPE_COLOR_HOUR_HAND = 1
const val TYPE_COLOR_MINUTE_HAND = 2
const val TYPE_COLOR_SECOND_HAND = 3
const val TYPE_COLOR_CENTRAL_CIRCLE = 4
const val TYPE_SMOOTH_SECONDS_HAND = 5

class HandsAdapter(
    private val colorStorage: ColorStorage,
    private val dataStorage: DataStorage
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            TYPE_COLOR_HOUR_HAND,
            TYPE_COLOR_MINUTE_HAND,
            TYPE_COLOR_SECOND_HAND,
            TYPE_COLOR_CENTRAL_CIRCLE -> viewHolder =
                ColorPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_text,
                            parent,
                            false
                        )
                )
            TYPE_SMOOTH_SECONDS_HAND -> viewHolder =
                SettingsWithSwitchViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_switch,
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
            0 -> TYPE_COLOR_HOUR_HAND
            1 -> TYPE_COLOR_MINUTE_HAND
            2 -> TYPE_COLOR_SECOND_HAND
            3 -> TYPE_COLOR_CENTRAL_CIRCLE
            4 -> TYPE_SMOOTH_SECONDS_HAND
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_COLOR_HOUR_HAND ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_hours_hand_color,
                    HOURS_HAND_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getHoursHandColor(),
                    true
                )
            TYPE_COLOR_MINUTE_HAND ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_minutes_hand_color,
                    MINUTES_HAND_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getMinutesHandColor(),
                    true
                )
            TYPE_COLOR_SECOND_HAND ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_seconds_hand_color,
                    SECONDS_HAND_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getSecondsHandColor(),
                    true
                )
            TYPE_COLOR_CENTRAL_CIRCLE ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_central_circle_color,
                    CENTRAL_CIRCLE_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getCentralCircleColor(),
                    true
                )
            TYPE_SMOOTH_SECONDS_HAND ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_smooth_seconds_hand,
                    dataStorage.hasSmoothSecondsHand()
                ) {
                    dataStorage.setHasSmoothSecondsHand(it)
                }
        }
    }
}