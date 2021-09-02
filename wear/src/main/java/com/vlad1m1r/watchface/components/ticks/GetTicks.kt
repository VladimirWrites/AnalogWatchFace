package com.vlad1m1r.watchface.components.ticks

import com.vlad1m1r.watchface.components.ticks.layout.*
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.TicksLayoutType
import javax.inject.Inject

class GetTicks @Inject constructor(
    private val dataStorage: DataStorage,
    private val ticksLayoutOriginal: TicksLayoutOriginal,
    private val ticksLayout1: TicksLayout1,
    private val ticksLayout2: TicksLayout2,
    private val ticksLayout3: TicksLayout3,
) {

    operator fun invoke(): TicksLayout {
        return when (dataStorage.getTicksLayoutType()) {
            TicksLayoutType.ORIGINAL -> {
                ticksLayoutOriginal
            }
            TicksLayoutType.TICKS_LAYOUT_1 -> {
                ticksLayout1
            }
            TicksLayoutType.TICKS_LAYOUT_2 -> {
                ticksLayout2
            }
            TicksLayoutType.TICKS_LAYOUT_3 -> {
                ticksLayout3
            }
        }
    }
}