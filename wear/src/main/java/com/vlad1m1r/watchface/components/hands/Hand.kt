package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.FloatRange
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.inAmbientMode
import com.vlad1m1r.watchface.utils.inInteractiveMode

class Hand(
    private val handData: HandData
) {

    private var paint = Paint().apply {
        color = handData.color
        strokeWidth = handData.width
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        setShadowLayer(
            handData.shadowRadius, 0f, 0f, handData.shadowColor
        )
    }

    fun draw(canvas: Canvas, rotation:Float, center: Point) {
        val handLength = center.x * handData.handLengthRatio
        canvas.rotate(rotation, center.x, center.y)
        canvas.drawLine(
            center.x,
            center.y - handData.paddingFromCenter,
            center.x,
            center.y - handLength,
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