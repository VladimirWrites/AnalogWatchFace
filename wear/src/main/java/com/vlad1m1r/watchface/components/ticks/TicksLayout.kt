package com.vlad1m1r.watchface.components.ticks

import android.content.Context
import android.graphics.Canvas
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.utils.WatchView
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.tan

abstract class TicksLayout(context: Context, dataStorage: DataStorage): WatchView {
    protected val tickPaintProvider = TickPaintProvider(context)

    protected val isSquareScreen: Boolean = !context.resources.configuration.isScreenRound

    protected val shouldAdjustToSquareScreen: Boolean = dataStorage.shouldAdjustToSquareScreen()
    protected val adjustTicks = AdjustTicks(AdjustToSquare(), AdjustToChin())
    protected val roundCorners = RoundCorners()

    var bottomInset = 0

    abstract val centerInvalidated: Boolean

    abstract fun draw(canvas: Canvas)
    abstract fun setMode(mode: Mode)

    protected fun shouldAdjustForBurnInProtection(mode: Mode) =
        mode.isAmbient && mode.isBurnInProtection && ((isSquareScreen && shouldAdjustToSquareScreen) || !isSquareScreen)
}