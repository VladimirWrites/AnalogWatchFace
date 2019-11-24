package com.vlad1m1r.watchface.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R
import java.util.*

class Hands(context: Context) : WatchView(context) {

    private val secondColor = ContextCompat.getColor(context, R.color.watch_hand_second)
    private val minuteColor = ContextCompat.getColor(context, R.color.watch_hand_minute)
    private val hourColor = ContextCompat.getColor(context, R.color.watch_hand_hour)
    private val circleColor = ContextCompat.getColor(context, R.color.watch_tick)

    private val handWidthSecond = context.resources.getDimension(R.dimen.hand_width_second)
    private val handWidthMinute = context.resources.getDimension(R.dimen.hand_width_minute)
    private val handWidthHour = context.resources.getDimension(R.dimen.hand_width_hour)

    private val middleCircleRadius = context.resources.getDimension(R.dimen.middle_circle_radius)
    private val circleHandGap = context.resources.getDimension(R.dimen.circle_hand_gap)

    private var showSecondsHand = true

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
        val secondsRotation = calendar.secondsRotation()
        val minutesRotation = calendar.minutesRotation()
        val hoursRotation = calendar.hoursRotation()

        canvas.rotate(hoursRotation, center.x, center.y)
        canvas.drawLine(
            center.x,
            center.y - middleCircleRadius - circleHandGap,
            center.x,
            center.y - hourHandLength,
            hourPaint
        )

        canvas.rotate(minutesRotation - hoursRotation, center.x, center.y)
        canvas.drawLine(
            center.x,
            center.y - middleCircleRadius - circleHandGap,
            center.x,
            center.y - minuteHandLength,
            minutePaint
        )

        if (showSecondsHand) {
            canvas.rotate(secondsRotation - minutesRotation, center.x, center.y)
            canvas.drawLine(
                center.x,
                center.y - middleCircleRadius,
                center.x,
                center.y - secondHandLength,
                secondPaint
            )
        }

        canvas.drawCircle(
            center.x,
            center.y,
            middleCircleRadius,
            circlePaint
        )
    }

    fun setMode(mode: Mode) {
        if (mode.isAmbient) {
            showSecondsHand = false

            hourPaint.inAmbientMode()
            minutePaint.inAmbientMode()
            secondPaint.inAmbientMode()
            circlePaint.inAmbientMode()
            if (mode.isBurnInProtection) {
                circlePaint.strokeWidth = 0f
            }
        } else {
            showSecondsHand = true

            hourPaint.inInteractiveMode(hourColor)
            minutePaint.inInteractiveMode(minuteColor)
            secondPaint.inInteractiveMode(secondColor)
            circlePaint.inInteractiveMode(circleColor)
            circlePaint.strokeWidth = handWidthSecond
        }
    }
}