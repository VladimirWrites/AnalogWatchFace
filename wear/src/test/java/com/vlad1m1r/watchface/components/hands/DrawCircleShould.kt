package com.vlad1m1r.watchface.components.hands

import android.graphics.Canvas
import android.graphics.Paint
import androidx.wear.watchface.DrawMode
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
    private val center = Point(11f, 22f)

    private val circleData = CircleData(
        color = 1,
        colorAmbient = 2,
        shadowColor = 3,
        shadowRadius = 4,
        width = 5,
        radius = 6,
        useAntiAliasingInAmbientMode = false
    )

    private val drawCircle = DrawCircle(
        circleData,
        handPaintProviderMock
    )

    @Test
    fun drawCircle() {
        drawCircle.invoke(
            canvasMock,
            center,
            DrawMode.INTERACTIVE
        )

        verify(canvasMock).drawCircle(
            center.x,
            center.y,
            circleData.radius.toFloat(),
            paintMock
        )
    }

    @Test
    fun drawInAmbientMode() {
        drawCircle.invoke(
            canvasMock,
            center,
            DrawMode.AMBIENT
        )

        verify(paintMock).color = circleData.colorAmbient
        verify(paintMock).isAntiAlias = false
        verify(paintMock).clearShadowLayer()
    }

    @Test
    fun drawInInteractiveMode() {
        drawCircle.invoke(
            canvasMock,
            center,
            DrawMode.INTERACTIVE
        )

        verify(paintMock).color = circleData.color
        verify(paintMock).isAntiAlias = true
        verify(paintMock).setShadowLayer(
            circleData.shadowRadius.toFloat(), 0f, 0f, circleData.shadowColor
        )
    }
}