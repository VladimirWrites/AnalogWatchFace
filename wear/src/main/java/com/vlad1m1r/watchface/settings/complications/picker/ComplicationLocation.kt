package com.vlad1m1r.watchface.settings.complications.picker

import com.vlad1m1r.watchface.components.BOTTOM_COMPLICATION_ID
import com.vlad1m1r.watchface.components.LEFT_COMPLICATION_ID
import com.vlad1m1r.watchface.components.RIGHT_COMPLICATION_ID
import com.vlad1m1r.watchface.components.TOP_COMPLICATION_ID

enum class ComplicationLocation(val id: Int, val isBig: Boolean) {
    LEFT(LEFT_COMPLICATION_ID, false),
    RIGHT(RIGHT_COMPLICATION_ID, false),
    TOP(TOP_COMPLICATION_ID, true),
    BOTTOM(BOTTOM_COMPLICATION_ID, true);

    companion object {
        fun getFromId(id: Int): ComplicationLocation {
            return enumValues<ComplicationLocation>().first {
                it.id == id
            }
        }
    }
}