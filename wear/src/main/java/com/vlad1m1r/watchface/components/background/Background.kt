package com.vlad1m1r.watchface.components.background

import android.graphics.*
import com.vlad1m1r.watchface.data.state.BackgroundState
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.utils.WatchView
import javax.inject.Inject

class Background @Inject constructor(
    private val drawBackground: DrawBackground
) : WatchView {

    private var state: BackgroundState? = null

    private var center = Point()

    fun setState(state: BackgroundState) {
        this.state = state
    }

    private fun initializeDrawBackground() {
        if(state != null) {
            drawBackground.setCenter(center, state!!)
        }
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
        if(state != null) {
            drawBackground.setCenter(center, state!!)
        }
    }
}