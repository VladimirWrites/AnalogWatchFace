package com.vlad1m1r.watchface.components

import com.vlad1m1r.watchface.components.background.Background
import com.vlad1m1r.watchface.components.hands.Hands
import com.vlad1m1r.watchface.components.ticks.*
import com.vlad1m1r.watchface.components.ticks.layout.TicksLayout
import com.vlad1m1r.watchface.data.state.WatchFaceState
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import javax.inject.Inject

class Layouts @Inject constructor(
    val background: Background,
    val hands: Hands,
    private val getTicks: GetTicks
) {
    private var bottomInset = 0

    lateinit var ticks: TicksLayout
        private set


    fun setState(watchFaceState: WatchFaceState) {
        background.setState(watchFaceState.backgroundState)
        hands.setState(watchFaceState.handsState)

        ticks = getTicks(watchFaceState.ticksState)
        ticks.bottomInset = bottomInset
        ticks.setTicksState(watchFaceState.ticksState)
    }

    fun setBottomInset(bottomInset: Int) {
        this.bottomInset = bottomInset
    }
}
