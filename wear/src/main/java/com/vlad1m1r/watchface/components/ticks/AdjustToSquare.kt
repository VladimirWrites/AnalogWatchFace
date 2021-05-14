package com.vlad1m1r.watchface.components.ticks

import com.vlad1m1r.watchface.model.Point
import kotlin.math.*

class AdjustToSquare() {
    operator fun invoke(tickRotation: Double, center: Point): Double {
        val angle = atan(center.x / center.y)
        return if ((tickRotation < angle || tickRotation >= 2 * PI - angle) || (tickRotation >= PI - angle && tickRotation < PI + angle)) {
            sqrt(1 + (tan(tickRotation).pow(2))) * center.y / center.x
        } else {
            sqrt(1 + (1 / tan(tickRotation).pow(2)))
        }
    }
}