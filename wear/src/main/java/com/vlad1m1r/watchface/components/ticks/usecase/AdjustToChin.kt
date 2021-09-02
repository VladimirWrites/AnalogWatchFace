package com.vlad1m1r.watchface.components.ticks.usecase

import com.vlad1m1r.watchface.model.Point
import javax.inject.Inject
import kotlin.math.*

/*
It's used for adjusting the round tick layout to the shape of the watch with chin (flat tire).
It calculates half of the central angle over the chin, an than for all angles between PI - angle and PI + angle
it calculates the difference in distance between center of the watch face and edge of the watchface compared
to distance between center of the watch face and the point on the edge of the chin.

This allows us to translate the ticks from the edge to the watch face to the edge of the chin by multiplying the
distance from the center, in Polar coordinate system, by the result of this method.
*/

class AdjustToChin @Inject constructor() {
    operator fun invoke(tickRotation: Double, center: Point, bottomInset: Int): Double {
        val angle = acos((center.x - bottomInset) / center.x)
        return if (tickRotation > PI - angle && tickRotation < PI + angle) {
            -(center.x - bottomInset) / (center.x * cos(tickRotation - 2 * PI))
        } else {
            1.0
        }
    }
}