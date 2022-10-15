package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import android.graphics.Paint
import androidx.wear.watchface.DrawMode
import com.vlad1m1r.watchface.model.Point
import org.junit.Test
import org.mockito.kotlin.*

class DrawHandShould {

    private val paintMock = mock<Paint>()
    private val handPaintProviderMock = mock<HandPaintProvider>().apply {
        whenever(this.getHandPaint(any())).thenReturn(paintMock)
    }

    private val canvasMock = mock<Canvas>()

    private val handData = HandData(
        color = 1,
        colorAmbient = 2,
        shadowColor = 3,
        shadowRadius = 4,
        width = 5,
        paddingFromCenter = 6,
        handLengthRatio = 0.5f,
        useAntiAliasingInAmbientMode = false
    )

    private val rotation = 91f
    private val center = Point(11f, 22f)

    @Test
    fun drawRotateCanvasAroundCenter() {
        val handDataMock = mock<HandData>()
        val drawHand = DrawHand(
            handDataMock,
            handPaintProviderMock
        )

        drawHand.invoke(
            canvasMock,
            rotation,
            center,
            0f,
            DrawMode.INTERACTIVE
        )

        canvasMock.inOrder {
            verify().rotate(rotation, center.x, center.y)
            verify().drawLine(eq(center.x), any(), eq(center.x), any(), eq(paintMock))
            verify().rotate(-rotation, center.x, center.y)
        }
    }

    @Test
    fun drawHand() {

        val drawHand = DrawHand(
            handData,
            handPaintProviderMock
        )

        drawHand.invoke(
            canvasMock,
            rotation,
            center,
            10f,
            DrawMode.INTERACTIVE
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
        val drawHand = DrawHand(
            handData,
            handPaintProviderMock
        )

        drawHand.invoke(
            canvasMock,
            rotation,
            center,
            10f,
            DrawMode.AMBIENT
        )

        verify(paintMock).color = handData.colorAmbient
        verify(paintMock).isAntiAlias = false
        verify(paintMock).clearShadowLayer()
    }

    @Test
    fun switchToInteractiveMode() {
        val drawHand = DrawHand(
            handData,
            handPaintProviderMock
        )

        drawHand.invoke(
            canvasMock,
            rotation,
            center,
            10f,
            DrawMode.INTERACTIVE
        )
        verify(paintMock).color = handData.color
        verify(paintMock).isAntiAlias = true
        verify(paintMock).setShadowLayer(
            handData.shadowRadius.toFloat(), 0f, 0f, handData.shadowColor
        )
    }
}