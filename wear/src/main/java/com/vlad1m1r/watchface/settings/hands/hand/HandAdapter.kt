package com.vlad1m1r.watchface.settings.hands.hand

import android.content.Intent
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.viewholders.*
import com.vlad1m1r.watchface.utils.getActivityContext

private const val TYPE_TITLE = 0
private const val TYPE_PREVIEW = 1
private const val TYPE_COLOR_HAND = 2
private const val TYPE_HAND_WIDTH = 3
private const val TYPE_HAND_SCALE = 4
private const val TYPE_SMOOTH_HAND = 5

class HandAdapter(
    private val stateHolder: WatchFaceStateHolder,
    private val navigator: Navigator,
    private val handType: HandType,
    @StringRes private val title: Int,
    private val hoursHandColorLauncher: ActivityResultLauncher<Intent>,
    private val minutesHandColorLauncher: ActivityResultLauncher<Intent>,
    private val secondsHandColorLauncher: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(parent)
            TYPE_PREVIEW -> WatchPreviewViewHolder(parent)
            TYPE_COLOR_HAND -> ColorPickerViewHolder(parent, navigator)
            TYPE_HAND_WIDTH -> SettingsSliderViewHolder(parent)
            TYPE_HAND_SCALE -> SettingsSliderScaleViewHolder(parent)
            TYPE_SMOOTH_HAND -> SettingsWithSwitchViewHolder(parent)
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
                val activity = viewHolder.itemView.context.getActivityContext() as FragmentActivity
                val width = activity.window.decorView.width.toFloat()
                val height = activity.resources.getDimension(R.dimen.item_watch_preview_height)
                (viewHolder as WatchPreviewViewHolder).bind(
                    Point(width / 2, height / 2),
                    stateHolder.currentState
                )
            }
            TYPE_COLOR_HAND ->
                when (handType) {
                    HandType.HOURS -> (viewHolder as ColorPickerViewHolder).bind(
                        R.string.wear_hand_color,
                        stateHolder.currentState.handsState.hoursHand.color,
                        true,
                        hoursHandColorLauncher
                    )
                    HandType.MINUTES -> (viewHolder as ColorPickerViewHolder).bind(
                        R.string.wear_hand_color,
                        stateHolder.currentState.handsState.minutesHand.color,
                        true,
                        minutesHandColorLauncher
                    )
                    HandType.SECONDS -> (viewHolder as ColorPickerViewHolder).bind(
                        R.string.wear_hand_color,
                        stateHolder.currentState.handsState.secondsHand.color,
                        true,
                        secondsHandColorLauncher
                    )
                }
            TYPE_HAND_WIDTH -> (viewHolder as SettingsSliderViewHolder).setData(
                R.string.wear_hand_width,
                when (handType) {
                    HandType.HOURS -> stateHolder.currentState.handsState.hoursHand.width
                    HandType.MINUTES -> stateHolder.currentState.handsState.minutesHand.width
                    HandType.SECONDS -> stateHolder.currentState.handsState.secondsHand.width
                },
                1,
                when (handType) {
                    HandType.HOURS -> 20
                    HandType.MINUTES -> 20
                    HandType.SECONDS -> 10
                }
            ) { handWidth ->
                when (handType) {
                    HandType.HOURS -> {
                        val newHoursHandState =
                            stateHolder.currentState.handsState.hoursHand.copy(width = handWidth)
                        stateHolder.setHandsState(stateHolder.currentState.handsState.copy(hoursHand = newHoursHandState))
                    }
                    HandType.MINUTES -> {
                        val newMinutesHandState =
                            stateHolder.currentState.handsState.minutesHand.copy(width = handWidth)
                        stateHolder.setHandsState(
                            stateHolder.currentState.handsState.copy(
                                minutesHand = newMinutesHandState
                            )
                        )
                    }
                    HandType.SECONDS -> {
                        val newSecondsHandState =
                            stateHolder.currentState.handsState.secondsHand.copy(width = handWidth)
                        stateHolder.setHandsState(
                            stateHolder.currentState.handsState.copy(
                                secondsHand = newSecondsHandState
                            )
                        )
                    }
                }
                notifyDataSetChanged()
            }
            TYPE_HAND_SCALE -> (viewHolder as SettingsSliderScaleViewHolder).setData(
                R.string.wear_hand_scale,
                when (handType) {
                    HandType.HOURS -> stateHolder.currentState.handsState.hoursHand.lengthScale
                    HandType.MINUTES -> stateHolder.currentState.handsState.minutesHand.lengthScale
                    HandType.SECONDS -> stateHolder.currentState.handsState.secondsHand.lengthScale
                },
                99
            ) { handScale ->
                when (handType) {
                    HandType.HOURS -> {
                        val newHoursHandState =
                            stateHolder.currentState.handsState.hoursHand.copy(lengthScale = handScale)
                        stateHolder.setHandsState(stateHolder.currentState.handsState.copy(hoursHand = newHoursHandState))
                    }
                    HandType.MINUTES -> {
                        val newMinutesHandState =
                            stateHolder.currentState.handsState.minutesHand.copy(lengthScale = handScale)
                        stateHolder.setHandsState(
                            stateHolder.currentState.handsState.copy(
                                minutesHand = newMinutesHandState
                            )
                        )
                    }
                    HandType.SECONDS -> {
                        val newSecondsHandState =
                            stateHolder.currentState.handsState.secondsHand.copy(lengthScale = handScale)
                        stateHolder.setHandsState(
                            stateHolder.currentState.handsState.copy(
                                secondsHand = newSecondsHandState
                            )
                        )
                    }
                }
                notifyDataSetChanged()
            }
            TYPE_SMOOTH_HAND ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_smooth_seconds_hand,
                    stateHolder.currentState.handsState.hasSmoothSecondsHand
                ) { hasSmoothSecondsHand ->
                    val newHandsState =
                        stateHolder.currentState.handsState.copy(hasSmoothSecondsHand = hasSmoothSecondsHand)
                    stateHolder.setHandsState(newHandsState)
                }
        }
    }
}