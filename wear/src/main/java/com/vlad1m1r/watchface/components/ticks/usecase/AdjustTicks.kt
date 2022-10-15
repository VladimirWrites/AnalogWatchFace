package com.vlad1m1r.watchface.components.ticks.usecase

import com.vlad1m1r.watchface.model.Point
import javax.inject.Inject

class AdjustTicks @Inject constructor(
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
        return if (isSquareScreen && shouldAdjustToSquareScreen) {
            adjustToSquare(tickRotation, center)
        } else if (!isSquareScreen && bottomInset > 0) {
            adjustToChin(tickRotation, center, bottomInset)
        } else {
            1.0
        }
    }
}