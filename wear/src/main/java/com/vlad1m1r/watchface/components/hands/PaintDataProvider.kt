package com.vlad1m1r.watchface.components.hands

import android.content.Context
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage

class PaintDataProvider(
    private val context: Context,
    private val colorStorage: ColorStorage
) {
    private val middleCircleRadius = context.resources.getDimension(R.dimen.middle_circle_radius)
    private val circleHandGap = context.resources.getDimension(R.dimen.circle_hand_gap)

    private val ambientColor = ContextCompat.getColor(context, R.color.watch_ambient)

    private val shadowColor = ContextCompat.getColor(context, R.color.watch_shadow)
    private val shadowRadius = context.resources.getDimension(R.dimen.shadow_radius)

    fun getHourHandData(): HandData {
        val hourColor = colorStorage.getHoursHandColor()
        val handWidthHour = context.resources.getDimension(R.dimen.hand_width_hour)
        return HandData(
            hourColor,
            ambientColor,
            shadowColor,
            shadowRadius,
            handWidthHour,
            middleCircleRadius + circleHandGap,
            0.5f
        )
    }

    fun getMinuteHandData(): HandData {
        val minuteColor = colorStorage.getMinutesHandColor()
        val handWidthMinute = context.resources.getDimension(R.dimen.hand_width_minute)
        return HandData(
            minuteColor,
            ambientColor,
            shadowColor,
            shadowRadius,
            handWidthMinute,
            middleCircleRadius + circleHandGap,
            0.75f
        )
    }

    fun getSecondHandData(): HandData {
        val secondColor = colorStorage.getSecondsHandColor()
        val handWidthSecond = context.resources.getDimension(R.dimen.hand_width_second)
        return HandData(
            secondColor,
            ambientColor,
            shadowColor,
            shadowRadius,
            handWidthSecond,
            middleCircleRadius,
            0.875f
        )
    }

    fun getCircleData(): CircleData {
        val circleColor = colorStorage.getCentralCircleColor()
        val handWidthSecond = context.resources.getDimension(R.dimen.hand_width_second)
        return CircleData(circleColor, ambientColor, shadowColor, shadowRadius, handWidthSecond, middleCircleRadius)
    }
}