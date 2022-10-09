package com.vlad1m1r.watchface.data.style

import android.content.Context
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateSecondsHandStyleSettings @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage,
    private val sizeStorage: SizeStorage
) {
    operator fun invoke(): List<UserStyleSetting> {
        val hasSmoothSecondsHand = UserStyleSetting.BooleanUserStyleSetting(
            HAND_SECONDS_IS_SMOOTH,
            context.resources,
            R.string.wear_smooth_seconds_hand,
            R.string.wear_hand_settings,
            null,
            listOf(WatchFaceLayer.BASE),
            dataStorage.hasSmoothSecondsHand(),
        )

        val hasSecondsHand = UserStyleSetting.BooleanUserStyleSetting(
            HAND_SECONDS_HAS,
            context.resources,
            R.string.wear_seconds_hand,
            R.string.wear_hand_settings,
            null,
            listOf(WatchFaceLayer.BASE),
            false,
        )

        val secondsHandColor = UserStyleSetting.LongRangeUserStyleSetting(
            HAND_SECONDS_COLOR,
            context.resources,
            R.string.wear_seconds_hand_color,
            R.string.wear_hand_settings,
            null,
            Long.MIN_VALUE,
            Long.MAX_VALUE,
            listOf(WatchFaceLayer.BASE),
            colorStorage.getSecondsHandColor().toLong(),
        )

        val secondsHandWidth = UserStyleSetting.LongRangeUserStyleSetting(
            HAND_SECONDS_WIDTH,
            context.resources,
            R.string.wear_hand_width,
            R.string.wear_hand_settings,
            null,
            Long.MIN_VALUE,
            Long.MAX_VALUE,
            listOf(WatchFaceLayer.BASE),
            sizeStorage.getSecondsHandWidth().toLong(),
        )

        val secondsHandLengthScale = UserStyleSetting.DoubleRangeUserStyleSetting(
            HAND_SECONDS_LENGTH_SCALE,
            context.resources,
            R.string.wear_hand_scale,
            R.string.wear_hand_settings,
            null,
            0.0,
            1.0,
            listOf(WatchFaceLayer.BASE),
            sizeStorage.getSecondsHandScale().toDouble(),
        )

        return listOf(
            hasSmoothSecondsHand,
            hasSecondsHand,
            secondsHandColor,
            secondsHandWidth,
            secondsHandLengthScale
        )
    }
}

