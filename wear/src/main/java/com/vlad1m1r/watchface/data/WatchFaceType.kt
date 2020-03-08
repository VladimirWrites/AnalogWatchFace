package com.vlad1m1r.watchface.data

import androidx.annotation.DrawableRes
import com.vlad1m1r.watchface.R

enum class WatchFaceType(val id: Int, @DrawableRes val drawableRes: Int) {
    ORIGINAL(0, R.drawable.watchface_0),
    WATCH_FACE_1(1, R.drawable.watchface_1);

    companion object {
        fun fromId(id: Int): WatchFaceType {
            return when (id) {
                0 -> ORIGINAL
                1 -> WATCH_FACE_1
                else -> throw IllegalArgumentException("WatchFaceType with id $id is not supported")
            }
        }
    }
}