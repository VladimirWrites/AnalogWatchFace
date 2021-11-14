package com.vlad1m1r.watchface.components.hands

import android.content.Context
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.SizeStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetHandData @Inject constructor(
    @ApplicationContext private val context: Context,
    private val colorStorage: ColorStorage,
    private val dataStorage: DataStorage,
    private val sizeStorage: SizeStorage
) {

    private val circleHandGap = context.resources.getDimensionPixelSize(R.dimen.circle_hand_gap)
    private val ambientColor = ContextCompat.getColor(context, R.color.watch_ambient)
    private val shadowColor = ContextCompat.getColor(context, R.color.watch_shadow)
    private val shadowRadius = context.resources.getDimensionPixelSize(R.dimen.shadow_radius)

    fun getHourHandData(): HandData {
        val hourColor = colorStorage.getHoursHandColor()
        val handWidthHour = sizeStorage.getHoursHandWidth()

        val ambientHourColor = if(hourColor.isColorTransparent()) { hourColor } else { ambientColor }

        return HandData(
            hourColor,
            ambientHourColor,
            shadowColor,
            shadowRadius,
            handWidthHour,
            sizeStorage.getCircleRadius() + circleHandGap,
            sizeStorage.getHoursHandScale(),
            dataStorage.useAntiAliasingInAmbientMode()
        )
    }

    fun getMinuteHandData(): HandData {
        val minuteColor = colorStorage.getMinutesHandColor()
        val handWidthMinute = sizeStorage.getMinutesHandWidth()

        val ambientMinuteColor = if(minuteColor.isColorTransparent()) { minuteColor } else { ambientColor }

        return HandData(
            minuteColor,
            ambientMinuteColor,
            shadowColor,
            shadowRadius,
            handWidthMinute,
            sizeStorage.getCircleRadius() + circleHandGap,
            sizeStorage.getMinutesHandScale(),
            dataStorage.useAntiAliasingInAmbientMode()
        )
    }

    fun getSecondHandData(): HandData {
        val secondColor = colorStorage.getSecondsHandColor()
        val handWidthSecond = sizeStorage.getSecondsHandWidth()

        val ambientSecondColor = if(secondColor.isColorTransparent()) { secondColor } else { ambientColor }

        return HandData(
            secondColor,
            ambientSecondColor,
            shadowColor,
            shadowRadius,
            handWidthSecond,
            sizeStorage.getCircleRadius(),
            sizeStorage.getSecondsHandScale(),
            dataStorage.useAntiAliasingInAmbientMode()
        )
    }

    fun getCircleData(): CircleData {
        val circleColor = colorStorage.getCentralCircleColor()
        val circleWidth = sizeStorage.getCircleWidth()
        val middleCircleRadius = sizeStorage.getCircleRadius()

        val ambientCircleColor =
            if(dataStorage.hasCenterCircleInAmbientMode()) {
                if (circleColor.isColorTransparent()) {
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
            dataStorage.useAntiAliasingInAmbientMode()
        )
    }

    private fun Int.isColorTransparent(): Boolean = this == 0
}