package com.vlad1m1r.watchface.components.ticks.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.components.ticks.usecase.AdjustTicks
import com.vlad1m1r.watchface.components.ticks.usecase.RoundCorners
import com.vlad1m1r.watchface.components.ticks.TickPaintProvider
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.state.TicksState
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.getLighterGrayscale
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.*

class TicksLayout3 @Inject constructor(
    @ApplicationContext context: Context,
    override val  tickPaintProvider: TickPaintProvider,
    override val adjustTicks: AdjustTicks,
    override val roundCorners: RoundCorners
) : TicksLayout(context) {

    private val tickLength = context.resources.getDimension(R.dimen.design3_tick_length)
    private var watchHourTickColor = 0
    private val tickWidth = context.resources.getDimension(R.dimen.design3_tick_width)

    private val tickLengthMinute = context.resources.getDimension(R.dimen.design3_tick_length_minute)
    private var watchMinuteTickColor = 0
    private val tickWidthMinute = context.resources.getDimension(R.dimen.design3_tick_width_minute)

    private val tickLengthMilli = context.resources.getDimension(R.dimen.design3_tick_length_milli)
    private val tickWidthMilli = context.resources.getDimension(R.dimen.design3_tick_width_milli)

    private val tickBurnInPadding = context.resources.getDimension(R.dimen.design3_tick_padding)
    private var tickPadding = tickBurnInPadding

    private lateinit var tickPaint: Paint
    private lateinit var tickPaintMinute: Paint
    private lateinit var tickPaintMilli: Paint

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
            val adjust = adjustTicks(tickRotation,
                center,
                bottomInset,
                isSquareScreen,
                state.shouldAdjustToSquareScreen
            )
            val roundCorners = if (state.shouldAdjustToSquareScreen) roundCorners(tickRotation, center, PI / 20) * 10 else 0.0

            val outerX = sin(tickRotation) * (outerTickRadius - roundCorners) * adjust
            val outerY = -cos(tickRotation) * (outerTickRadius - roundCorners) * adjust

            val innerTickRadius = if (tickIndex % 20 == 0) innerTickRadiusHour else if (tickIndex % 4 == 0) innerTickRadiusMinute else innerTickRadiusMilli
            val paint = if (tickIndex % 20 == 0) tickPaint else if (tickIndex % 4 == 0) tickPaintMinute else tickPaintMilli

            val innerX = sin(tickRotation) * (innerTickRadius - roundCorners) * adjust
            val innerY = -cos(tickRotation) * (innerTickRadius - roundCorners) * adjust

            canvas.drawLine(
                (center.x + innerX).toFloat(), (center.y + innerY).toFloat(),
                (center.x + outerX).toFloat(), (center.y + outerY).toFloat(), paint
            )
        }
    }

    override fun setMode(mode: Mode) {
        tickPaint.apply {
            if (mode.isAmbient) {
                tickPaintProvider.inAmbientMode(this, getLighterGrayscale(watchHourTickColor), state.useAntialiasingInAmbientMode)
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                tickPaintProvider.inInteractiveMode(this, watchHourTickColor)
                strokeWidth = tickWidth
            }
        }
        tickPaintMinute.apply {
            if (mode.isAmbient) {
                tickPaintProvider.inAmbientMode(this, getLighterGrayscale(watchMinuteTickColor), state.useAntialiasingInAmbientMode)
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                tickPaintProvider.inInteractiveMode(this, watchMinuteTickColor)
                strokeWidth = tickWidthMinute
            }
        }
        tickPaintMilli.apply {
            if (mode.isAmbient) {
                tickPaintProvider.inAmbientMode(this, getLighterGrayscale(watchMinuteTickColor), state.useAntialiasingInAmbientMode)
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                }
            } else {
                tickPaintProvider.inInteractiveMode(this, watchMinuteTickColor)
                strokeWidth = tickWidthMilli
            }
        }
        tickPadding = if (shouldAdjustForBurnInProtection(mode)) {
            tickBurnInPadding
        } else {
            0f
        }
        centerInvalidated = true
    }

    override fun setTicksState(ticksState: TicksState) {
        super.setTicksState(ticksState)
        this.watchHourTickColor = state.hourTicksColor
        this.watchMinuteTickColor = state.minuteTicksColor
        this.tickPaint = tickPaintProvider.getTickPaint(watchHourTickColor, tickWidth).apply {
            strokeCap = Paint.Cap.ROUND
        }
        this.tickPaintMinute = tickPaintProvider.getTickPaint(watchMinuteTickColor, tickWidthMinute)
        this.tickPaintMilli = tickPaintProvider.getTickPaint(watchMinuteTickColor, tickWidthMilli)
    }
}