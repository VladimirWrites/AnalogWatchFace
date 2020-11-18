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

class TicksLayout3(context: Context, colorStorage: ColorStorage) : WatchView(context), Ticks {

    private val tickLength = context.resources.getDimension(R.dimen.design3_tick_length)
    private val watchHourTickColor = colorStorage.getHourTicksColor()
    private val tickWidth = context.resources.getDimension(R.dimen.design3_tick_width)

    private val tickLengthMinute = context.resources.getDimension(R.dimen.design3_tick_length_minute)
    private val watchMinuteTickColor = colorStorage.getMinuteTicksColor()
    private val tickWidthMinute = context.resources.getDimension(R.dimen.design3_tick_width_minute)

    private val tickLengthMilli = context.resources.getDimension(R.dimen.design3_tick_length_milli)
    private val tickWidthMilli = context.resources.getDimension(R.dimen.design3_tick_width_milli)

    private val tickBurnInPadding = context.resources.getDimension(R.dimen.design3_tick_padding)
    private var tickPadding = tickBurnInPadding

    override var centerInvalidated = true
        private set

    private val tickPaint = Paint().apply {
        color = watchHourTickColor
        strokeWidth = tickWidth
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }
    private val tickPaintMinute = Paint().apply {
        color = watchMinuteTickColor
        strokeWidth = tickWidthMinute
        isAntiAlias = true
        style = Paint.Style.STROKE
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }

    private val tickPaintMilli = Paint().apply {
        color = watchMinuteTickColor
        strokeWidth = tickWidthMilli
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
    private var innerTickRadiusMilli: Float = 0f

    override fun setCenter(center: Point) {
        centerInvalidated = false
        this.center = center
        this.outerTickRadius = center.x - tickPadding
        this.innerTickRadius = outerTickRadius - tickLength
        this.innerTickRadiusMinute = outerTickRadius - tickLengthMinute
        this.innerTickRadiusMilli = outerTickRadius - tickLengthMilli
    }

    override fun draw(canvas: Canvas) {


        for (tickIndex in 0..239) {

            val tickRotation = tickIndex * PI / 120

            val sinTickRotation = sin(tickRotation)
            val cosTickRotation = -cos(tickRotation)

            val outerX = sinTickRotation * outerTickRadius
            val outerY = cosTickRotation * outerTickRadius


            when {
                tickIndex % 20 == 0 -> {
                    val innerX = sinTickRotation * innerTickRadius
                    val innerY = cosTickRotation * innerTickRadius
                    canvas.drawLine(
                        (center.x + innerX).toFloat(), (center.y + innerY).toFloat(),
                        (center.x + outerX).toFloat(), (center.y + outerY).toFloat(), tickPaint
                    )

                }
                tickIndex % 4 == 0 -> {
                    val innerX = sinTickRotation * innerTickRadiusMinute
                    val innerY = cosTickRotation * innerTickRadiusMinute
                    canvas.drawLine(
                        (center.x + innerX).toFloat(), (center.y + innerY).toFloat(),
                        (center.x + outerX).toFloat(), (center.y + outerY).toFloat(), tickPaintMinute
                    )
                }
                else -> {
                    val innerXMilli = sinTickRotation * innerTickRadiusMilli
                    val innerYMilli = cosTickRotation * innerTickRadiusMilli

                    canvas.drawLine(
                        (center.x + innerXMilli).toFloat(), (center.y + innerYMilli).toFloat(),
                        (center.x + outerX).toFloat(), (center.y + outerY).toFloat(), tickPaintMilli
                    )
                }
            }
        }
    }

    override fun setMode(mode: Mode) {
        tickPaint.apply {
            if (mode.isAmbient) {
                inAmbientMode(getLighterGrayscale(watchHourTickColor))
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                inInteractiveMode(watchHourTickColor)
                strokeWidth = tickWidth
            }
        }
        tickPaintMinute.apply {
            if (mode.isAmbient) {
                inAmbientMode(getLighterGrayscale(watchMinuteTickColor))
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                inInteractiveMode(watchMinuteTickColor)
                strokeWidth = tickWidthMinute
            }
        }
        tickPadding = if (mode.isAmbient && mode.isBurnInProtection) {
            tickBurnInPadding
        } else {
            0f
        }
        centerInvalidated = true
    }
}