package com.vlad1m1r.watchface.components.hands

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.WatchView
import com.vlad1m1r.watchface.utils.hoursRotation
import com.vlad1m1r.watchface.utils.minutesRotation
import com.vlad1m1r.watchface.utils.secondsRotation
import java.util.*

class Hands(
    context: Context,
    private val colorStorage: ColorStorage
) : WatchView {

    private val paintDataProvider = PaintDataProvider(context, colorStorage)

    private var showSecondsHand = true
    private var showMinutesHand = true
    private var showHoursHand = true

    private var center = Point()

    private lateinit var hourHand: Hand
    private lateinit var minuteHand: Hand
    private lateinit var secondsHand: Hand
    private lateinit var circle: Circle

    init {
        initializeHands()
    }

    private fun initializeHands() {
        hourHand = Hand(
            paintDataProvider.getHourHandData()
        )

        minuteHand = Hand(
            paintDataProvider.getMinuteHandData()
        )

        secondsHand = Hand(
            paintDataProvider.getSecondHandData()
        )

        circle = Circle(
            paintDataProvider.getCircleData()
        )
    }

    override fun setCenter(center: Point) {
        this.center = center
    }

    fun draw(canvas: Canvas, calendar: Calendar) {

        val hoursRotation = calendar.hoursRotation()
        val minutesRotation = calendar.minutesRotation()

        if(showMinutesHand) {
            minuteHand.draw(canvas, minutesRotation, center)
        }

        if(showHoursHand) {
            hourHand.draw(canvas, hoursRotation, center)
        }

        if (showSecondsHand) {
            val secondsRotation = calendar.secondsRotation()
            secondsHand.draw(canvas, secondsRotation, center)
        }

        if(showSecondsHand || showHoursHand || showMinutesHand) {
            circle.draw(canvas, center)
        }
    }

    fun setMode(mode: Mode) {
        val secondColor = colorStorage.getSecondsHandColor()
        val minuteColor = colorStorage.getMinutesHandColor()
        val hourColor = colorStorage.getHoursHandColor()

        showSecondsHand = !mode.isAmbient && secondColor != Color.TRANSPARENT
        showMinutesHand = minuteColor != Color.TRANSPARENT
        showHoursHand = hourColor != Color.TRANSPARENT

        hourHand.setInAmbientMode(mode.isAmbient)
        minuteHand.setInAmbientMode(mode.isAmbient)
        secondsHand.setInAmbientMode(mode.isAmbient)

        circle.setInAmbientMode(mode.isAmbient)
        circle.setInBurnInProtectionMode(mode.isBurnInProtection && mode.isAmbient)
    }

    fun invalidate() {
        initializeHands()
    }
}