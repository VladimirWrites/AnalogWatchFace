package com.vlad1m1r.watchface.components.hands

import androidx.annotation.ColorInt

data class CircleData(
    @ColorInt val color: Int,
    @ColorInt val colorAmbient: Int,
    @ColorInt val shadowColor: Int,
    val shadowRadius: Int,
    val width: Int,
    val radius: Int,
    val useAntiAliasingInAmbientMode: Boolean
)
