package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import android.graphics.Paint
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.inAmbientMode
import com.vlad1m1r.watchface.utils.inInteractiveMode

class DrawCircle(
    private val circleData: CircleData
) {

    private var paint = Paint().apply {
        color = circleData.color
        strokeWidth = circleData.width.toFloat()
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        setShadowLayer(
            circleData.shadowRadius.toFloat(), 0f, 0f, circleData.shadowColor
        )
    }

    operator fun invoke(canvas: Canvas, center: Point) {
        canvas.drawCircle(
            center.x,
            center.y,
            circleData.radius.toFloat(),
            paint
        )
    }

    fun setInAmbientMode(isInAmbientMode: Boolean) {
        if(isInAmbientMode) {
            paint.inAmbientMode(circleData.colorAmbient)
        } else {
            paint.inInteractiveMode(circleData.color, circleData.shadowColor, circleData.shadowRadius)
        }
    }

    fun setInBurnInProtectionMode(isInBurnInProtectionMode: Boolean) {
        if (isInBurnInProtectionMode) {
            paint.strokeWidth = 0f
        } else {
            paint.strokeWidth = circleData.width.toFloat()
        }
    }
}