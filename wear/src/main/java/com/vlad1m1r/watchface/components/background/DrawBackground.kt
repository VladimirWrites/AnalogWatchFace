package com.vlad1m1r.watchface.components.background

import android.graphics.*
import androidx.wear.watchface.DrawMode
import com.vlad1m1r.watchface.data.state.BackgroundState
import com.vlad1m1r.watchface.model.Point
import javax.inject.Inject

class DrawBackground @Inject constructor(
    private val backgroundBitmapProvider: BackgroundBitmapProvider
) {

    operator fun invoke(canvas: Canvas, drawMode: DrawMode, center: Point, state: BackgroundState) {
        if(!backgroundBitmapProvider.isInitialized) {
            backgroundBitmapProvider.initialize(center, state)
        }
        if (drawMode == DrawMode.AMBIENT) {
            canvas.drawBitmap(backgroundBitmapProvider.getAmbientBitmap(), 0f, 0f, null)
        } else {
            canvas.drawBitmap(backgroundBitmapProvider.getNormalBitmap(), 0f, 0f, null)
        }
    }
}