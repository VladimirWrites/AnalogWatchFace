package com.vlad1m1r.watchface.components.hands

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange

data class HandData(
    @ColorInt val color: Int,
    @ColorInt val colorAmbient: Int,
    @ColorInt  val shadowColor: Int,
    val shadowRadius: Int,
    val width: Int,
    val paddingFromCenter: Int,
    @FloatRange(from = 0.0, to = 1.0) val handLengthRatio: Float,
    val useAntiAliasingInAmbientMode: Boolean
)
