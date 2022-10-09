package com.vlad1m1r.watchface.components.background

import android.graphics.*
import com.vlad1m1r.watchface.data.state.BackgroundState
import com.vlad1m1r.watchface.model.Point
import javax.inject.Inject

class DrawBackground @Inject constructor(
    private val backgroundBitmapProvider: BackgroundBitmapProvider
) {
    private var isInAmbientMode = false

    operator fun invoke(canvas: Canvas) {
        if (isInAmbientMode) {
            canvas.drawBitmap(backgroundBitmapProvider.getAmbientBitmap(), 0f, 0f, null)
        } else {
            canvas.drawBitmap(backgroundBitmapProvider.getNormalBitmap(), 0f, 0f, null)
        }
    }

    fun setInAmbientMode(isInAmbientMode: Boolean) {
        this.isInAmbientMode = isInAmbientMode
    }

    fun setCenter(center: Point, backgroundState: BackgroundState) {
        backgroundBitmapProvider.initialize(center, backgroundState)
    }
}