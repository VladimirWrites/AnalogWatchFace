package com.vlad1m1r.watchface.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class TicksLayout1(context: Context) : WatchView(context), Ticks {

    private val tickLength = context.resources.getDimension(R.dimen.design2_tick_length)
    private val watchTickColor = ContextCompat.getColor(context, R.color.watch_tick)
    private val tickWidth = context.resources.getDimension(R.dimen.tick_width)

    private val tickLengthMinute = context.resources.getDimension(R.dimen.tick_length_minute)
    private val watchTickColorMinute = ContextCompat.getColor(context, R.color.watch_design2_tick_minute)
    private val tickWidthMinute = context.resources.getDimension(R.dimen.design2_tick_width_minute)

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
    private val tickPaintMinute = Paint().apply {
        color = watchTickColorMinute
        strokeWidth = tickWidthMinute
        isAntiAlias = true
        style = Paint.Style.STROKE
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }

    private var center = Point()
    private var outerTickRadius: Float = 0f
    private var innerTickRadius: Float = 0f
    private var innerTickRadiusMinute: Float = 0f

    override fun setCenter(center: Point) {
        centerInvalidated = false
        this.center = center
        this.outerTickRadius = center.x
        this.innerTickRadius = center.x - tickLength
        this.innerTickRadiusMinute = center.x - tickLengthMinute
    }

    override fun draw(canvas: Canvas) {
        for (tickIndex in 0..59) {
            // do not under paint the hour ones
            if (tickIndex % 5 != 0) {
                val tickRotation = tickIndex * PI / 30
                val innerX = sin(tickRotation) * innerTickRadiusMinute
                val innerY = -cos(tickRotation) * innerTickRadiusMinute
                val outerX = sin(tickRotation) * outerTickRadius
                val outerY = -cos(tickRotation) * outerTickRadius
                canvas.drawLine(
                    (center.x + innerX).toFloat(), (center.y + innerY).toFloat(),
                    (center.x + outerX).toFloat(), (center.y + outerY).toFloat(), tickPaintMinute
                )
            }
        }
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
                inAmbientMode(watchTickColor)
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                inInteractiveMode(watchTickColor)
                strokeWidth = tickWidth
            }
        }
        tickPaintMinute.apply {
            if (mode.isAmbient) {
                inAmbientMode(watchTickColorMinute)
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                inInteractiveMode(watchTickColorMinute)
                strokeWidth = tickWidthMinute
            }
        }
    }
}