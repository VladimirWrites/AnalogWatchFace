package com.vlad1m1r.watchface.settings.complications.picker

import com.vlad1m1r.watchface.*


enum class ComplicationLocation(val id: Int, val isBig: Boolean) {
    LEFT(LEFT_COMPLICATION_ID, false),
    RIGHT(RIGHT_COMPLICATION_ID, false),
    TOP(TOP_COMPLICATION_ID, true),
    BOTTOM(BOTTOM_COMPLICATION_ID, true),
    BACKGROUND(BACKGROUND_COMPLICATION_ID, false);

    companion object {
        fun getFromId(id: Int): ComplicationLocation {
            return enumValues<ComplicationLocation>().first {
                it.id == id
            }
        }
    }
}