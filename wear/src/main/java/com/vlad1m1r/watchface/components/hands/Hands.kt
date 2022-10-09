package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import android.graphics.Color
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.state.HandsState
import com.vlad1m1r.watchface.data.state.WatchFaceState
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.WatchView
import com.vlad1m1r.watchface.utils.hoursRotation
import com.vlad1m1r.watchface.utils.minutesRotation
import com.vlad1m1r.watchface.utils.secondsRotation
import java.time.ZonedDateTime
import java.util.*
import javax.inject.Inject

class Hands @Inject constructor(
    private val  getHandData: GetHandData
) : WatchView {

    private var showSecondsHand = true
    private var showMinutesHand = true
    private var showHoursHand = true

    private var center = Point()
    private lateinit var state: HandsState

    private lateinit var drawHourHand: DrawHand
    private lateinit var drawMinuteHand: DrawHand
    private lateinit var drawSecondsHand: DrawHand
    private lateinit var drawCircle: DrawCircle

    fun setState(state: HandsState) {
        this.state = state
        initializeHands()
    }

    private fun initializeHands() {
        val handPaintProvider = HandPaintProvider()

        drawHourHand = DrawHand(
            getHandData.getHourHandData(state),
            handPaintProvider
        )

        drawMinuteHand = DrawHand(
            getHandData.getMinuteHandData(state),
            handPaintProvider
        )

        drawSecondsHand = DrawHand(
            getHandData.getSecondHandData(state),
            handPaintProvider
        )

        drawCircle = DrawCircle(
            getHandData.getCircleData(state),
            handPaintProvider
        )
    }

    override fun setCenter(center: Point) {
        this.center = center
    }

    fun draw(canvas: Canvas, calendar: Calendar) {

        val hoursRotation = calendar.hoursRotation()
        val minutesRotation = calendar.minutesRotation()

        if(showMinutesHand) {
            drawMinuteHand(canvas, minutesRotation, center, center.x)
        }

        if(showHoursHand) {
            drawHourHand(canvas, hoursRotation, center, center.x)
        }

        if (showSecondsHand) {
            val secondsRotation = calendar.secondsRotation()
            drawSecondsHand(canvas, secondsRotation, center, center.x)
        }

        if(showSecondsHand || showHoursHand || showMinutesHand) {
            drawCircle(canvas, center)
        }
    }

    fun draw(canvas: Canvas, zonedDateTime: ZonedDateTime) {

        val hoursRotation = zonedDateTime.hoursRotation()
        val minutesRotation = zonedDateTime.minutesRotation()

        if(showMinutesHand) {
            drawMinuteHand(canvas, minutesRotation, center, center.x)
        }

        if(showHoursHand) {
            drawHourHand(canvas, hoursRotation, center, center.x)
        }

        if (showSecondsHand) {
            val secondsRotation = zonedDateTime.secondsRotation()
            drawSecondsHand(canvas, secondsRotation, center, center.x)
        }

        if(showSecondsHand || showHoursHand || showMinutesHand) {
            drawCircle(canvas, center)
        }
    }

    fun setMode(mode: Mode) {
        val secondColor = state.secondsHand.color
        val minuteColor = state.minutesHand.color
        val hourColor = state.hoursHand.color

        showSecondsHand = !mode.isAmbient && secondColor != Color.TRANSPARENT
        showMinutesHand = minuteColor != Color.TRANSPARENT
        showHoursHand = hourColor != Color.TRANSPARENT

        drawHourHand.setInAmbientMode(mode.isAmbient)
        drawMinuteHand.setInAmbientMode(mode.isAmbient)
        drawSecondsHand.setInAmbientMode(mode.isAmbient)

        drawCircle.setInAmbientMode(mode.isAmbient)
        drawCircle.setInBurnInProtectionMode(mode.isBurnInProtection && mode.isAmbient)
    }
}