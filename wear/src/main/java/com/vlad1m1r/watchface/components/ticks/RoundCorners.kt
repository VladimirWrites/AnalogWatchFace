package com.vlad1m1r.watchface.components.ticks

import com.vlad1m1r.watchface.model.Point
import kotlin.math.*

class RoundCorners() {
    operator fun invoke(tickRotation: Double, center: Point, cornerRadius: Double = 0.0): Double {
        val angle = atan(center.x / center.y)
        val cornerAdjustment = if (tickRotation > angle - cornerRadius && tickRotation < angle + cornerRadius) {
            cornerRadius - abs(angle - tickRotation)
        } else if (tickRotation > (2 * PI - angle) - cornerRadius && tickRotation < (2 * PI - angle) + cornerRadius) {
            cornerRadius - abs(2 * PI - angle - tickRotation)
        } else if (tickRotation > (PI - angle) - cornerRadius && tickRotation < (PI - angle) + cornerRadius) {
            cornerRadius - abs(PI - angle - tickRotation)
        } else if (tickRotation > (PI + angle) - cornerRadius && tickRotation < (PI + angle) + cornerRadius) {
            cornerRadius - abs(PI + angle - tickRotation)
        } else {
            0.0
        }

        return exp(PI / (3 * cornerRadius) * cornerAdjustment) - 1
    }
}