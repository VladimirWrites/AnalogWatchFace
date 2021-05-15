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

class TicksLayout3(
    context: Context,
    private val dataStorage: DataStorage,
    colorStorage: ColorStorage
) : TicksLayout(context, dataStorage) {

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
    private var innerTickRadiusHour: Float = 0f
    private var innerTickRadiusMinute: Float = 0f
    private var innerTickRadiusMilli: Float = 0f

    override fun setCenter(center: Point) {
        centerInvalidated = false
        this.center = center
        this.outerTickRadius = center.x - tickPadding
        this.innerTickRadiusHour = outerTickRadius - tickLength
        this.innerTickRadiusMinute = outerTickRadius - tickLengthMinute
        this.innerTickRadiusMilli = outerTickRadius - tickLengthMilli
    }

    override fun draw(canvas: Canvas) {


        for (tickIndex in 0..239) {

            val tickRotation = tickIndex * PI / 120
            val adjust = if (shouldAdjustToSquareScreen) adjustToSquare(tickRotation, center) else 1.0
            val roundCorners = if (shouldAdjustToSquareScreen) roundCorners(tickRotation, center, PI / 20) * 10 else 0.0

            val sinTickRotation = sin(tickRotation)
            val cosTickRotation = -cos(tickRotation)

            val outerX = sinTickRotation * (outerTickRadius - roundCorners) * adjust
            val outerY = cosTickRotation * (outerTickRadius - roundCorners) * adjust

            val innerTickRadius = if (tickIndex % 20 == 0) innerTickRadiusHour else if (tickIndex % 4 == 0) innerTickRadiusMinute else innerTickRadiusMilli
            val paint = if (tickIndex % 20 == 0) tickPaint else if (tickIndex % 4 == 0) tickPaintMinute else tickPaintMilli

            val innerX = sinTickRotation * (innerTickRadius - roundCorners) * adjust
            val innerY = cosTickRotation * (innerTickRadius - roundCorners) * adjust

            canvas.drawLine(
                (center.x + innerX).toFloat(), (center.y + innerY).toFloat(),
                (center.x + outerX).toFloat(), (center.y + outerY).toFloat(), paint
            )
        }
    }

    override fun setMode(mode: Mode) {
        tickPaint.apply {
            if (mode.isAmbient) {
                inAmbientMode(getLighterGrayscale(watchHourTickColor), dataStorage.useAntiAliasingInAmbientMode())
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                inInteractiveMode(watchHourTickColor, shadowColor, shadowRadius)
                strokeWidth = tickWidth
            }
        }
        tickPaintMinute.apply {
            if (mode.isAmbient) {
                inAmbientMode(getLighterGrayscale(watchMinuteTickColor), dataStorage.useAntiAliasingInAmbientMode())
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                inInteractiveMode(watchMinuteTickColor, shadowColor, shadowRadius)
                strokeWidth = tickWidthMinute
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