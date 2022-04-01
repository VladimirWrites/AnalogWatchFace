package com.vlad1m1r.watchface.components

import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.components.background.Background
import com.vlad1m1r.watchface.components.hands.Hands
import com.vlad1m1r.watchface.components.ticks.*
import com.vlad1m1r.watchface.components.ticks.layout.TicksLayout
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import javax.inject.Inject

class Layouts @Inject constructor(
    val background: Background,
    //val complications: Complications,
    val hands: Hands,
    //val backgroundComplication: BackgroundComplication,
    private val getTicks: GetTicks
) {
    private var bottomInset = 0

    lateinit var ticks: TicksLayout
        private set

    init {
//        complications.setComplicationDrawable(R.drawable.complication_drawable)
//        backgroundComplication.setComplicationDrawable(R.drawable.complication_drawable)
        initTicks()
    }

    fun initTicks() {
        ticks = getTicks()
        ticks.bottomInset = bottomInset
        ticks.invalidate()
    }

    fun invalidateHands() {
        hands.invalidate()
    }

    fun invalidateBackground() {
        background.invalidate()
    }

    fun invalidateComplications() {
        //complications.invalidate()
    }

    fun invalidateBackgroundComplication() {
        //backgroundComplication.invalidate()
    }

    fun setBottomInset(bottomInset: Int) {
        this.bottomInset = bottomInset
        this.ticks.bottomInset = bottomInset
        //this.complications.bottomInset = bottomInset
    }

    fun setMode(mode: Mode) {
        background.setMode(mode)
//        complications.setMode(mode)
//        backgroundComplication.setMode(mode)
        ticks.setMode(mode)
        hands.setMode(mode)
    }

    fun setCenter(center: Point) {
        background.setCenter(center)
        ticks.setCenter(center)
//        complications.setCenter(center)
//        backgroundComplication.setCenter(center)
        hands.setCenter(center)
    }
}
