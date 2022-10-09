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

class CreateComplicationStyleSettings @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage
) {
    operator fun invoke(): List<UserStyleSetting> {

        val hasInAmbientMode = UserStyleSetting.BooleanUserStyleSetting(
            COMPLICATION_HAS_IN_AMBIENT_MODE,
            context.resources,
            R.string.wear_complications_in_ambient_mode,
            R.string.wear_complications_settings,
            null,
            listOf(WatchFaceLayer.COMPLICATIONS),
            dataStorage.hasComplicationsInAmbientMode(),
        )

        val hasBiggerTopAndBottom = UserStyleSetting.BooleanUserStyleSetting(
            COMPLICATION_HAS_BIGGER_TOP_AND_BOTTOM,
            context.resources,
            R.string.wear_bigger_top_and_bottom_complications,
            R.string.wear_complications_settings,
            null,
            listOf(WatchFaceLayer.COMPLICATIONS),
            dataStorage.hasBiggerTopAndBottomComplications(),
        )

        val textColor = UserStyleSetting.LongRangeUserStyleSetting(
            COMPLICATION_TEXT_COLOR,
            context.resources,
            R.string.wear_complications_text_color,
            R.string.wear_complications_settings,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.COMPLICATIONS),
            colorStorage.getComplicationsTextColor().toLong(),
        )

        val titleColor = UserStyleSetting.LongRangeUserStyleSetting(
            COMPLICATION_TITLE_COLOR,
            context.resources,
            R.string.wear_complications_title_color,
            R.string.wear_complications_settings,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.COMPLICATIONS),
            colorStorage.getComplicationsTitleColor().toLong(),
        )

        val iconColor = UserStyleSetting.LongRangeUserStyleSetting(
            COMPLICATION_ICON_COLOR,
            context.resources,
            R.string.wear_complications_icon_color,
            R.string.wear_complications_settings,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.COMPLICATIONS),
            colorStorage.getComplicationsIconColor().toLong(),
        )

        val borderColor = UserStyleSetting.LongRangeUserStyleSetting(
            COMPLICATION_BORDER_COLOR,
            context.resources,
            R.string.wear_complications_border_color,
            R.string.wear_complications_settings,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.COMPLICATIONS),
            colorStorage.getComplicationsBorderColor().toLong(),
        )

        val rangedValuePrimaryColor = UserStyleSetting.LongRangeUserStyleSetting(
            COMPLICATION_RANGED_VALUE_PRIMARY_COLOR,
            context.resources,
            R.string.wear_complications_ranged_value_primary_color,
            R.string.wear_complications_settings,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.COMPLICATIONS),
            colorStorage.getComplicationsRangedValuePrimaryColor().toLong(),
        )

        val rangedValueSecondaryColor = UserStyleSetting.LongRangeUserStyleSetting(
            COMPLICATION_RANGED_VALUE_SECONDARY_COLOR,
            context.resources,
            R.string.wear_complications_ranged_value_secondary_color,
            R.string.wear_complications_settings,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.COMPLICATIONS),
            colorStorage.getComplicationsRangedValueSecondaryColor().toLong(),
        )

        val backgroundColor = UserStyleSetting.LongRangeUserStyleSetting(
            COMPLICATION_BACKGROUND_COLOR,
            context.resources,
            R.string.wear_complications_background_color,
            R.string.wear_complications_settings,
            null,
            Int.MIN_VALUE.toLong(),
            Int.MAX_VALUE.toLong(),
            listOf(WatchFaceLayer.COMPLICATIONS),
            colorStorage.getComplicationsBackgroundColor().toLong(),
        )

        return listOf(
            hasInAmbientMode,
            hasBiggerTopAndBottom,
            textColor,
            titleColor,
            iconColor,
            borderColor,
            rangedValuePrimaryColor,
            rangedValueSecondaryColor,
            backgroundColor
        )
    }
}

