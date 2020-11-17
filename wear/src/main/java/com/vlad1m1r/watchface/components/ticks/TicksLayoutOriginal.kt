package com.vlad1m1r.watchface.components.ticks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.WatchView
import com.vlad1m1r.watchface.utils.getLighterGrayscale
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class TicksLayoutOriginal(context: Context, colorStorage: ColorStorage) : WatchView(context), Ticks {

    private val tickLength = context.resources.getDimension(R.dimen.tick_length)
    private val watchTickColor = colorStorage.getHourTicksColor()
    private val tickWidth = context.resources.getDimension(R.dimen.tick_width)

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
        this.outerTickRadius = center.x
        this.innerTickRadius = center.x - tickLength
    }

    override fun draw(canvas: Canvas) {
        for (tickIndex in 0..11) {
            val tickRotation = tickIndex * PI / 6
            val innerX = sin(tickRotation) * innerTickRadius
            val innerY = -cos(tickRotation) * innerTickRadius
            val outerX = sin(tickRotation) * outerTickRadius
            val outerY = -cos(tickRotation) * outerTickRadius
            canvas.drawLine(
                (center.x + innerX).toFloat(), (center.y + innerY).toFloat(),
                (center.x + outerX).toFloat(), (center.y + outerY).toFloat(), tickPaint
            )
        }
    }

    override fun setMode(mode: Mode) {
        tickPaint.apply {
            if (mode.isAmbient) {
                inAmbientMode(getLighterGrayscale(watchTickColor))
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                inInteractiveMode(watchTickColor)
                strokeWidth = tickWidth
            }
        }
    }
}