package com.vlad1m1r.watchface.data.style

import android.content.Context
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.utils.HAND_MINUTES_COLOR
import com.vlad1m1r.watchface.utils.HAND_MINUTES_LENGTH_SCALE
import com.vlad1m1r.watchface.utils.HAND_MINUTES_WIDTH
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateMinutesHandStyleSettings @Inject constructor(
    @ApplicationContext private val context: Context,
    private val colorStorage: ColorStorage,
    private val sizeStorage: SizeStorage
) {
    operator fun invoke(): List<UserStyleSetting> {

        val minutesHandColor = UserStyleSetting.LongRangeUserStyleSetting(
            HAND_MINUTES_COLOR,
            context.resources,
            R.string.wear_minutes_hand_color,
            R.string.wear_hand_settings,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.BASE, WatchFaceLayer.COMPLICATIONS_OVERLAY),
            colorStorage.getMinutesHandColor().toLong(),
        )

        val minutesHandWidth = UserStyleSetting.LongRangeUserStyleSetting(
            HAND_MINUTES_WIDTH,
            context.resources,
            R.string.wear_hand_width,
            R.string.wear_hand_settings,
            null,
            1,
            20,
            listOf(WatchFaceLayer.BASE, WatchFaceLayer.COMPLICATIONS_OVERLAY),
            sizeStorage.getMinutesHandWidth().toLong(),
        )

        val minutesHandLengthScale = UserStyleSetting.DoubleRangeUserStyleSetting(
            HAND_MINUTES_LENGTH_SCALE,
            context.resources,
            R.string.wear_hand_scale,
            R.string.wear_hand_settings,
            null,
            0.0,
            1.0,
            listOf(WatchFaceLayer.BASE, WatchFaceLayer.COMPLICATIONS_OVERLAY),
            sizeStorage.getMinutesHandScale().toDouble(),
        )

        return listOf(minutesHandColor, minutesHandWidth, minutesHandLengthScale)
    }
}

