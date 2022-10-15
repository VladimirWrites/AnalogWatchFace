package com.vlad1m1r.watchface.components.background

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.wear.watchface.DrawMode
import com.vlad1m1r.watchface.data.state.BackgroundState
import com.vlad1m1r.watchface.model.Point
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DrawBackgroundShould {

    private val ambientBitmapMock = mock<Bitmap>()
    private val normalBitmapMock = mock<Bitmap>()

    private val backgroundBitmapProviderMock = mock<BackgroundBitmapProvider>().apply {
        whenever(this.getAmbientBitmap()).thenReturn(ambientBitmapMock)
        whenever(this.getNormalBitmap()).thenReturn(normalBitmapMock)
    }

    private val canvasMock = mock<Canvas>()

    private val backgroundState = BackgroundState(false, Color.BLACK, Color.WHITE)

    private val center = Point(0f, 0f)

    @Test
    fun switchToAmbientMode() {
        val drawBackground = DrawBackground(
            backgroundBitmapProviderMock
        )

        drawBackground(canvasMock, DrawMode.AMBIENT, center, backgroundState)

        verify(canvasMock).drawBitmap(backgroundBitmapProviderMock.getAmbientBitmap(), 0f, 0f, null)
    }

    @Test
    fun switchToInteractiveMode() {
        val drawBackground = DrawBackground(
            backgroundBitmapProviderMock
        )

        drawBackground(canvasMock, DrawMode.INTERACTIVE, center, backgroundState)

        verify(canvasMock).drawBitmap(backgroundBitmapProviderMock.getNormalBitmap(), 0f, 0f, null)
    }
}