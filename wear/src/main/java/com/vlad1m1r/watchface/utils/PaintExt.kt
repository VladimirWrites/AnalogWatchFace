package com.vlad1m1r.watchface.utils

import android.graphics.Paint
import androidx.annotation.ColorInt

fun Paint.inAmbientMode(@ColorInt color: Int, isAntiAlias: Boolean) {
    this.color = color
    this.isAntiAlias = isAntiAlias
    clearShadowLayer()
}

fun Paint.inInteractiveMode(@ColorInt color: Int, @ColorInt shadowColor: Int, shadowRadius: Float) {
    this.color = color
    isAntiAlias = true
    setShadowLayer(
        shadowRadius, 0f, 0f, shadowColor
    )
}