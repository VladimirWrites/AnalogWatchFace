package com.vlad1m1r.watchface.settings.base

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.wear.watchface.DrawMode
import com.vlad1m1r.watchface.components.background.DrawBackground
import com.vlad1m1r.watchface.components.hands.*
import com.vlad1m1r.watchface.data.state.WatchFaceState
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

    private lateinit var watchFaceState: WatchFaceState

    fun invalidate(
        center: Point,
        watchFaceState: WatchFaceState
    ) {
        this.watchFaceState = watchFaceState
        this.center = center
        this.drawSecondsHand = DrawHand(getHandData.getSecondHandData(watchFaceState.handsState), handPaintProvider)
        this.drawHourHand = DrawHand(getHandData.getHourHandData(watchFaceState.handsState), handPaintProvider)
        this.drawMinutesHand = DrawHand(getHandData.getMinuteHandData(watchFaceState.handsState), handPaintProvider)
        this.drawCircle = DrawCircle(getHandData.getCircleData(watchFaceState.handsState), handPaintProvider)

        this.adjustedCenter = Point(center.x/2, center.y)

        this.setOnClickListener {
            switchMode()
        }
    }

    private fun switchMode() {
        isInAmbientMode = !isInAmbientMode
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if(::watchFaceState.isInitialized) {
            val drawMode = if (isInAmbientMode) DrawMode.AMBIENT else DrawMode.INTERACTIVE

            if (::drawBackground.isInitialized) {
                drawBackground(canvas, drawMode, center, watchFaceState.backgroundState)
            }
            if (::drawMinutesHand.isInitialized) {
                drawMinutesHand(canvas, 100f, adjustedCenter, center.x, drawMode)
            }
            if (::drawHourHand.isInitialized) {
                drawHourHand(canvas, 80f, adjustedCenter, center.x, drawMode)
            }
            if (::drawSecondsHand.isInitialized) {
                drawSecondsHand(canvas, 90f, adjustedCenter, center.x, drawMode)
            }
            if (::drawCircle.isInitialized) {
                drawCircle(canvas, adjustedCenter, drawMode)
            }
        }
    }
}