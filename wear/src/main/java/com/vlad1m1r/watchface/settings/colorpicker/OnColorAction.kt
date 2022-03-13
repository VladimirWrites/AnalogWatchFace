package com.vlad1m1r.watchface.settings.colorpicker

import androidx.annotation.ColorInt

interface OnColorAction {
    fun colorSelected(@ColorInt color: Int)
    fun colorDeleted(@ColorInt color: Int)
}