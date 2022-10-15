package com.vlad1m1r.watchface.components.ticks.layout

import android.content.Context
import android.graphics.Canvas
import androidx.wear.watchface.DrawMode
import com.vlad1m1r.watchface.components.ticks.TickPaintProvider
import com.vlad1m1r.watchface.components.ticks.usecase.AdjustTicks
import com.vlad1m1r.watchface.components.ticks.usecase.RoundCorners
import com.vlad1m1r.watchface.data.state.TicksState
import com.vlad1m1r.watchface.model.Point

sealed class TicksLayout(
    context: Context
) {

    protected val isSquareScreen: Boolean = !context.resources.configuration.isScreenRound

    protected abstract val adjustTicks: AdjustTicks
    protected abstract val roundCorners: RoundCorners
    protected abstract val tickPaintProvider: TickPaintProvider

    protected lateinit var state: TicksState

    var bottomInset = 0

    protected fun shouldAdjustForBurnInProtection(mode: DrawMode) =
        mode == DrawMode.AMBIENT && ((isSquareScreen && state.shouldAdjustToSquareScreen) || !isSquareScreen)

    abstract fun draw(canvas: Canvas, drawMode: DrawMode, center: Point)

    open fun setTicksState(ticksState: TicksState) {
        this.state = ticksState
    }
}