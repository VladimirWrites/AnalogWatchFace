package com.vlad1m1r.watchface.components.ticks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.WatchView
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.tan

abstract class TicksLayout(context: Context, dataStorage: DataStorage): WatchView {
    protected val tickPaintProvider = TickPaintProvider(context)

    private val isSquareScreen: Boolean = !context.resources.configuration.isScreenRound
    protected val shouldAdjustToSquareScreen: Boolean = dataStorage.shouldAdjustToSquareScreen()
    protected val adjustToSquare = AdjustToSquare()
    protected val roundCorners = RoundCorners()

    abstract val centerInvalidated: Boolean

    abstract fun draw(canvas: Canvas)
    abstract fun setMode(mode: Mode)

    protected fun shouldAdjustForBurnInProtection(mode: Mode) =
        mode.isAmbient && mode.isBurnInProtection && ((isSquareScreen && shouldAdjustToSquareScreen) || !isSquareScreen)
}