package com.vlad1m1r.watchface.components.hands

import android.content.Context
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.SizeStorage

class GetHandData(
    private val context: Context,
    private val colorStorage: ColorStorage,
    private val sizeStorage: SizeStorage
) {

    private val circleHandGap = context.resources.getDimensionPixelSize(R.dimen.circle_hand_gap)
    private val ambientColor = ContextCompat.getColor(context, R.color.watch_ambient)
    private val shadowColor = ContextCompat.getColor(context, R.color.watch_shadow)
    private val shadowRadius = context.resources.getDimensionPixelSize(R.dimen.shadow_radius)

    fun getHourHandData(): HandData {
        val hourColor = colorStorage.getHoursHandColor()
        val handWidthHour = sizeStorage.getHoursHandWidth()
        return HandData(
            hourColor,
            ambientColor,
            shadowColor,
            shadowRadius,
            handWidthHour,
            sizeStorage.getCircleRadius() + circleHandGap,
            sizeStorage.getHoursHandScale()
        )
    }

    fun getMinuteHandData(): HandData {
        val minuteColor = colorStorage.getMinutesHandColor()
        val handWidthMinute = sizeStorage.getMinutesHandWidth()
        return HandData(
            minuteColor,
            ambientColor,
            shadowColor,
            shadowRadius,
            handWidthMinute,
            sizeStorage.getCircleRadius() + circleHandGap,
            sizeStorage.getMinutesHandScale()
        )
    }

    fun getSecondHandData(): HandData {
        val secondColor = colorStorage.getSecondsHandColor()
        val handWidthSecond = sizeStorage.getSecondsHandWidth()
        return HandData(
            secondColor,
            ambientColor,
            shadowColor,
            shadowRadius,
            handWidthSecond,
            sizeStorage.getCircleRadius(),
            sizeStorage.getSecondsHandScale()
        )
    }

    fun getCircleData(): CircleData {
        val circleColor = colorStorage.getCentralCircleColor()
        val circleWidth = sizeStorage.getCircleWidth()
        val middleCircleRadius = sizeStorage.getCircleRadius()
        return CircleData(circleColor, ambientColor, shadowColor, shadowRadius, circleWidth, middleCircleRadius)
    }
}