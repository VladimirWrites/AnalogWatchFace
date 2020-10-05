package com.vlad1m1r.watchface.components.ticks

import android.graphics.Canvas
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.model.Mode

interface Ticks {
    val centerInvalidated: Boolean
    fun setCenter(center: Point)
    fun draw(canvas: Canvas)
    fun setMode(mode: Mode)
}