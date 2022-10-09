package com.vlad1m1r.watchface.data.style

import android.content.Context
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.utils.HAND_CIRCLE_COLOR
import com.vlad1m1r.watchface.utils.HAND_CIRCLE_HAS_IN_AMBIENT_MODE
import com.vlad1m1r.watchface.utils.HAND_CIRCLE_RADIUS
import com.vlad1m1r.watchface.utils.HAND_CIRCLE_WIDTH
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateHandCircleStyleSettings @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage,
    private val sizeStorage: SizeStorage
) {
    operator fun invoke(): List<UserStyleSetting> {

        val handCircleColor = UserStyleSetting.LongRangeUserStyleSetting(
            HAND_CIRCLE_COLOR,
            context.resources,
            R.string.wear_central_circle_color,
            R.string.wear_central_circle,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.BASE),
            colorStorage.getCentralCircleColor().toLong(),
        )

        val handCircleWidth = UserStyleSetting.LongRangeUserStyleSetting(
            HAND_CIRCLE_WIDTH,
            context.resources,
            R.string.wear_circle_width,
            R.string.wear_central_circle,
            null,
            1,
            15,
            listOf(WatchFaceLayer.BASE),
            sizeStorage.getCircleWidth().toLong(),
        )

        val handCircleRadius = UserStyleSetting.LongRangeUserStyleSetting(
            HAND_CIRCLE_RADIUS,
            context.resources,
            R.string.wear_circle_radius,
            R.string.wear_central_circle,
            null,
            1,
            15,
            listOf(WatchFaceLayer.BASE),
            sizeStorage.getCircleRadius().toLong(),
        )

        val hasCircleInAmbientMode = UserStyleSetting.BooleanUserStyleSetting(
            HAND_CIRCLE_HAS_IN_AMBIENT_MODE,
            context.resources,
            R.string.wear_central_circle_in_ambient_mode,
            R.string.wear_central_circle,
            null,
            listOf(WatchFaceLayer.BASE),
            dataStorage.hasCenterCircleInAmbientMode(),
        )

        return listOf(handCircleColor, handCircleWidth, handCircleRadius, hasCircleInAmbientMode)
    }
}

