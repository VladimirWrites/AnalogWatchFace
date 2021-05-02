package com.vlad1m1r.watchface.components.hands

import android.graphics.Paint

class HandPaintProvider() {

    fun getCirclePaint(circleData: CircleData): Paint {
        return Paint().apply {
            color = circleData.color
            strokeWidth = circleData.width.toFloat()
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            setShadowLayer(
                circleData.shadowRadius.toFloat(), 0f, 0f, circleData.shadowColor
            )
        }
    }

    fun getHandPaint(handData: HandData): Paint {
        return Paint().apply {
            color = handData.color
            strokeWidth = handData.width.toFloat()
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            setShadowLayer(
                handData.shadowRadius.toFloat(), 0f, 0f, handData.shadowColor
            )
        }
    }
}