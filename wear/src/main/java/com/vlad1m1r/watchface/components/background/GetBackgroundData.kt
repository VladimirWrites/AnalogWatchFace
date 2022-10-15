package com.vlad1m1r.watchface.components.background

import android.graphics.Color
import com.vlad1m1r.watchface.data.state.BackgroundState
import com.vlad1m1r.watchface.utils.getDarkerGrayscale
import javax.inject.Inject

class GetBackgroundData @Inject constructor() {

    operator fun invoke(backgroundState: BackgroundState): BackgroundData {
        val leftColorAmbient = if (backgroundState.blackInAmbient) {
            Color.BLACK
        } else {
            getDarkerGrayscale(backgroundState.leftColor)
        }

        val rightColorAmbient = if (backgroundState.blackInAmbient) {
            Color.BLACK
        } else {
            getDarkerGrayscale(backgroundState.rightColor)
        }

        return BackgroundData(
            backgroundState.leftColor,
            backgroundState.rightColor,
            leftColorAmbient,
            rightColorAmbient
        )
    }
}