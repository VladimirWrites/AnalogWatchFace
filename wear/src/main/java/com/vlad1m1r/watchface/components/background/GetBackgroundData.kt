package com.vlad1m1r.watchface.components.background

import android.graphics.Color
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.utils.getDarkerGrayscale

class GetBackgroundData(
    private val colorStorage: ColorStorage,
    private val dataStorage: DataStorage
) {

    operator fun invoke(): BackgroundData {
        val leftColorAmbient =  if(dataStorage.hasBlackAmbientBackground()) {
            Color.BLACK
        } else {
            getDarkerGrayscale(colorStorage.getBackgroundLeftColor())
        }

        val rightColorAmbient =  if(dataStorage.hasBlackAmbientBackground()) {
            Color.BLACK
        } else {
            getDarkerGrayscale(colorStorage.getBackgroundRightColor())
        }

        return BackgroundData(
            colorStorage.getBackgroundLeftColor(),
            colorStorage.getBackgroundRightColor(),
            leftColorAmbient,
            rightColorAmbient
        )
    }
}