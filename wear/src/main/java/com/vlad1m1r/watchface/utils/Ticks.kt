package com.vlad1m1r.watchface.utils

import android.graphics.Canvas

interface Ticks {
    val centerInvalidated: Boolean
    fun setCenter(center: Point)
    fun draw(canvas: Canvas)
    fun setMode(mode: Mode)
}