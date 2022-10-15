package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import androidx.wear.watchface.DrawMode
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.inAmbientMode
import com.vlad1m1r.watchface.utils.inInteractiveMode

class DrawCircle(
    private val circleData: CircleData,
    handsPaintProvider: HandPaintProvider
) {

    private var paint = handsPaintProvider.getCirclePaint(circleData)

    operator fun invoke(canvas: Canvas, center: Point, drawMode: DrawMode) {
        if (drawMode == DrawMode.AMBIENT) {
            paint.inAmbientMode(circleData.colorAmbient, circleData.useAntiAliasingInAmbientMode)
            paint.strokeWidth = 0f
        } else {
            paint.inInteractiveMode(
                circleData.color,
                circleData.shadowColor,
                circleData.shadowRadius.toFloat()
            )
            paint.strokeWidth = circleData.width.toFloat()
        }

        canvas.drawCircle(
            center.x,
            center.y,
            circleData.radius.toFloat(),
            paint
        )
    }
}