package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.inAmbientMode
import com.vlad1m1r.watchface.utils.inInteractiveMode

class DrawHand(
    private val handData: HandData,
    handPaintProvider: HandPaintProvider
) {

    private var paint = handPaintProvider.getHandPaint(handData)

    operator fun invoke(canvas: Canvas, rotation:Float, center: Point, handSpace: Float) {
        val handLength = (handSpace - handData.paddingFromCenter) * handData.handLengthRatio
        canvas.rotate(rotation, center.x, center.y)
        canvas.drawLine(
            center.x,
            center.y - handData.paddingFromCenter,
            center.x,
            center.y - handData.paddingFromCenter - handLength,
            paint
        )
        canvas.rotate(-rotation, center.x, center.y)
    }

    fun setInAmbientMode(isInAmbientMode: Boolean) {
        if(isInAmbientMode) {
            paint.inAmbientMode(handData.colorAmbient, handData.useAntiAliasingInAmbientMode)
        } else {
            paint.inInteractiveMode(handData.color, handData.shadowColor, handData.shadowRadius.toFloat())
        }
    }
}