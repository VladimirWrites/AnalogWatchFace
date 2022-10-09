package com.vlad1m1r.watchface.components.hands

import android.content.Context
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.state.HandsState
import com.vlad1m1r.watchface.data.state.WatchFaceState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetHandData @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val circleHandGap = context.resources.getDimensionPixelSize(R.dimen.circle_hand_gap)
    private val ambientColor = ContextCompat.getColor(context, R.color.watch_ambient)
    private val shadowColor = ContextCompat.getColor(context, R.color.watch_shadow)
    private val shadowRadius = context.resources.getDimensionPixelSize(R.dimen.shadow_radius)

    fun getHourHandData(state: HandsState): HandData {
        val hourColor = state.hoursHand.color
        val handWidthHour = state.hoursHand.width

        val ambientHourColor = if(hourColor.isColorTransparent() || state.shouldKeepHandColorInAmbientMode) { hourColor } else { ambientColor }

        return HandData(
            hourColor,
            ambientHourColor,
            shadowColor,
            shadowRadius,
            handWidthHour,
            state.circleState.radius + circleHandGap,
            state.hoursHand.lengthScale,
            state.useAntialiasingInAmbientMode
        )
    }

    fun getMinuteHandData(state: HandsState): HandData {
        val minuteColor = state.minutesHand.color
        val handWidthMinute = state.minutesHand.width

        val ambientMinuteColor = if(minuteColor.isColorTransparent() || state.shouldKeepHandColorInAmbientMode) { minuteColor } else { ambientColor }

        return HandData(
            minuteColor,
            ambientMinuteColor,
            shadowColor,
            shadowRadius,
            handWidthMinute,
            state.circleState.radius + circleHandGap,
            state.minutesHand.lengthScale,
            state.useAntialiasingInAmbientMode
        )
    }

    fun getSecondHandData(state: HandsState): HandData {
        val secondColor = state.secondsHand.color
        val handWidthSecond = state.secondsHand.width

        val ambientSecondColor = if(secondColor.isColorTransparent()  || state.shouldKeepHandColorInAmbientMode) { secondColor } else { ambientColor }

        return HandData(
            secondColor,
            ambientSecondColor,
            shadowColor,
            shadowRadius,
            handWidthSecond,
            state.circleState.radius,
            state.secondsHand.lengthScale,
            state.useAntialiasingInAmbientMode
        )
    }

    fun getCircleData(state: HandsState): CircleData {
        val circleColor = state.circleState.color
        val circleWidth =  state.circleState.width
        val middleCircleRadius =  state.circleState.radius

        val ambientCircleColor =
            if(state.circleState.hasInAmbientMode) {
                if (circleColor.isColorTransparent()  || state.shouldKeepHandColorInAmbientMode) {
                    circleColor
                } else {
                    ambientColor
                }
            } else {
                ContextCompat.getColor(context, R.color.transparent)
            }

        return CircleData(
            circleColor,
            ambientCircleColor,
            shadowColor,
            shadowRadius,
            circleWidth,
            middleCircleRadius,
            state.useAntialiasingInAmbientMode
        )
    }

    private fun Int.isColorTransparent(): Boolean = this == 0
}