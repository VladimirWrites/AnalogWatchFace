package com.vlad1m1r.watchface.components.background

import android.graphics.*
import androidx.wear.watchface.DrawMode
import com.vlad1m1r.watchface.data.state.BackgroundState
import com.vlad1m1r.watchface.model.Point
import javax.inject.Inject

class Background @Inject constructor(
    private val drawBackground: DrawBackground
) {
    private var state: BackgroundState? = null

    fun setState(state: BackgroundState) {
        this.state = state
    }

    fun draw(canvas: Canvas, drawMode: DrawMode, center: Point) {
        if(state != null) {
            drawBackground(canvas, drawMode, center, state!!)
        }
    }
}