package com.vlad1m1r.watchface.data.state

data class WatchFaceState(
    val backgroundState: BackgroundState,
    val ticksState: TicksState,
    val handsState: HandsState,
    val complicationsState: ComplicationsState
)
