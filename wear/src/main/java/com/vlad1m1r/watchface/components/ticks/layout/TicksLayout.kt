package com.vlad1m1r.watchface.components.ticks.layout

import android.content.Context
import android.graphics.Canvas
import com.vlad1m1r.watchface.components.ticks.usecase.AdjustTicks
import com.vlad1m1r.watchface.components.ticks.usecase.RoundCorners
import com.vlad1m1r.watchface.components.ticks.TickPaintProvider
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.utils.WatchView

sealed class TicksLayout(
    context: Context,
    private val dataStorage: DataStorage,
): WatchView {

    protected val isSquareScreen: Boolean = !context.resources.configuration.isScreenRound
    protected var shouldAdjustToSquareScreen: Boolean = dataStorage.shouldAdjustToSquareScreen()

    protected abstract val adjustTicks: AdjustTicks
    protected abstract val roundCorners: RoundCorners
    protected abstract val tickPaintProvider: TickPaintProvider

    var bottomInset = 0

    var centerInvalidated: Boolean = true
        protected set

    abstract fun draw(canvas: Canvas)
    abstract fun setMode(mode: Mode)

    protected fun shouldAdjustForBurnInProtection(mode: Mode) =
        mode.isAmbient && mode.isBurnInProtection && ((isSquareScreen && shouldAdjustToSquareScreen) || !isSquareScreen)

    fun invalidate() {
        shouldAdjustToSquareScreen = dataStorage.shouldAdjustToSquareScreen()
    }
}