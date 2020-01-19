package com.vlad1m1r.watchface.utils

import android.content.Context
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataProvider

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
        if (dataProvider.isLayout2()) {
            ticks = TicksLayout2(context)
            complications.setComplicationDrawable(R.drawable.design2_complication_drawable)
        } else {
            ticks = TicksLayout1(context)
            complications.setComplicationDrawable(R.drawable.complication_drawable)
        }
    }
}
