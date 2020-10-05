package com.vlad1m1r.watchface.settings.colorpicker

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R
import java.lang.IllegalArgumentException

class ColorProvider(val context: Context) {
    operator fun invoke(position: Int, showNoColor: Boolean): Colors {
        return when (position) {
            0 -> Colors(
                if(showNoColor) Color.TRANSPARENT else null,
                ContextCompat.getColor(context, R.color.watch_hand_minute),
                ContextCompat.getColor(context, R.color.watch_hand_hour)
            )
            1 -> Colors(
                ContextCompat.getColor(context, R.color.primary_color_light),
                ContextCompat.getColor(context, R.color.primary_color),
                ContextCompat.getColor(context, R.color.primary_color_dark)
            )
            2 -> Colors(
                ContextCompat.getColor(context, R.color.color_1_shade_1),
                ContextCompat.getColor(context, R.color.color_1_shade_2),
                ContextCompat.getColor(context, R.color.color_1_shade_3)
            )
            3 -> Colors(
                ContextCompat.getColor(context, R.color.color_2_shade_1),
                ContextCompat.getColor(context, R.color.color_2_shade_2),
                ContextCompat.getColor(context, R.color.color_2_shade_3)
            )
            4 -> Colors(
                ContextCompat.getColor(context, R.color.color_3_shade_1),
                ContextCompat.getColor(context, R.color.color_3_shade_2),
                ContextCompat.getColor(context, R.color.color_3_shade_3)
            )
            5 -> Colors(
                ContextCompat.getColor(context, R.color.color_4_shade_1),
                ContextCompat.getColor(context, R.color.color_4_shade_2),
                ContextCompat.getColor(context, R.color.color_4_shade_3)
            )
            6 -> Colors(
                ContextCompat.getColor(context, R.color.color_5_shade_1),
                ContextCompat.getColor(context, R.color.color_5_shade_2),
                ContextCompat.getColor(context, R.color.color_5_shade_3)
            )
            7 -> Colors(
                ContextCompat.getColor(context, R.color.color_6_shade_1),
                ContextCompat.getColor(context, R.color.color_6_shade_2),
                ContextCompat.getColor(context, R.color.color_6_shade_3)
            )
            8 -> Colors(
                ContextCompat.getColor(context, R.color.color_7_shade_1),
                ContextCompat.getColor(context, R.color.color_7_shade_2),
                ContextCompat.getColor(context, R.color.color_7_shade_3)
            )
            9 -> Colors(
                ContextCompat.getColor(context, R.color.color_8_shade_1),
                ContextCompat.getColor(context, R.color.color_8_shade_2),
                ContextCompat.getColor(context, R.color.color_8_shade_3)
            )
            10 -> Colors(
                ContextCompat.getColor(context, R.color.color_9_shade_1),
                ContextCompat.getColor(context, R.color.color_9_shade_2),
                ContextCompat.getColor(context, R.color.color_9_shade_3)
            )
            11 -> Colors(
                ContextCompat.getColor(context, R.color.color_10_shade_1),
                ContextCompat.getColor(context, R.color.color_10_shade_2),
                ContextCompat.getColor(context, R.color.color_10_shade_3)
            )
            else -> throw IllegalArgumentException("Position not supported: $position")
        }
    }
}