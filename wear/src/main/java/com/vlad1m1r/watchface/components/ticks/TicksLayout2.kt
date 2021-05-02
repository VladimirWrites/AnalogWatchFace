package com.vlad1m1r.watchface.components.ticks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.getLighterGrayscale
import com.vlad1m1r.watchface.utils.inAmbientMode
import com.vlad1m1r.watchface.utils.inInteractiveMode
import kotlin.math.*

class TicksLayout2(
    context: Context,
    private val dataStorage: DataStorage,
    colorStorage: ColorStorage
) : TicksLayout(context, dataStorage) {

    private val tickRadius = context.resources.getDimension(R.dimen.design2_tick_radius)
    private val watchHourTickColor = colorStorage.getHourTicksColor()

    private val tickRadiusMinute = context.resources.getDimension(R.dimen.design2_tick_radius_minute)
    private val watchMinuteTickColor = colorStorage.getMinuteTicksColor()

    private val tickPadding = context.resources.getDimension(R.dimen.design2_tick_padding)

    override var centerInvalidated = true
        private set

    private val tickPaint = Paint().apply {
        color = watchHourTickColor
        isAntiAlias = true
        style = Paint.Style.FILL
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }
    private val tickPaintMinute = Paint().apply {
        color = watchMinuteTickColor
        isAntiAlias = true
        style = Paint.Style.FILL
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }

    private var center = Point()
    private var outerTickRadius: Float = 0f

    override fun setCenter(center: Point) {
        centerInvalidated = false
        this.center = center
        this.outerTickRadius = center.x - tickPadding
    }

    override fun draw(canvas: Canvas) {
        for (tickIndex in 0..59) {
            val tickRotation = tickIndex * PI / 30
            val adjust = if(shouldAdjustToSquareScreen) adjustToSquare(tickRotation) else 1.0
            if (tickIndex % 5 == 0) {
                val x = sin(tickRotation) * outerTickRadius * adjust
                val y = -cos(tickRotation) * outerTickRadius * adjust
                canvas.drawCircle(
                    (center.x + x).toFloat(), (center.y + y).toFloat(),
                    tickRadius, tickPaint
                )
            } else {
                val x = sin(tickRotation) * outerTickRadius * adjust
                val y = -cos(tickRotation) * outerTickRadius * adjust
                canvas.drawCircle(
                    (center.x + x).toFloat(), (center.y + y).toFloat(),
                    tickRadiusMinute, tickPaintMinute
                )
            }
        }
    }

    override fun setMode(mode: Mode) {
        tickPaint.apply {
            if (mode.isAmbient) {
                inAmbientMode(getLighterGrayscale(watchHourTickColor), dataStorage.useAntiAliasingInAmbientMode())
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                    style = Paint.Style.STROKE
                }
            } else {
                inInteractiveMode(watchHourTickColor, shadowColor, shadowRadius)
                style = Paint.Style.FILL
            }
        }
        tickPaintMinute.apply {
            if (mode.isAmbient) {
                inAmbientMode(getLighterGrayscale(watchMinuteTickColor), dataStorage.useAntiAliasingInAmbientMode())
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                    style = Paint.Style.STROKE
                }
            } else {
                inInteractiveMode(watchMinuteTickColor, shadowColor, shadowRadius)
                style = Paint.Style.FILL
            }
        }
    }
}