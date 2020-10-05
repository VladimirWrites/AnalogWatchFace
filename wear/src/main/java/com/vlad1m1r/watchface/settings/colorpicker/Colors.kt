package com.vlad1m1r.watchface.settings.colorpicker

import androidx.annotation.ColorInt

data class Colors(
    @ColorInt val firstColor: Int?,
    @ColorInt val secondColor: Int,
    @ColorInt val thirdColor: Int
)