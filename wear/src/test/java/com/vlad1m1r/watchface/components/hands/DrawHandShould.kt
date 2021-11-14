package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import android.graphics.Paint
import com.vlad1m1r.watchface.model.Point
import org.junit.Test
import org.mockito.kotlin.*

class DrawHandShould {

    private val paintMock = mock<Paint>()
    private val handPaintProviderMock = mock<HandPaintProvider>().apply {
        whenever(this.getHandPaint(any())).thenReturn(paintMock)
    }

    private val canvasMock = mock<Canvas>()

    @Test
    fun drawRotateCanvasAroundCenter() {
        val handDataMock = mock<HandData>()
        val drawHand = DrawHand(
            handDataMock,
            handPaintProviderMock
        )
        val rotation = 91f
        val center = Point(11f, 22f)

        drawHand.invoke(
            canvasMock,
            rotation,
            center,
            0f
        )

        canvasMock.inOrder {
            verify().rotate(rotation, center.x, center.y)
            verify().drawLine(eq(center.x), any(), eq(center.x), any(), eq(paintMock))
            verify().rotate(-rotation, center.x, center.y)
        }
    }

    @Test
    fun drawHand() {
        val handData = HandData(
            color = 1,
            colorAmbient = 2,
            shadowColor = 3,
            shadowRadius = 4,
            width = 5,
            paddingFromCenter = 6,
            handLengthRatio = 0.5f,
            useAntiAliasingInAmbientMode = false
        )
        val drawHand = DrawHand(
            handData,
            handPaintProviderMock
        )
        val rotation = 91f
        val center = Point(11f, 22f)

        drawHand.invoke(
            canvasMock,
            rotation,
            center,
            10f
        )

        verify(canvasMock).drawLine(
            center.x,
            center.y - handData.paddingFromCenter,
            center.x,
            center.y - handData.paddingFromCenter - 2f,
            paintMock
        )
    }

    @Test
    fun switchToAmbientMode() {
        val handDataMock = mock<HandData>().apply {
            whenever(this.colorAmbient).thenReturn(128)
        }
        val drawHand = DrawHand(
            handDataMock,
            handPaintProviderMock
        )

        drawHand.setInAmbientMode(true)

        verify(paintMock).color = handDataMock.colorAmbient
        verify(paintMock).isAntiAlias = false
        verify(paintMock).clearShadowLayer()
    }

    @Test
    fun switchToInteractiveMode() {
        val handDataMock = mock<HandData>().apply {
            whenever(this.color).thenReturn(256)
            whenever(this.shadowRadius).thenReturn(10)
            whenever(this.shadowColor).thenReturn(512)
        }
        val drawHand = DrawHand(
            handDataMock,
            handPaintProviderMock
        )

        drawHand.setInAmbientMode(false)

        verify(paintMock).color = handDataMock.color
        verify(paintMock).isAntiAlias = true
        verify(paintMock).setShadowLayer(
            handDataMock.shadowRadius.toFloat(), 0f, 0f, handDataMock.shadowColor
        )
    }
}