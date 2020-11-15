package com.vlad1m1r.watchface.utils

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

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

fun getDistanceBetweenColors(@ColorInt color1: Int, @ColorInt color2: Int): Float {
    val r1 = Color.red(color1)
    val g1 = Color.green(color1)
    val b1 = Color.blue(color1)

    val r2 = Color.red(color2)
    val g2 = Color.green(color2)
    val b2 = Color.blue(color2)

    return sqrt(
        (r1 - r2).toFloat().pow(2) +
                (g1 - g2).toFloat().pow(2) +
                (b1 - b2).toFloat().pow(2)
    )
}