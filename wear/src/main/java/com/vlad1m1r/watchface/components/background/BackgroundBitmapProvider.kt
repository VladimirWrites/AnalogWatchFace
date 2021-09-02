package com.vlad1m1r.watchface.components.background

import android.graphics.*
import com.vlad1m1r.watchface.model.Point
import javax.inject.Inject

class BackgroundBitmapProvider @Inject constructor(
    private val getBackgroundData: GetBackgroundData
) {
    private lateinit var normalBitmap: Bitmap
    private lateinit var ambientBitmap: Bitmap

    fun initialize(center: Point) {
        val backgroundData = getBackgroundData()
        if(center.x == 0f || center.y == 0f) return
        normalBitmap = getBackgroundBitmap(backgroundData.leftColor, backgroundData.rightColor, center)
        ambientBitmap = getBackgroundBitmap(backgroundData.leftColorAmbient, backgroundData.rightColorAmbient, center)
    }

    fun getAmbientBitmap() = ambientBitmap
    fun getNormalBitmap() = normalBitmap

    fun getBackgroundBitmap(leftColor: Int, rightColor: Int, center: Point): Bitmap {
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
}