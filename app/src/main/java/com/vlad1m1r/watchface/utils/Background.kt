package com.vlad1m1r.watchface.utils

import android.graphics.*

class Background {

    private var centerX = 0f
    private var centerY = 0f

    private lateinit var normalBitmap: Bitmap
    private lateinit var ambientBitmap: Bitmap

    private var mode: Mode = Mode()

    fun draw(canvas: Canvas) {
        if(mode.isAmbient && (mode.isLowBitAmbient || mode.isBurnInProtection)) {
            canvas.drawColor(android.graphics.Color.BLACK)
        } else if(mode.isAmbient) {
            if(!this::ambientBitmap.isInitialized) {
                initializeAmbientBackground()
            }
            canvas.drawBitmap(ambientBitmap, 0f, 0f, null)
        } else {
            if(!this::normalBitmap.isInitialized) {
                initializeNormalBackground()
            }
            canvas.drawBitmap(normalBitmap, 0f, 0f, null)
        }
    }

    fun setMode(mode: Mode) {
        this.mode = mode
    }

    fun setCenter(centerX: Float, centerY: Float) {
        this.centerX = centerX
        this.centerY = centerY
    }

    private fun initializeNormalBackground() {
        val gradient = LinearGradient(0f, centerX*2, centerY*2, 0f, Color.parseColor("#2b3948"), Color.parseColor("#377e8f"), Shader.TileMode.CLAMP)
        val p = Paint().apply {
            isDither = true
            shader = gradient
        }

        val bitmap = Bitmap.createBitmap((centerX*2).toInt(), (centerY*2f).toInt(), Bitmap.Config.ARGB_8888)
        Canvas(bitmap).apply {
            drawRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), p)
        }

        normalBitmap = bitmap
    }

    private fun initializeAmbientBackground() {
        val gradient = LinearGradient(0f, centerX*2, centerY*2f, 0f, Color.parseColor("#222222"), Color.parseColor("#777777"), Shader.TileMode.CLAMP)
        val p = Paint().apply {
            isDither = true
            shader = gradient
        }

        val bitmap = Bitmap.createBitmap((centerX*2).toInt(), (centerY*2f).toInt(), Bitmap.Config.ARGB_8888)
        Canvas(bitmap).apply {
            drawRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), p)
        }

        ambientBitmap = bitmap
    }
}