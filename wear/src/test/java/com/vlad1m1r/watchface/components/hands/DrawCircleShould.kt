package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import android.graphics.Paint
import com.vlad1m1r.watchface.model.Point
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DrawCircleShould {

    private val paintMock = mock<Paint>()
    private val handPaintProviderMock = mock<HandPaintProvider>().apply {
        whenever(this.getCirclePaint(any())).thenReturn(paintMock)
    }

    private val canvasMock = mock<Canvas>()

    @Test
    fun drawCircle() {
        val circleData = CircleData(
            color = 1,
            colorAmbient = 2,
            shadowColor = 3,
            shadowRadius = 4,
            width = 5,
            radius = 6,
            useAntiAliasingInAmbientMode = false
        )
        val drawCircle = DrawCircle(
            circleData,
            handPaintProviderMock
        )
        val center = Point(11f, 22f)

        drawCircle.invoke(
            canvasMock,
            center
        )

        verify(canvasMock).drawCircle(
            center.x,
            center.y,
            circleData.radius.toFloat(),
            paintMock
        )
    }

    @Test
    fun switchToAmbientMode() {
        val circleDataMock = mock<CircleData>().apply {
            whenever(this.colorAmbient).thenReturn(128)
        }
        val drawCircle = DrawCircle(
            circleDataMock,
            handPaintProviderMock
        )

        drawCircle.setInAmbientMode(true)

        verify(paintMock).color = circleDataMock.colorAmbient
        verify(paintMock).isAntiAlias = false
        verify(paintMock).clearShadowLayer()
    }

    @Test
    fun switchToInteractiveMode() {
        val circleDataMock = mock<CircleData>().apply {
            whenever(this.color).thenReturn(256)
            whenever(this.shadowRadius).thenReturn(10)
            whenever(this.shadowColor).thenReturn(512)
        }
        val drawCircle = DrawCircle(
            circleDataMock,
            handPaintProviderMock
        )

        drawCircle.setInAmbientMode(false)

        verify(paintMock).color = circleDataMock.color
        verify(paintMock).isAntiAlias = true
        verify(paintMock).setShadowLayer(
            circleDataMock.shadowRadius.toFloat(), 0f, 0f, circleDataMock.shadowColor
        )
    }
}