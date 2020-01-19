package com.vlad1m1r.watchface.utils

import android.content.Context
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R

abstract class WatchView(context: Context) {
    protected val shadowColor = ContextCompat.getColor(context, R.color.watch_shadow)
    protected val shadowRadius = context.resources.getDimension(R.dimen.shadow_radius)
    private val ambientColor = ContextCompat.getColor(context, R.color.watch_ambient)

    abstract fun setCenter(center: Point)

    protected fun Paint.inAmbientMode(color: Int = ambientColor) {
        this.color = color
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