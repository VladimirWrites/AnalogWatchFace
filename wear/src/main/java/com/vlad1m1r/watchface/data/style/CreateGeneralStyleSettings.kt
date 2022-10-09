package com.vlad1m1r.watchface.data.style

import android.content.Context
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.utils.WATCH_USE_ANTIALIASING
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateGeneralStyleSettings @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorage: DataStorage
) {
    operator fun invoke(): List<UserStyleSetting> {
        val useAntialiasingInAmbientMode = UserStyleSetting.BooleanUserStyleSetting(
            WATCH_USE_ANTIALIASING,
            context.resources,
            R.string.wear_anti_aliasing_in_ambient_mode,
            R.string.wear_settings,
            null,
            listOf(WatchFaceLayer.BASE),
            dataStorage.useAntiAliasingInAmbientMode(),
        )

        return listOf(
            useAntialiasingInAmbientMode
        )
    }
}

