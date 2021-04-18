package com.vlad1m1r.watchface.components.background

import android.graphics.*
import com.vlad1m1r.watchface.model.Point

class DrawBackground(
    private val backgroundData: BackgroundData
) {

    private var center = Point()

    private var isInAmbientMode = false

    private lateinit var normalBitmap: Bitmap
    private lateinit var ambientBitmap: Bitmap

    private fun initializeBitmaps() {
        if(center.x == 0f || center.y == 0f) return
        normalBitmap = getBackgroundBitmap(backgroundData.leftColor, backgroundData.rightColor)
        ambientBitmap = getBackgroundBitmap(backgroundData.leftColorAmbient, backgroundData.rightColorAmbient)
    }

    operator fun invoke(canvas: Canvas) {

        if (::normalBitmap.isInitialized && ::ambientBitmap.isInitialized) {
            if (isInAmbientMode) {
                canvas.drawBitmap(ambientBitmap, 0f, 0f, null)
            } else {
                canvas.drawBitmap(normalBitmap, 0f, 0f, null)
            }
        }
    }

    fun setInAmbientMode(isInAmbientMode: Boolean) {
        this.isInAmbientMode = isInAmbientMode
    }

    private fun getBackgroundBitmap(leftColor: Int, rightColor: Int): Bitmap {
        val gradient = LinearGradient(
            0f,
            center.y * 2,
            center.x * 2,
            0f,
            leftColor,
            rightColor,
            Shader.TileMode.CLAMP
        )
        val p = Paint().apply {
            isDither = true
            shader = gradient
        }

        val bitmap = Bitmap.createBitmap(
            (center.x * 2).toInt(),
            (center.y * 2).toInt(),
            Bitmap.Config.ARGB_8888
        )
        Canvas(bitmap).apply {
            drawRect(RectF(0f, 0f, center.x * 2f, center.y * 2f), p)
        }

        return bitmap
    }

    fun setCenter(center: Point) {
        this.center = center
        initializeBitmaps()
    }
}