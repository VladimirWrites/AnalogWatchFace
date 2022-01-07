package com.vlad1m1r.watchface.settings.hands.hand

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.viewholders.*

private const val TYPE_TITLE = 0
private const val TYPE_PREVIEW = 1
private const val TYPE_COLOR_HAND = 2
private const val TYPE_HAND_WIDTH = 3
private const val TYPE_HAND_SCALE = 4
private const val TYPE_SMOOTH_HAND = 5

class HandAdapter(
    private val colorStorage: ColorStorage,
    private val dataStorage: DataStorage,
    private val sizeStorage: SizeStorage,
    private val navigator: Navigator,
    private val handType: HandType,
    @StringRes private val title: Int,
    private val hoursHandColorLauncher: ActivityResultLauncher<Intent>,
    private val minutesHandColorLauncher: ActivityResultLauncher<Intent>,
    private val secondsHandColorLauncher: ActivityResultLauncher<Intent>
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
                    )
            )
            TYPE_COLOR_HAND -> ColorPickerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_text,
                        parent,
                        false
                    ),
                navigator
            )
            TYPE_HAND_WIDTH -> SettingsSliderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_slider,
                        parent,
                        false
                    )
            )
            TYPE_HAND_SCALE -> SettingsSliderScaleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_slider,
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
        return if (handType == HandType.SECONDS) 6 else 5
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_PREVIEW
            2 -> TYPE_COLOR_HAND
            3 -> TYPE_HAND_WIDTH
            4 -> TYPE_HAND_SCALE
            5 -> TYPE_SMOOTH_HAND
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
                    HandType.HOURS -> (viewHolder as ColorPickerViewHolder).bind(
                        R.string.wear_hand_color,
                        colorStorage.getHoursHandColor(),
                        true,
                        hoursHandColorLauncher
                    )
                    HandType.MINUTES -> (viewHolder as ColorPickerViewHolder).bind(
                        R.string.wear_hand_color,
                        colorStorage.getMinutesHandColor(),
                        true,
                        minutesHandColorLauncher
                    )
                    HandType.SECONDS -> (viewHolder as ColorPickerViewHolder).bind(
                        R.string.wear_hand_color,
                        colorStorage.getSecondsHandColor(),
                        true,
                        secondsHandColorLauncher
                    )
                }
            TYPE_HAND_WIDTH -> (viewHolder as SettingsSliderViewHolder).setData(
                R.string.wear_hand_width,
                when (handType) {
                    HandType.HOURS -> sizeStorage.getHoursHandWidth()
                    HandType.MINUTES -> sizeStorage.getMinutesHandWidth()
                    HandType.SECONDS -> sizeStorage.getSecondsHandWidth()
                },
                1,
                when (handType) {
                    HandType.HOURS -> 20
                    HandType.MINUTES -> 20
                    HandType.SECONDS -> 10
                }
            ) { handWidth ->
                when (handType) {
                    HandType.HOURS -> sizeStorage.setHoursHandWidth(handWidth)
                    HandType.MINUTES -> sizeStorage.setMinutesHandWidth(handWidth)
                    HandType.SECONDS -> sizeStorage.setSecondsHandWidth(handWidth)
                }
                notifyDataSetChanged()
            }
            TYPE_HAND_SCALE -> (viewHolder as SettingsSliderScaleViewHolder).setData(
                R.string.wear_hand_scale,
                when (handType) {
                    HandType.HOURS -> sizeStorage.getHoursHandScale()
                    HandType.MINUTES -> sizeStorage.getMinutesHandScale()
                    HandType.SECONDS -> sizeStorage.getSecondsHandScale()
                },
                99
            ) { handScale ->
                when (handType) {
                    HandType.HOURS -> sizeStorage.setHoursHandScale(handScale)
                    HandType.MINUTES -> sizeStorage.setMinutesHandScale(handScale)
                    HandType.SECONDS -> sizeStorage.setSecondsHandScale(handScale)
                }
                notifyDataSetChanged()
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