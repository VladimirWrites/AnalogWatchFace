package com.vlad1m1r.watchface.components

import android.content.Context
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.components.background.Background
import com.vlad1m1r.watchface.components.background.BackgroundComplication
import com.vlad1m1r.watchface.components.hands.Hands
import com.vlad1m1r.watchface.components.ticks.*
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.TicksLayoutType
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.components.ticks.TicksLayout
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.model.Mode

class Layouts(
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage,
    private val context: Context,
    sizeStorage: SizeStorage,
) {
    val background = Background(colorStorage, dataStorage)
    val complications = Complications(context, dataStorage, colorStorage)
    val hands: Hands = Hands(context, colorStorage, dataStorage, sizeStorage)
    val backgroundComplication = BackgroundComplication(context)

    private var bottomInset = 0

    lateinit var ticks: TicksLayout
        private set

    init {
        complications.setComplicationDrawable(R.drawable.complication_drawable)
        backgroundComplication.setComplicationDrawable(R.drawable.complication_drawable)
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
        ticks.bottomInset = bottomInset
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

    fun invalidateBackgroundComplication() {
        backgroundComplication.invalidate()
    }

    fun setBottomInset(bottomInset: Int) {
        this.bottomInset = bottomInset
        this.ticks.bottomInset = bottomInset
        this.complications.bottomInset = bottomInset
    }

    fun setMode(mode: Mode) {
        background.setMode(mode)
        complications.setMode(mode)
        backgroundComplication.setMode(mode)
        ticks.setMode(mode)
        hands.setMode(mode)
    }
}
