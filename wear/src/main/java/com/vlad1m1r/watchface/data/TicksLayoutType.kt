package com.vlad1m1r.watchface.data

import androidx.annotation.DrawableRes
import com.vlad1m1r.watchface.R

enum class TicksLayoutType(val id: Int, @DrawableRes val drawableRes: Int, @DrawableRes val drawableZoomedRes: Int) {
    ORIGINAL(0, R.drawable.ticks_1, R.drawable.ticks_1_zoom),
    TICKS_LAYOUT_1(1, R.drawable.ticks_2, R.drawable.ticks_2_zoom),
    TICKS_LAYOUT_2(2, R.drawable.ticks_3, R.drawable.ticks_3_zoom),
    TICKS_LAYOUT_3(3, R.drawable.ticks_4, R.drawable.ticks_4_zoom);

    companion object {
        fun fromId(id: Int): TicksLayoutType {
            return when (id) {
                0 -> ORIGINAL
                1 -> TICKS_LAYOUT_1
                2 -> TICKS_LAYOUT_2
                3 -> TICKS_LAYOUT_3
                else -> throw IllegalArgumentException("WatchFaceType with id $id is not supported")
            }
        }
    }
}