package com.vlad1m1r.watchface.components.ticks

import com.vlad1m1r.watchface.model.Point
import kotlin.math.*

class AdjustToChin() {
    operator fun invoke(tickRotation: Double, center: Point, bottomInset: Int): Double {
        val angle = acos((center.x - bottomInset) / center.x)
        return if (tickRotation > PI - angle && tickRotation < PI + angle) {
            -(center.x - bottomInset) / (center.x * cos(tickRotation - 2 * PI))
        } else {
            1.0
        }
    }
}