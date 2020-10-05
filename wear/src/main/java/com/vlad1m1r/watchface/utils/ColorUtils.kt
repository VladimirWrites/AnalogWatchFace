package com.vlad1m1r.watchface.utils

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.min

fun getDarkerGrayscale(@ColorInt color: Int): Int {
    return getGrayscale(color) { gray -> (gray / 2).toInt() }
}

fun getLighterGrayscale(@ColorInt color: Int): Int {
    return getGrayscale(color) { gray -> min((gray / 2).toInt() + 128, 255) }
}

private fun getGrayscale(@ColorInt color: Int, adjustGray: (Double) -> Int): Int {
    val a = Color.alpha(color)
    val r = Color.red(color)
    val g = Color.green(color)
    val b = Color.blue(color)

    val gray = ((0.3 * r) + (0.59 * g) + (0.11 * b))

    val adjustedGray = adjustGray(gray)

    return Color.argb(a, adjustedGray, adjustedGray, adjustedGray)
}