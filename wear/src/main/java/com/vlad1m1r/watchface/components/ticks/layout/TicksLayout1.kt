package com.vlad1m1r.watchface.components.ticks.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.wear.watchface.DrawMode
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.components.ticks.usecase.AdjustTicks
import com.vlad1m1r.watchface.components.ticks.usecase.RoundCorners
import com.vlad1m1r.watchface.components.ticks.TickPaintProvider
import com.vlad1m1r.watchface.data.state.TicksState
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.getLighterGrayscale
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.*

class TicksLayout1 @Inject constructor(
    @ApplicationContext context: Context,
    override val  tickPaintProvider: TickPaintProvider,
    override val adjustTicks: AdjustTicks,
    override val roundCorners: RoundCorners
) : TicksLayout(context) {

    private val tickLength = context.resources.getDimension(R.dimen.design1_tick_length)
    private var watchHourTickColor = 0
    private val tickWidth = context.resources.getDimension(R.dimen.design1_tick_width)

    private val tickLengthMinute = context.resources.getDimension(R.dimen.design1_tick_length_minute)
    private var watchMinuteTickColor = 0
    private val tickWidthMinute = context.resources.getDimension(R.dimen.design1_tick_width_minute)

    private val tickBurnInPadding = context.resources.getDimension(R.dimen.design1__tick_padding)
    private var tickPadding = tickBurnInPadding

    private lateinit var tickPaint: Paint
    private lateinit var tickPaintMinute:Paint

    private var center = Point()
    private var outerTickRadius: Float = 0f
    private var innerTickRadiusHours: Float = 0f
    private var innerTickRadiusMinute: Float = 0f

    override fun draw(canvas: Canvas, drawMode: DrawMode, center: Point) {

        this.outerTickRadius = center.x - tickPadding
        this.innerTickRadiusHours = center.x - tickLength - tickPadding
        this.innerTickRadiusMinute = center.x - tickLengthMinute - tickPadding

        tickPaint.apply {
            if (drawMode == DrawMode.AMBIENT) {
                tickPaintProvider.inAmbientMode(this, getLighterGrayscale(watchHourTickColor), state.useAntialiasingInAmbientMode)
                strokeWidth = 0f
            } else {
                tickPaintProvider.inInteractiveMode(this, watchHourTickColor)
                strokeWidth = tickWidth
            }
        }
        tickPaintMinute.apply {
            if (drawMode == DrawMode.AMBIENT) {
                tickPaintProvider.inAmbientMode(this, getLighterGrayscale(watchMinuteTickColor), state.useAntialiasingInAmbientMode)
                strokeWidth = 0f
            } else {
                tickPaintProvider.inInteractiveMode(this, watchMinuteTickColor)
                strokeWidth = tickWidthMinute
            }
        }
        tickPadding = if (shouldAdjustForBurnInProtection(drawMode)) {
            tickBurnInPadding
        } else {
            -2f
        }


        for (tickIndex in 0..59) {
            val tickRotation = tickIndex * PI / 30
            val adjust = adjustTicks(tickRotation,
                center,
                bottomInset,
                isSquareScreen,
                state.shouldAdjustToSquareScreen
            )
            val roundCorners = if (state.shouldAdjustToSquareScreen) roundCorners(tickRotation, center, PI / 20) * 10 else 0.0

            val outerX = sin(tickRotation) * (outerTickRadius - roundCorners) * adjust
            val outerY = -cos(tickRotation) * (outerTickRadius - roundCorners) * adjust

            val innerTickRadius = if (tickIndex % 5 == 0) innerTickRadiusHours else innerTickRadiusMinute
            val paint = if (tickIndex % 5 == 0) tickPaint else tickPaintMinute

            val innerX = sin(tickRotation) * (innerTickRadius - roundCorners) * adjust
            val innerY = -cos(tickRotation) * (innerTickRadius - roundCorners) * adjust

            canvas.drawLine(
                (center.x + innerX).toFloat(), (center.y + innerY).toFloat(),
                (center.x + outerX).toFloat(), (center.y + outerY).toFloat(), paint
            )
        }
    }

    override fun setTicksState(ticksState: TicksState) {
        super.setTicksState(ticksState)
        this.watchHourTickColor = state.hourTicksColor
        this.watchMinuteTickColor = state.minuteTicksColor
        this.tickPaint = tickPaintProvider.getTickPaint(watchHourTickColor, tickWidth)
        this.tickPaintMinute = tickPaintProvider.getTickPaint(watchMinuteTickColor, tickWidthMinute)
    }
}