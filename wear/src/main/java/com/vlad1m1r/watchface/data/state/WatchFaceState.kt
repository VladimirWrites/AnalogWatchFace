package com.vlad1m1r.watchface.data.state

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R

data class WatchFaceState(
    val backgroundState: BackgroundState,
    val ticksState: TicksState,
    val handsState: HandsState,
    val useAntiAliasingInAmbientMode: Boolean,

)
