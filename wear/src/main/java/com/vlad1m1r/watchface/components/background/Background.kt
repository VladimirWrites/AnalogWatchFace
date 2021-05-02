package com.vlad1m1r.watchface.components.background

import android.graphics.*
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.utils.WatchView
import com.vlad1m1r.watchface.utils.getDarkerGrayscale

class Background(
    private val colorStorage: ColorStorage,
    private val dataStorage: DataStorage
    ): WatchView {

    private var center = Point()

    init {
        initializeDrawBackground()
    }

    private lateinit var drawBackground: DrawBackground

    private fun initializeDrawBackground() {
        val getBackgroundData = GetBackgroundData(colorStorage, dataStorage)
        val backgroundBitmapProvider = BackgroundBitmapProvider(getBackgroundData())
        drawBackground = DrawBackground(backgroundBitmapProvider)
        drawBackground.setCenter(center)
    }

    private var mode: Mode = Mode()

    fun draw(canvas: Canvas) {
        drawBackground(canvas)
    }

    fun setMode(mode: Mode) {
        this.mode = mode
        drawBackground.setInAmbientMode(mode.isAmbient)
    }

    override fun setCenter(center: Point) {
        this.center = center
        drawBackground.setCenter(center)
    }

    fun invalidate() {
        initializeDrawBackground()
    }
}