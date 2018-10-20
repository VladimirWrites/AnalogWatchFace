package com.vlad1m1r.watchface.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import com.vlad1m1r.watchface.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Ticks(context: Context) : WatchView(context) {

    private val tickLength = context.resources.getDimension(R.dimen.tick_length)
    private val watchTickColor = ContextCompat.getColor(context, R.color.watch_tick)
    private val tickWidth = context.resources.getDimension(R.dimen.tick_width)

    private val tickPaint = Paint().apply {
        color = watchTickColor
        strokeWidth = tickWidth
        isAntiAlias = true
        style = Paint.Style.STROKE
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }

    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var outerTickRadius: Float = 0f
    private var innerTickRadius: Float = 0f

    override fun setCenter(centerX: Float, centerY: Float) {
        this.centerX = centerX
        this.centerY = centerY
        this.outerTickRadius = centerX
        this.innerTickRadius = centerX - tickLength
    }

    fun draw(canvas: Canvas) {
        for (tickIndex in 0..11) {
            val tickRotation = tickIndex * PI / 6
            val innerX = sin(tickRotation) * innerTickRadius
            val innerY = -cos(tickRotation) * innerTickRadius
            val outerX = sin(tickRotation) * outerTickRadius
            val outerY = -cos(tickRotation) * outerTickRadius
            canvas.drawLine(
                (centerX + innerX).toFloat(), (centerY + innerY).toFloat(),
                (centerX + outerX).toFloat(), (centerY + outerY).toFloat(), tickPaint
            )
        }
    }

    fun setMode(mode: Mode) {
        tickPaint.apply {
            if (mode.isAmbient) {
                inAmbientMode()
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