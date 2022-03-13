package com.vlad1m1r.watchface.data

import androidx.annotation.ColorInt

data class WatchFaceColor(
    val id: Int,
    @ColorInt val color: Int,
    val custom: Boolean = false
)

fun List<Int>.toWatchFaceColors() = this.mapIndexed { index, color ->
    WatchFaceColor(index, color, true)
}

