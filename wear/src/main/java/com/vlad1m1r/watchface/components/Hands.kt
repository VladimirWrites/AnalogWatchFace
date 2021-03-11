package com.vlad1m1r.watchface.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.*
import java.util.*

class Hands(
    context: Context,
    private val colorStorage: ColorStorage
) : WatchView(context) {

    private var secondColor = colorStorage.getSecondsHandColor()
    private var minuteColor = colorStorage.getMinutesHandColor()
    private var hourColor = colorStorage.getHoursHandColor()
    private var circleColor = colorStorage.getCentralCircleColor()

    private val handWidthSecond = context.resources.getDimension(R.dimen.hand_width_second)
    private val handWidthMinute = context.resources.getDimension(R.dimen.hand_width_minute)
    private val handWidthHour = context.resources.getDimension(R.dimen.hand_width_hour)

    private val middleCircleRadius = context.resources.getDimension(R.dimen.middle_circle_radius)
    private val circleHandGap = context.resources.getDimension(R.dimen.circle_hand_gap)

    private var showSecondsHand = true
    private var showMinutesHand = true
    private var showHoursHand = true

    private var hourPaint = Paint().apply {
        color = hourColor
        strokeWidth = handWidthHour
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }
    private var minutePaint = Paint().apply {
        color = minuteColor
        strokeWidth = handWidthMinute
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }

    private var secondPaint = Paint().apply {
        color = secondColor
        strokeWidth = handWidthSecond
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }

    private var circlePaint = Paint().apply {
        color = circleColor
        strokeWidth = handWidthSecond
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }

    private var center = Point()

    private var secondHandLength = 0f
    private var minuteHandLength = 0f
    private var hourHandLength = 0f

    override fun setCenter(center: Point) {
        this.center = center
        secondHandLength = (center.x * 0.875).toFloat()
        minuteHandLength = (center.x * 0.75).toFloat()
        hourHandLength = (center.x * 0.5).toFloat()
    }

    fun draw(canvas: Canvas, calendar: Calendar) {
        val hoursRotation = calendar.hoursRotation()
        val minutesRotation = calendar.minutesRotation()

        if(showMinutesHand) {
            canvas.rotate(minutesRotation, center.x, center.y)
            canvas.drawLine(
                center.x,
                center.y - middleCircleRadius - circleHandGap,
                center.x,
                center.y - minuteHandLength,
                minutePaint
            )
            canvas.rotate(-minutesRotation, center.x, center.y)
        }

        if(showHoursHand) {
            canvas.rotate(hoursRotation, center.x, center.y)
            canvas.drawLine(
                center.x,
                center.y - middleCircleRadius - circleHandGap,
                center.x,
                center.y - hourHandLength,
                hourPaint
            )
            canvas.rotate(-hoursRotation, center.x, center.y)
        }

        if (showSecondsHand) {
            val secondsRotation = calendar.secondsRotation()
            canvas.rotate(secondsRotation, center.x, center.y)
            canvas.drawLine(
                center.x,
                center.y - middleCircleRadius,
                center.x,
                center.y - secondHandLength,
                secondPaint
            )
        }

        if(showSecondsHand || showHoursHand || showMinutesHand) {
            canvas.drawCircle(
                center.x,
                center.y,
                middleCircleRadius,
                circlePaint
            )
        }
    }

    fun setMode(mode: Mode) {

        showSecondsHand = !mode.isAmbient && secondColor != Color.TRANSPARENT
        showMinutesHand = minuteColor != Color.TRANSPARENT
        showHoursHand = hourColor != Color.TRANSPARENT

        if (mode.isAmbient) {
            hourPaint.inAmbientMode()
            minutePaint.inAmbientMode()
            secondPaint.inAmbientMode()
            circlePaint.inAmbientMode()
            if (mode.isBurnInProtection) {
                circlePaint.strokeWidth = 0f
            }
        } else {
            hourPaint.inInteractiveMode(hourColor)
            minutePaint.inInteractiveMode(minuteColor)
            secondPaint.inInteractiveMode(secondColor)
            circlePaint.inInteractiveMode(circleColor)
            circlePaint.strokeWidth = handWidthSecond
        }
    }

    fun invalidate() {
        secondColor = colorStorage.getSecondsHandColor()
        minuteColor = colorStorage.getMinutesHandColor()
        hourColor = colorStorage.getHoursHandColor()
        circleColor = colorStorage.getCentralCircleColor()
    }
}