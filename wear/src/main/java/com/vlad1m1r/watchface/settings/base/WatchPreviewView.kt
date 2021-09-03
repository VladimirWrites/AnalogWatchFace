package com.vlad1m1r.watchface.settings.base

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.vlad1m1r.watchface.components.background.DrawBackground
import com.vlad1m1r.watchface.components.hands.*
import com.vlad1m1r.watchface.model.Point
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WatchPreviewView: View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    @Inject
    lateinit var drawBackground: DrawBackground

    @Inject
    lateinit var getHandData: GetHandData

    private var isInAmbientMode = false

    private lateinit var drawSecondsHand: DrawHand
    private lateinit var drawHourHand: DrawHand
    private lateinit var drawMinutesHand: DrawHand
    private lateinit var drawCircle: DrawCircle

    private lateinit var center: Point
    private lateinit var adjustedCenter: Point

    private val handPaintProvider = HandPaintProvider()

    fun invalidate(
        center: Point
    ) {
        this.center = center
        this.drawSecondsHand = DrawHand(getHandData.getSecondHandData(), handPaintProvider)
        this.drawHourHand = DrawHand(getHandData.getHourHandData(), handPaintProvider)
        this.drawMinutesHand = DrawHand(getHandData.getMinuteHandData(), handPaintProvider)
        this.drawCircle = DrawCircle(getHandData.getCircleData(), handPaintProvider)

        this.drawBackground.setCenter(center)
        this.adjustedCenter = Point(center.x/2, center.y)

        refreshMode()

        this.setOnClickListener {
            switchMode()
        }
    }

    private fun switchMode() {
        isInAmbientMode = !isInAmbientMode
        refreshMode()
    }

    private fun refreshMode() {
        drawHourHand.setInAmbientMode(isInAmbientMode)
        drawMinutesHand.setInAmbientMode(isInAmbientMode)
        drawSecondsHand.setInAmbientMode(isInAmbientMode)
        drawCircle.setInAmbientMode(isInAmbientMode)
        drawBackground.setInAmbientMode(isInAmbientMode)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(::drawBackground.isInitialized) {
            drawBackground(canvas)
        }
        if(::drawMinutesHand.isInitialized) {
            drawMinutesHand(canvas, 100f, adjustedCenter, center.x)
        }
        if(::drawHourHand.isInitialized) {
            drawHourHand(canvas, 80f, adjustedCenter, center.x)
        }
        if(::drawSecondsHand.isInitialized) {
            drawSecondsHand(canvas, 90f, adjustedCenter, center.x)
        }
        if(::drawCircle.isInitialized) {
            drawCircle(canvas, adjustedCenter)
        }
    }
}