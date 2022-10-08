package com.vlad1m1r.watchface.data.state

import com.vlad1m1r.watchface.data.TicksLayoutType

data class TicksState(
    val hasInAmbient: Boolean,
    val hasInInteractive: Boolean,
    val layoutType: TicksLayoutType,
    val hourTicksColor: Int,
    val minuteTicksColor: Int,
    val shouldAdjustToSquareScreen: Boolean
)