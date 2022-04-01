package com.vlad1m1r.watchface

import android.content.Context
import androidx.wear.watchface.style.UserStyleSchema
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer

const val WATCH_BACKGROUND_MODIFIED = "WATCH_BACKGROUND_MODIFIED"

const val WATCH_HANDS_MODIFIED = "WATCH_HANDS_MODIFIED"

const val WATCH_COMPLICATION_MODIFIED = "WATCH_COMPLICATION_MODIFIED"

const val WATCH_TICKS_MODIFIED = "WATCH_TICKS_MODIFIED"

fun createUserStyleSchema(context: Context): UserStyleSchema {

    val backgroundModified = UserStyleSetting.BooleanUserStyleSetting(
        UserStyleSetting.Id(WATCH_BACKGROUND_MODIFIED),
        context.resources,
        R.string.wear_background_settings,
        R.string.wear_background_settings,
        null,
        listOf(WatchFaceLayer.BASE),
        false
    )

    val handsModified = UserStyleSetting.BooleanUserStyleSetting(
        UserStyleSetting.Id(WATCH_HANDS_MODIFIED),
        context.resources,
        R.string.wear_hand_settings,
        R.string.wear_hand_settings,
        null,
        listOf(WatchFaceLayer.COMPLICATIONS_OVERLAY),
        false
    )

    val complicationsModified = UserStyleSetting.BooleanUserStyleSetting(
        UserStyleSetting.Id(WATCH_COMPLICATION_MODIFIED),
        context.resources,
        R.string.wear_complications_settings,
        R.string.wear_complications_settings,
        null,
        listOf(WatchFaceLayer.COMPLICATIONS),
        false
    )

    val ticksModified = UserStyleSetting.BooleanUserStyleSetting(
        UserStyleSetting.Id(WATCH_TICKS_MODIFIED),
        context.resources,
        R.string.wear_ticks_settings,
        R.string.wear_ticks_settings,
        null,
        listOf(WatchFaceLayer.BASE),
        false
    )

    return UserStyleSchema(
        listOf(
            backgroundModified,
            handsModified,
            complicationsModified,
            ticksModified
        )
    )
}