package com.vlad1m1r.watchface.utils

import android.content.Context
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import com.vlad1m1r.watchface.R

abstract class WatchView(context: Context) {
    protected val shadowColor = ContextCompat.getColor(context, R.color.watch_shadow)
    protected val shadowRadius = context.resources.getDimension(R.dimen.shadow_radius)
    protected val ambientColor = ContextCompat.getColor(context, R.color.watch_ambient)

    abstract fun setCenter(centerX: Float, centerY: Float)

    protected fun Paint.inAmbientMode() {
        color = ambientColor
        isAntiAlias = false
        clearShadowLayer()
    }

    protected fun Paint.inInteractiveMode(color: Int) {
        this.color = color
        isAntiAlias = true
        setShadowLayer(
            shadowRadius, 0f, 0f, shadowColor
        )
    }
}