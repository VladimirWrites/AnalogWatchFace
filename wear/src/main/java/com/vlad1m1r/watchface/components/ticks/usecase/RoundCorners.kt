package com.vlad1m1r.watchface.components.ticks.usecase

import com.vlad1m1r.watchface.model.Point
import javax.inject.Inject
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.exp

/*
It's used for adding round corners to a rectangular watch face.
The result of this method should be subtracted from the distance dimension of the position of the ticks
in Polar coordinate system.
*/

class RoundCorners @Inject constructor() {
    operator fun invoke(tickRotation: Double, center: Point, cornerRadius: Double = 0.0): Double {
        val angle = atan(center.x / center.y)
        val cornerAdjustment =
            if (tickRotation > angle - cornerRadius && tickRotation < angle + cornerRadius) {
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