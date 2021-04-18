package com.vlad1m1r.watchface.settings.hands.hand

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.vlad1m1r.watchface.components.hands.*
import com.vlad1m1r.watchface.model.Point

class WatchPreviewView: View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    private var isInAmbientMode = false

    private lateinit var drawSecondsHand: DrawHand
    private lateinit var drawHourHand: DrawHand
    private lateinit var drawMinutesHand: DrawHand
    private lateinit var drawCircle: DrawCircle

    private lateinit var paintDataProvider: PaintDataProvider

    private lateinit var center: Point
    private lateinit var adjustedCenter: Point

    fun initialize(paintDataProvider: PaintDataProvider) {
        this.paintDataProvider = paintDataProvider
    }


    fun invalidate(
        center: Point
    ) {
        this.center = center
        this.drawSecondsHand = DrawHand(paintDataProvider.getSecondHandData())
        this.drawHourHand = DrawHand(paintDataProvider.getHourHandData())
        this.drawMinutesHand = DrawHand(paintDataProvider.getMinuteHandData())
        this.drawCircle = DrawCircle(paintDataProvider.getCircleData())
        this.adjustedCenter = Point(center.x/2, center.y)

        this.setOnClickListener {
            switchMode()
        }
    }

    private fun switchMode() {
        isInAmbientMode = !isInAmbientMode
        drawHourHand.setInAmbientMode(isInAmbientMode)
        drawMinutesHand.setInAmbientMode(isInAmbientMode)
        drawSecondsHand.setInAmbientMode(isInAmbientMode)
        drawCircle.setInAmbientMode(isInAmbientMode)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(::drawCircle.isInitialized) {
            drawCircle(canvas, adjustedCenter)
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
    }
}