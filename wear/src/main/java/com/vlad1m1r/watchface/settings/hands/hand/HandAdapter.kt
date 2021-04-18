package com.vlad1m1r.watchface.settings.hands.hand

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.settings.HOURS_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.MINUTES_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.SECONDS_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.base.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsWithSwitchViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.WatchPreviewViewHolder


private const val TYPE_TITLE = 0
private const val TYPE_PREVIEW = 1
private const val TYPE_COLOR_HAND = 2
private const val TYPE_SMOOTH_HAND = 3

class HandAdapter(
    private val colorStorage: ColorStorage,
    private val dataStorage: DataStorage,
    private val handType: HandType,
    @StringRes private val title: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_title,
                        parent,
                        false
                    )
            )
            TYPE_PREVIEW -> WatchPreviewViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_hand_preview,
                        parent,
                        false
                    ),
                colorStorage,
                dataStorage
            )
            TYPE_COLOR_HAND -> ColorPickerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_text,
                        parent,
                        false
                    )
            )
            TYPE_SMOOTH_HAND -> SettingsWithSwitchViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_switch,
                        parent,
                        false
                    )
            )
            else -> {
                throw IllegalArgumentException("viewType: $viewType is not supported")
            }
        }
    }

    override fun getItemCount(): Int {
        return if (handType == HandType.SECONDS) 4 else 3
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_PREVIEW
            2 -> TYPE_COLOR_HAND
            3 -> TYPE_SMOOTH_HAND
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE -> (viewHolder as TitleViewHolder).bind(
                title
            )
            TYPE_PREVIEW -> {
                val context = viewHolder.itemView.context
                val width = (context as Activity).window.decorView.width.toFloat()
                val height = context.resources.getDimension(R.dimen.item_watch_preview_height)
                (viewHolder as WatchPreviewViewHolder).bind(
                    Point(width / 2, height / 2),
                )
            }
            TYPE_COLOR_HAND ->
                when (handType) {
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