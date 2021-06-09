package com.vlad1m1r.watchface.settings.colorpicker

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.watchFaceColors
import java.lang.IllegalArgumentException

class ColorProvider(val context: Context) {
    operator fun invoke(position: Int, showNoColor: Boolean): Colors {
        return if(position == 0) {
            Colors(
                if(showNoColor) watchFaceColors.find { it.id ==  0}!!.color else null,
                watchFaceColors.find { it.id ==  1}!!.color,
                watchFaceColors.find { it.id ==  2}!!.color,
            )
        } else {
            Colors(
                watchFaceColors.find { it.id ==  3 * position}!!.color,
                watchFaceColors.find { it.id ==  3 * position + 1}!!.color,
                watchFaceColors.find { it.id ==  3 * position + 2}!!.color,
            )
        }
    }
}