package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import androidx.annotation.FloatRange
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.inAmbientMode
import com.vlad1m1r.watchface.utils.inInteractiveMode

class DrawHand(
    private val handData: HandData
) {

    private var paint = Paint().apply {
        color = handData.color
        strokeWidth = handData.width.toFloat()
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        setShadowLayer(
            handData.shadowRadius.toFloat(), 0f, 0f, handData.shadowColor
        )
    }

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
            paint.inAmbientMode(handData.colorAmbient)
        } else {
            paint.inInteractiveMode(handData.color, handData.shadowColor, handData.shadowRadius)
        }
    }
}