package com.vlad1m1r.watchface.components.ticks

import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.watchface.components.ticks.usecase.AdjustTicks
import com.vlad1m1r.watchface.components.ticks.usecase.AdjustToChin
import com.vlad1m1r.watchface.components.ticks.usecase.AdjustToSquare
import com.vlad1m1r.watchface.model.Point
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AdjustTicksShould {

    private val adjustToChin = mock<AdjustToChin>()
    private val adjustToSquare = mock<AdjustToSquare>()

    private val adjustTicks = AdjustTicks(adjustToSquare, adjustToChin)

    private val tickRotation = 0.0
    private val center = Point(100f, 100f)

    @Test
    fun callAdjustToSquare_whenRectangularScreen_andShouldAdjustToSquareIsTrue() {
        adjustTicks(
            tickRotation,
            center,
            bottomInset = 0,
            isSquareScreen = true,
            shouldAdjustToSquareScreen = true
        )

        verify(adjustToSquare).invoke(tickRotation, center)
    }

    @Test
    fun callAdjustToChin_whenRoundScreen_andHasBottomInset() {
        val bottomInset = 10
        adjustTicks(
            tickRotation,
            center,
            bottomInset,
            isSquareScreen = false,
            shouldAdjustToSquareScreen = false
        )

        verify(adjustToChin).invoke(tickRotation, center, bottomInset)
    }

    @Test
    fun returnOne_whenRectangularScreen_andShouldAdjustToSquareIsFalse() {
        val result = adjustTicks(
            tickRotation,
            center,
            10,
            isSquareScreen = true,
            shouldAdjustToSquareScreen = false
        )

        assertThat(result).isEqualTo(1.0)
    }

    @Test
    fun returnOne_whenRoundScreen_andHasNoBottomInset() {
        val result = adjustTicks(
            tickRotation,
            center,
            0,
            isSquareScreen = false,
            shouldAdjustToSquareScreen = false
        )

        assertThat(result).isEqualTo(1.0)
    }
}