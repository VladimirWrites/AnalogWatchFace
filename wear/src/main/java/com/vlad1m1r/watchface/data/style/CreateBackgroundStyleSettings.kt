package com.vlad1m1r.watchface.data.style

import android.content.Context
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.utils.BACKGROUND_BLACK_IN_AMBIENT
import com.vlad1m1r.watchface.utils.BACKGROUND_LEFT_COLOR
import com.vlad1m1r.watchface.utils.BACKGROUND_RIGHT_COLOR
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateBackgroundStyleSettings @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage
) {
    operator fun invoke(): List<UserStyleSetting> {
        val hasBlackBackground = UserStyleSetting.BooleanUserStyleSetting(
            BACKGROUND_BLACK_IN_AMBIENT,
            context.resources,
            R.string.wear_black_ambient_background,
            R.string.wear_background_settings,
            null,
            listOf(WatchFaceLayer.BASE),
            dataStorage.hasBlackAmbientBackground(),
        )

        val backgroundLeftColor = UserStyleSetting.LongRangeUserStyleSetting(
            BACKGROUND_LEFT_COLOR,
            context.resources,
            R.string.wear_left_background_color,
            R.string.wear_background_settings,
            null,
            Long.MIN_VALUE,
            Long.MAX_VALUE,
            listOf(WatchFaceLayer.BASE),
            colorStorage.getBackgroundLeftColor().toLong(),
        )

        val backgroundRightColor = UserStyleSetting.LongRangeUserStyleSetting(
            BACKGROUND_RIGHT_COLOR,
            context.resources,
            R.string.wear_right_background_color,
            R.string.wear_background_settings,
            null,
            Long.MIN_VALUE,
            Long.MAX_VALUE,
            listOf(WatchFaceLayer.BASE),
            colorStorage.getBackgroundRightColor().toLong(),
        )

        return listOf(hasBlackBackground, backgroundLeftColor, backgroundRightColor)
    }
}

