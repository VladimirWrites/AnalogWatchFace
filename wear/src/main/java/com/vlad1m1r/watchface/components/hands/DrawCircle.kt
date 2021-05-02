package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.inAmbientMode
import com.vlad1m1r.watchface.utils.inInteractiveMode

class DrawCircle(
    private val circleData: CircleData,
    handsPaintProvider: HandPaintProvider
) {

    private var paint = handsPaintProvider.getCirclePaint(circleData)

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