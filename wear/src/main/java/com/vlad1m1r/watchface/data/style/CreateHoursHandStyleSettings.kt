package com.vlad1m1r.watchface.data.style

import android.content.Context
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.utils.HAND_HOURS_COLOR
import com.vlad1m1r.watchface.utils.HAND_HOURS_LENGTH_SCALE
import com.vlad1m1r.watchface.utils.HAND_HOURS_WIDTH
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateHoursHandStyleSettings @Inject constructor(
    @ApplicationContext private val context: Context,
    private val colorStorage: ColorStorage,
    private val sizeStorage: SizeStorage
) {
    operator fun invoke(): List<UserStyleSetting> {

        val hoursHandColor = UserStyleSetting.LongRangeUserStyleSetting(
            HAND_HOURS_COLOR,
            context.resources,
            R.string.wear_hours_hand_color,
            R.string.wear_hand_settings,
            null,
            Long.MIN_VALUE,
            Long.MAX_VALUE,
            listOf(WatchFaceLayer.BASE),
            colorStorage.getHoursHandColor().toLong(),
        )

        val hoursHandWidth = UserStyleSetting.LongRangeUserStyleSetting(
            HAND_HOURS_WIDTH,
            context.resources,
            R.string.wear_hand_width,
            R.string.wear_hand_settings,
            null,
            Long.MIN_VALUE,
            Long.MAX_VALUE,
            listOf(WatchFaceLayer.BASE),
            sizeStorage.getHoursHandWidth().toLong(),
        )

        val hoursHandLengthScale = UserStyleSetting.DoubleRangeUserStyleSetting(
            HAND_HOURS_LENGTH_SCALE,
            context.resources,
            R.string.wear_hand_scale,
            R.string.wear_hand_settings,
            null,
            0.0,
            1.0,
            listOf(WatchFaceLayer.BASE),
            sizeStorage.getHoursHandScale().toDouble(),
        )

        return listOf(hoursHandColor, hoursHandWidth, hoursHandLengthScale)
    }
}

