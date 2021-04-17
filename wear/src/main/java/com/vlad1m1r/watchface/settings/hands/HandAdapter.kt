package com.vlad1m1r.watchface.settings.hands

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.config.HOURS_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.MINUTES_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.SECONDS_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.base.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsWithSwitchViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder
import java.lang.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_COLOR_HAND = 1
private const val TYPE_SMOOTH_HAND = 2

class HandAdapter(
    private val colorStorage: ColorStorage,
    private val dataStorage: DataStorage,
    private val handType: HandType,
    @StringRes private val title: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            TYPE_COLOR_HAND -> viewHolder =
                ColorPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_text,
                            parent,
                            false
                        )
                )
            TYPE_SMOOTH_HAND -> viewHolder =
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
        return if(handType == HandType.SECONDS) 3 else 2
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_COLOR_HAND
            2 -> TYPE_SMOOTH_HAND
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    title
                )
            TYPE_COLOR_HAND ->
                when(handType) {
                    HandType.HOURS -> (viewHolder as ColorPickerViewHolder).setData(
                        R.string.wear_hand_color,
                        HOURS_HAND_COLOR_PICKER_REQUEST_CODE,
                        colorStorage.getHoursHandColor(),
                        true
                    )
                    HandType.MINUTES -> (viewHolder as ColorPickerViewHolder).setData(
                        R.string.wear_hand_color,
                        MINUTES_HAND_COLOR_PICKER_REQUEST_CODE,
                        colorStorage.getMinutesHandColor(),
                        true
                    )
                    HandType.SECONDS -> (viewHolder as ColorPickerViewHolder).setData(
                        R.string.wear_hand_color,
                        SECONDS_HAND_COLOR_PICKER_REQUEST_CODE,
                        colorStorage.getSecondsHandColor(),
                        true
                    )
                }

            TYPE_SMOOTH_HAND ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_smooth_seconds_hand,
                    dataStorage.hasSmoothSecondsHand()
                ) {
                    dataStorage.setHasSmoothSecondsHand(it)
                }
        }
    }
}