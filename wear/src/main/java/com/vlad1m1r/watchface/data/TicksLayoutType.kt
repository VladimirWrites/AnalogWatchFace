package com.vlad1m1r.watchface.data

import androidx.annotation.DrawableRes
import com.vlad1m1r.watchface.R

enum class TicksLayoutType(val id: Int, @DrawableRes val drawableRes: Int, @DrawableRes val drawableZoomedRes: Int) {
    ORIGINAL(0, R.drawable.ticks_1, R.drawable.ticks_1_zoom),
    TICKS_LAYOUT_1(1, R.drawable.ticks_2, R.drawable.ticks_2_zoom);

    companion object {
        fun fromId(id: Int): TicksLayoutType {
            return when (id) {
                0 -> ORIGINAL
                1 -> TICKS_LAYOUT_1
                else -> throw IllegalArgumentException("WatchFaceType with id $id is not supported")
            }
        }
    }
}