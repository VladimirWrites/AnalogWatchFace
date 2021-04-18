package com.vlad1m1r.watchface.components

import android.content.Context
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.components.background.Background
import com.vlad1m1r.watchface.components.hands.Hands
import com.vlad1m1r.watchface.components.ticks.*
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.TicksLayoutType
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.components.ticks.TicksLayout

class Layouts(
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage,
    private val context: Context
) {
    val background = Background(colorStorage, dataStorage)
    val complications = Complications(context, dataStorage, colorStorage)
    val hands: Hands = Hands(context, colorStorage)

    lateinit var ticks: TicksLayout
        private set

    init {
        complications.setComplicationDrawable(R.drawable.complication_drawable)
        initTicks()
    }

    fun initTicks() {
        ticks = when (dataStorage.getTicksLayoutType()) {
            TicksLayoutType.ORIGINAL -> {
                TicksLayoutOriginal(context, dataStorage, colorStorage)
            }
            TicksLayoutType.TICKS_LAYOUT_1 -> {
                TicksLayout1(context, dataStorage, colorStorage)
            }
            TicksLayoutType.TICKS_LAYOUT_2 -> {
                TicksLayout2(context, dataStorage, colorStorage)
            }
            TicksLayoutType.TICKS_LAYOUT_3 -> {
                TicksLayout3(context, dataStorage, colorStorage)
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
