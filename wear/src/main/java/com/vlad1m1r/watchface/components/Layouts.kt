package com.vlad1m1r.watchface.components

import android.content.Context
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.components.ticks.Ticks
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.TicksLayoutType
import com.vlad1m1r.watchface.components.ticks.TicksLayout1
import com.vlad1m1r.watchface.components.ticks.TicksLayoutOriginal
import com.vlad1m1r.watchface.data.ColorStorage

class Layouts(
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage,
    private val context: Context
) {
    val background = Background(colorStorage)
    val complications = Complications(context, dataStorage, colorStorage)
    val hands: Hands = Hands(context, colorStorage)

    lateinit var ticks: Ticks
        private set

    init {
        complications.setComplicationDrawable(R.drawable.complication_drawable)
        initTicks()
    }

    fun initTicks() {
        ticks = when (dataStorage.getTicksLayoutType()) {
            TicksLayoutType.ORIGINAL -> {
                TicksLayoutOriginal(context, colorStorage)
            }
            TicksLayoutType.TICKS_LAYOUT_1 -> {
                TicksLayout1(context, colorStorage)
            }
        }
    }

    fun invalidateHands() {
        hands.invalidate()
    }

    fun invalidateBackground() {
        background.invalidate()
    }

    fun invalidateComplications() {
        complications.invalidate()
    }
}
