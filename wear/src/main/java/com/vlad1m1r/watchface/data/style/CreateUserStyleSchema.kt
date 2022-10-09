package com.vlad1m1r.watchface.data.style

import androidx.wear.watchface.style.UserStyleSchema
import javax.inject.Inject

class CreateUserStyleSchema @Inject constructor(
    private val createBackgroundStyleSettings: CreateBackgroundStyleSettings,
    private val createTicksStyleSettings: CreateTicksStyleSettings,
    private val createSecondsHandStyleSettings: CreateSecondsHandStyleSettings,
    private val createMinutesHandStyleSettings: CreateMinutesHandStyleSettings,
    private val createHoursHandStyleSettings: CreateHoursHandStyleSettings,
    private val createHandCircleStyleSettings: CreateHandCircleStyleSettings,
    private val createGeneralHandStyleSettings: CreateGeneralHandStyleSettings,
    private val createGeneralStyleSettings: CreateGeneralStyleSettings
) {
    operator fun invoke(): UserStyleSchema {
        return UserStyleSchema(
            createBackgroundStyleSettings() +
                    createTicksStyleSettings() +
                    createSecondsHandStyleSettings() +
                    createMinutesHandStyleSettings() +
                    createHoursHandStyleSettings() +
                    createHandCircleStyleSettings() +
                    createGeneralHandStyleSettings() +
                    createGeneralStyleSettings()
        )
    }
}