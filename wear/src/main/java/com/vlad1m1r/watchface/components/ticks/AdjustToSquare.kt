package com.vlad1m1r.watchface.components.ticks

import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.tan

class AdjustToSquare() {
    operator fun invoke(tickRotation: Double): Double {
        return if ((tickRotation < PI / 4 || tickRotation >= 7 * PI / 4) || (tickRotation >= 3 * PI / 4 && tickRotation < 5 * PI / 4)) {
            sqrt(1 + (tan(tickRotation).pow(2)))
        } else {
            sqrt(1 + (1 / tan(tickRotation).pow(2)))
        }
    }
}