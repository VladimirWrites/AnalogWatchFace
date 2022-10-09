package com.vlad1m1r.watchface.data.style

import android.content.Context
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.utils.HANDS_HAS
import com.vlad1m1r.watchface.utils.HANDS_HAS_IN_INTERACTIVE
import com.vlad1m1r.watchface.utils.HANDS_KEEP_COLOR_IN_AMBIENT_MODE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateGeneralHandStyleSettings @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorage: DataStorage
) {
    operator fun invoke(): List<UserStyleSetting> {

        val hasHandsInInteractiveMode = UserStyleSetting.BooleanUserStyleSetting(
            HANDS_HAS_IN_INTERACTIVE,
            context.resources,
            R.string.wear_hand_settings,
            R.string.wear_hand_settings,
            null,
            listOf(WatchFaceLayer.BASE, WatchFaceLayer.COMPLICATIONS_OVERLAY),
            true,
        )

        val keepHandColorInAmbientMode = UserStyleSetting.BooleanUserStyleSetting(
            HANDS_KEEP_COLOR_IN_AMBIENT_MODE,
            context.resources,
            R.string.wear_keep_hands_color_in_ambient_mode,
            R.string.wear_hand_settings,
            null,
            listOf(WatchFaceLayer.BASE, WatchFaceLayer.COMPLICATIONS_OVERLAY),
            dataStorage.shouldKeepHandColorInAmbientMode(),
        )

        val hasHands = UserStyleSetting.BooleanUserStyleSetting(
            HANDS_HAS,
            context.resources,
            R.string.wear_hand_settings,
            R.string.wear_hand_settings,
            null,
            listOf(WatchFaceLayer.BASE, WatchFaceLayer.COMPLICATIONS_OVERLAY),
            true,
        )

        return listOf(hasHandsInInteractiveMode, keepHandColorInAmbientMode, hasHands)
    }
}

