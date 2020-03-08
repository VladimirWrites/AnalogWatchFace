package com.vlad1m1r.watchface.utils

import android.content.Context
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataProvider
import com.vlad1m1r.watchface.data.WatchFaceType

class Layouts(private val dataProvider: DataProvider, private val context: Context) {
    val background = Background(dataProvider)
    val complications = Complications(context)
    lateinit var ticks: Ticks
        private set
    val hands = Hands(context, dataProvider)

    init {
        update()
    }

    fun update() {
        when(dataProvider.getWatchFaceType()) {
            WatchFaceType.ORIGINAL -> {
                ticks = TicksLayoutOriginal(context)
                complications.setComplicationDrawable(R.drawable.complication_drawable_original)
            }
            WatchFaceType.WATCH_FACE_1 -> {
                ticks = TicksLayout1(context)
                complications.setComplicationDrawable(R.drawable.complication_drawable_1)
            }
        }
    }
}
