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

class TicksLayout2 @Inject constructor(
    @ApplicationContext context: Context,
    override val  tickPaintProvider: TickPaintProvider,
    override val adjustTicks: AdjustTicks,
    override val roundCorners: RoundCorners
) : TicksLayout(context) {

    private val tickRadius = context.resources.getDimension(R.dimen.design2_tick_radius)
    private var watchHourTickColor = 0

    private val tickRadiusMinute = context.resources.getDimension(R.dimen.design2_tick_radius_minute)
    private var watchMinuteTickColor = 0

    private val tickPadding = context.resources.getDimension(R.dimen.design2_tick_padding)

    private lateinit var tickPaint: Paint
    private lateinit var tickPaintMinute: Paint

    private var outerTickRadius: Float = 0f

    override fun draw(canvas: Canvas, drawMode: DrawMode, center: Point) {

        this.outerTickRadius = center.x - tickPadding

        tickPaint.apply {
            if (drawMode == DrawMode.AMBIENT) {
                tickPaintProvider.inAmbientMode(this, getLighterGrayscale(watchHourTickColor), state.useAntialiasingInAmbientMode)
                strokeWidth = 0f
                style = Paint.Style.STROKE
            } else {
                tickPaintProvider.inInteractiveMode(this, watchHourTickColor)
                style = Paint.Style.FILL
            }
        }
        tickPaintMinute.apply {
            if (drawMode == DrawMode.AMBIENT) {
                tickPaintProvider.inAmbientMode(this, getLighterGrayscale(watchMinuteTickColor), state.useAntialiasingInAmbientMode)
                strokeWidth = 0f
                style = Paint.Style.STROKE
            } else {
                tickPaintProvider.inInteractiveMode(this, watchMinuteTickColor)
                style = Paint.Style.FILL
            }
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

            val radius = if (tickIndex % 5 == 0) tickRadius else tickRadiusMinute
            val paint = if (tickIndex % 5 == 0) tickPaint else tickPaintMinute

            val x = sin(tickRotation) * (outerTickRadius - roundCorners) * adjust
            val y = -cos(tickRotation) * (outerTickRadius - roundCorners) * adjust

            canvas.drawCircle(
                (center.x + x).toFloat(), (center.y + y).toFloat(),
                radius, paint
            )
        }
    }

    override fun setTicksState(ticksState: TicksState) {
        super.setTicksState(ticksState)
        this.watchHourTickColor = state.hourTicksColor
        this.watchMinuteTickColor = state.minuteTicksColor
        this.tickPaint = tickPaintProvider.getRoundTickPaint(watchHourTickColor)
        this.tickPaintMinute = tickPaintProvider.getRoundTickPaint(watchMinuteTickColor)
    }
}