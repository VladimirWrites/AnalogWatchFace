package com.vlad1m1r.watchface.components.background

import androidx.annotation.ColorInt

data class BackgroundData(
    @ColorInt val leftColor: Int,
    @ColorInt val rightColor: Int,
    @ColorInt val leftColorAmbient: Int,
    @ColorInt val rightColorAmbient: Int
)
