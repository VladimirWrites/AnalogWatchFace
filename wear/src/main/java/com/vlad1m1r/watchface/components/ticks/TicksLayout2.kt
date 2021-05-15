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

    private val tickPaint = tickPaintProvider.getRoundTickPaint(watchHourTickColor)
    private val tickPaintMinute = tickPaintProvider.getRoundTickPaint(watchMinuteTickColor)

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
            val adjust = if (shouldAdjustToSquareScreen) adjustToSquare(tickRotation, center) else 1.0
            val roundCorners = if (shouldAdjustToSquareScreen) roundCorners(tickRotation, center, PI / 20) * 10 else 0.0

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

    override fun setMode(mode: Mode) {
        tickPaint.apply {
            if (mode.isAmbient) {
                tickPaintProvider.inAmbientMode(this, getLighterGrayscale(watchHourTickColor), dataStorage.useAntiAliasingInAmbientMode())
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                    style = Paint.Style.STROKE
                }
            } else {
                tickPaintProvider.inInteractiveMode(this, watchHourTickColor)
                style = Paint.Style.FILL
            }
        }
        tickPaintMinute.apply {
            if (mode.isAmbient) {
                tickPaintProvider.inAmbientMode(this, getLighterGrayscale(watchMinuteTickColor), dataStorage.useAntiAliasingInAmbientMode())
                if (mode.isBurnInProtection) {
                    strokeWidth = 0f
                    style = Paint.Style.STROKE
                }
            } else {
                tickPaintProvider.inInteractiveMode(this, watchMinuteTickColor)
                style = Paint.Style.FILL
            }
        }
    }
}