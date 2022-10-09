package com.vlad1m1r.watchface.components.ticks.layout

import android.content.Context
import android.graphics.Canvas
import com.vlad1m1r.watchface.components.ticks.usecase.AdjustTicks
import com.vlad1m1r.watchface.components.ticks.usecase.RoundCorners
import com.vlad1m1r.watchface.components.ticks.TickPaintProvider
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.state.TicksState
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.utils.WatchView

sealed class TicksLayout(
    context: Context
): WatchView {

    protected val isSquareScreen: Boolean = !context.resources.configuration.isScreenRound

    protected abstract val adjustTicks: AdjustTicks
    protected abstract val roundCorners: RoundCorners
    protected abstract val tickPaintProvider: TickPaintProvider

    protected lateinit var state: TicksState

    var bottomInset = 0

    var centerInvalidated: Boolean = true
        protected set

    abstract fun draw(canvas: Canvas)
    abstract fun setMode(mode: Mode)

    protected fun shouldAdjustForBurnInProtection(mode: Mode) =
        mode.isAmbient && mode.isBurnInProtection && ((isSquareScreen && state.shouldAdjustToSquareScreen) || !isSquareScreen)

    open fun setTicksState(ticksState: TicksState) {
        this.state = ticksState
    }
}