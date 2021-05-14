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
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class TicksLayoutOriginal(
    context: Context,
    private val dataStorage: DataStorage,
    colorStorage: ColorStorage
) : TicksLayout(context, dataStorage) {

    private val tickLength = context.resources.getDimension(R.dimen.original_tick_length)
    private val watchTickColor = colorStorage.getHourTicksColor()
    private val tickWidth = context.resources.getDimension(R.dimen.original_tick_width)

    private val tickBurnInPadding = context.resources.getDimension(R.dimen.original_tick_padding)
    private var tickPadding = tickBurnInPadding

    override var centerInvalidated = true
        private set

    private val tickPaint = Paint().apply {
        color = watchTickColor
        strokeWidth = tickWidth
        isAntiAlias = true
        style = Paint.Style.STROKE
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }

    private var center = Point()
    private var outerTickRadius: Float = 0f
    private var innerTickRadius: Float = 0f

    override fun setCenter(center: Point) {
        centerInvalidated = false
        this.center = center
        this.outerTickRadius = center.x - tickPadding
        this.innerTickRadius = center.x - tickLength - tickPadding
    }

    override fun draw(canvas: Canvas) {
        for (tickIndex in 0..11) {
            val tickRotation = tickIndex * PI / 6
            val adjust = if (shouldAdjustToSquareScreen) adjustToSquare(tickRotation, center) else 1.0
            val roundCorners = if (shouldAdjustToSquareScreen) roundCorners(tickRotation, center, PI / 20) * 10 else 0.0

            val innerX = sin(tickRotation) * (innerTickRadius - roundCorners) * adjust
            val innerY = -cos(tickRotation) * (innerTickRadius - roundCorners) * adjust
            val outerX = sin(tickRotation) * (outerTickRadius - roundCorners) * adjust
            val outerY = -cos(tickRotation) * (outerTickRadius - roundCorners) * adjust
            canvas.drawLine(
                (center.x + innerX).toFloat(), (center.y + innerY).toFloat(),
                (center.x + outerX).toFloat(), (center.y + outerY).toFloat(), tickPaint
            )
        }
    }

    override fun setMode(mode: Mode) {
        tickPaint.apply {
            if (mode.isAmbient) {
                inAmbientMode(getLighterGrayscale(watchTickColor), dataStorage.useAntiAliasingInAmbientMode())
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                inInteractiveMode(watchTickColor, shadowColor, shadowRadius)
                strokeWidth = tickWidth
            }
        }
        tickPadding = if (shouldAdjustForBurnInProtection(mode)) {
            tickBurnInPadding
        } else {
            0f
        }
        centerInvalidated = true
    }
}