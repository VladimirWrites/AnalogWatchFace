package com.vlad1m1r.watchface.components.ticks

import com.vlad1m1r.watchface.model.Point

class AdjustTicks(
    private val adjustToSquare: AdjustToSquare,
    private val adjustToChin: AdjustToChin
) {
    operator fun invoke(
        tickRotation: Double,
        center: Point,
        bottomInset: Int,
        isSquareScreen: Boolean,
        shouldAdjustToSquareScreen: Boolean
    ): Double {
        return if(isSquareScreen && shouldAdjustToSquareScreen) {
            adjustToSquare(tickRotation, center)
        } else if (!isSquareScreen && bottomInset > 0) {
            adjustToChin(tickRotation, center, bottomInset)
        } else {
            1.0
        }
    }
}