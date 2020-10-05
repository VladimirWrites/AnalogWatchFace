package com.vlad1m1r.watchface.settings.colorpicker

import androidx.annotation.ColorInt

interface OnColorSelected {
    fun colorSelected(@ColorInt color: Int)
}