package com.vlad1m1r.watchface.components.ticks

import android.content.Context
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.utils.inAmbientMode
import com.vlad1m1r.watchface.utils.inInteractiveMode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TickPaintProvider @Inject constructor(
    @ApplicationContext context: Context
) {

    private val shadowColor = ContextCompat.getColor(context, R.color.watch_shadow)
    private val shadowRadius = context.resources.getDimension(R.dimen.shadow_radius)

    fun getTickPaint(
        @ColorInt handColor: Int,
        width: Float,
    ): Paint {
        return Paint().apply {
            color = handColor
            strokeWidth = width
            isAntiAlias = true
            style = Paint.Style.STROKE
            setShadowLayer(
                shadowRadius, 0f, 0f, shadowColor
            )
        }
    }

    fun getRoundTickPaint(
        @ColorInt handColor: Int
    ): Paint {
        return Paint().apply {
            color = handColor
            isAntiAlias = true
            style = Paint.Style.FILL
            setShadowLayer(
                shadowRadius, 0f, 0f, shadowColor
            )
        }
    }

    fun inAmbientMode(paint: Paint, @ColorInt color: Int, isAntiAlias: Boolean) {
        paint.inAmbientMode(color, isAntiAlias)
    }

    fun inInteractiveMode(paint: Paint, @ColorInt color: Int) {
        paint.inInteractiveMode(color, shadowColor, shadowRadius)
    }
}