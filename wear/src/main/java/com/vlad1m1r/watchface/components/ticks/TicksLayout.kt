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
    protected val shadowColor = ContextCompat.getColor(context, R.color.watch_shadow)
    protected val shadowRadius = context.resources.getDimension(R.dimen.shadow_radius)
    private val ambientColor = ContextCompat.getColor(context, R.color.watch_ambient)
    private val isSquareScreen: Boolean = !context.resources.configuration.isScreenRound
    protected val shouldAdjustToSquareScreen: Boolean = dataStorage.shouldAdjustToSquareScreen()

    abstract val centerInvalidated: Boolean

    abstract fun draw(canvas: Canvas)
    abstract fun setMode(mode: Mode)
    fun adjustToSquare(tickRotation: Double): Double {
        if ((tickRotation < PI / 4 || tickRotation >= 7 * PI / 4) || (tickRotation >= 3 * PI / 4 && tickRotation < 5 * PI / 4)) {
            return sqrt(1 + (tan(tickRotation).pow(2)))
        }
        if ((tickRotation >= PI / 4 && tickRotation < 3 * PI / 4) || (tickRotation >= 5 * PI / 4 && tickRotation <= 7 * PI / 4)) {
            return sqrt(1 + (1 / tan(tickRotation).pow(2)))
        }
        return 1.0
    }

    protected fun Paint.inAmbientMode(color: Int = ambientColor) {
        this.color = color
        isAntiAlias = false
        clearShadowLayer()
    }

    protected fun Paint.inInteractiveMode(color: Int) {
        this.color = color
        isAntiAlias = true
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }

    protected fun shouldAdjustForBurnInProtection(mode: Mode) =
        mode.isAmbient && mode.isBurnInProtection && ((isSquareScreen && shouldAdjustToSquareScreen) || !isSquareScreen)
}