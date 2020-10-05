package com.vlad1m1r.watchface.components

import android.graphics.*
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.utils.getDarkerGrayscale

class Background(private val colorStorage: ColorStorage) {

    private var center = Point()

    private lateinit var normalBitmap: Bitmap
    private lateinit var ambientBitmap: Bitmap

    private var mode: Mode = Mode()

    fun draw(canvas: Canvas) {
        if (mode.isAmbient) {
            if (!this::ambientBitmap.isInitialized) {
                initializeAmbientBackground()
            }
            canvas.drawBitmap(ambientBitmap, 0f, 0f, null)
        } else {
            if (!this::normalBitmap.isInitialized) {
                initializeNormalBackground()
            }
            canvas.drawBitmap(normalBitmap, 0f, 0f, null)
        }
    }

    fun setMode(mode: Mode) {
        this.mode = mode
    }

    fun setCenter(center: Point) {
        this.center = center
    }

    private fun initializeNormalBackground() {
        val gradient = LinearGradient(
            0f,
            center.x * 2,
            center.y * 2,
            0f,
            colorStorage.getBackgroundLeftColor(),
            colorStorage.getBackgroundRightColor(),
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
            drawRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), p)
        }

        normalBitmap = bitmap
    }

    private fun initializeAmbientBackground() {
        val leftColorGrayscale = getDarkerGrayscale(colorStorage.getBackgroundLeftColor())
        val rightColorGrayscale = getDarkerGrayscale(colorStorage.getBackgroundRightColor())
        val gradient = LinearGradient(
            0f,
            center.x * 2,
            center.y * 2,
            0f,
            leftColorGrayscale,
            rightColorGrayscale,
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
            drawRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), p)
        }

        ambientBitmap = bitmap
    }

    fun invalidate() {
        initializeNormalBackground()
        initializeAmbientBackground()
    }
}