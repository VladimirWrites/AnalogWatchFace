package com.vlad1m1r.watchface.data.style

import android.content.Context
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateTicksStyleSettings @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage
) {
    operator fun invoke(): List<UserStyleSetting> {
        val hasTicksInAmbientMode = UserStyleSetting.BooleanUserStyleSetting(
            TICKS_HAS_IN_AMBIENT_MODE,
            context.resources,
            R.string.wear_ticks_in_ambient_mode,
            R.string.wear_ticks_settings,
            null,
            listOf(WatchFaceLayer.BASE),
            dataStorage.hasTicksInAmbientMode(),
        )

        val hasTicksInInteractiveMode = UserStyleSetting.BooleanUserStyleSetting(
            TICKS_HAS_IN_INTERACTIVE_MODE,
            context.resources,
            R.string.wear_ticks_in_interactive_mode,
            R.string.wear_ticks_settings,
            null,
            listOf(WatchFaceLayer.BASE),
            dataStorage.hasTicksInInteractiveMode(),
        )

        val ticksLayoutType = UserStyleSetting.LongRangeUserStyleSetting(
            TICKS_LAYOUT_TYPE,
            context.resources,
            R.string.wear_ticks_layout_picker,
            R.string.wear_ticks_settings,
            null,
            0,
            3,
            listOf(WatchFaceLayer.BASE),
            dataStorage.getTicksLayoutType().id.toLong(),
        )

        val ticksHourColor = UserStyleSetting.LongRangeUserStyleSetting(
            TICKS_HOUR_COLOR,
            context.resources,
            R.string.wear_hour_ticks_color,
            R.string.wear_ticks_settings,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.BASE),
            colorStorage.getHourTicksColor().toLong(),
        )

        val ticksMinuteColor = UserStyleSetting.LongRangeUserStyleSetting(
            TICKS_MINUTE_COLOR,
            context.resources,
            R.string.wear_minute_ticks_color,
            R.string.wear_ticks_settings,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.BASE),
            colorStorage.getMinuteTicksColor().toLong(),
        )

        val ticksShouldAdjustToSquare = UserStyleSetting.BooleanUserStyleSetting(
            TICKS_SHOULD_ADJUST_TO_SQUARE,
            context.resources,
            R.string.wear_ticks_adjust_to_square_screen,
            R.string.wear_ticks_settings,
            null,
            listOf(WatchFaceLayer.BASE),
            dataStorage.shouldAdjustToSquareScreen(),
        )

        return listOf(
            hasTicksInAmbientMode,
            hasTicksInInteractiveMode,
            ticksLayoutType,
            ticksHourColor,
            ticksMinuteColor,
            ticksShouldAdjustToSquare
        )
    }
}

