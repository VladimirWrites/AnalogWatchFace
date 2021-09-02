package com.vlad1m1r.watchface.components.ticks.usecase

import com.vlad1m1r.watchface.model.Point
import javax.inject.Inject
import kotlin.math.*

/*
It's used for adjusting the round tick layout to a rectangle shape of the watch.
It calculates the difference in distance between center of the watch face and edge of the round watchface compared
to distance between center of the watch face and the point on the edge of the rectangular screen.

This allows us to translate the ticks from the edge to the round watch face to the edge of the rectangular screen by multiplying the
distance from the center, in Polar coordinate system, by the result of this method.
*/

class AdjustToSquare @Inject constructor() {
    operator fun invoke(tickRotation: Double, center: Point): Double {
        val angle = atan(center.x / center.y)
        return if ((tickRotation < angle || tickRotation >= 2 * PI - angle) || (tickRotation >= PI - angle && tickRotation < PI + angle)) {
            sqrt(1 + (tan(tickRotation).pow(2))) * center.y / center.x
        } else {
            sqrt(1 + (1 / tan(tickRotation).pow(2)))
        }
    }
}