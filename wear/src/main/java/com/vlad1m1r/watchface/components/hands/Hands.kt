package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import android.graphics.Color
import androidx.wear.watchface.DrawMode
import com.vlad1m1r.watchface.data.state.HandsState
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.hoursRotation
import com.vlad1m1r.watchface.utils.minutesRotation
import com.vlad1m1r.watchface.utils.secondsRotation
import java.time.ZonedDateTime
import javax.inject.Inject

class Hands @Inject constructor(
    private val getHandData: GetHandData
) {

    private var showSecondsHand = true
    private var showMinutesHand = true
    private var showHoursHand = true

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

    fun draw(canvas: Canvas, zonedDateTime: ZonedDateTime, drawMode: DrawMode, center: Point) {
        val secondColor = state.secondsHand.color
        val minuteColor = state.minutesHand.color
        val hourColor = state.hoursHand.color

        showSecondsHand = drawMode != DrawMode.AMBIENT && secondColor != Color.TRANSPARENT
        showMinutesHand = minuteColor != Color.TRANSPARENT
        showHoursHand = hourColor != Color.TRANSPARENT

        val hoursRotation = zonedDateTime.hoursRotation()
        val minutesRotation = zonedDateTime.minutesRotation()

        if (showMinutesHand) {
            drawMinuteHand(canvas, minutesRotation, center, center.x, drawMode)
        }

        if (showHoursHand) {
            drawHourHand(canvas, hoursRotation, center, center.x, drawMode)
        }

        if (showSecondsHand) {
            val secondsRotation = zonedDateTime.secondsRotation()
            drawSecondsHand(canvas, secondsRotation, center, center.x, drawMode)
        }

        if (showSecondsHand || showHoursHand || showMinutesHand) {
            drawCircle(canvas, center, drawMode)
        }
    }
}