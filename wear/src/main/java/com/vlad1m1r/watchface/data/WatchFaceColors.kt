package com.vlad1m1r.watchface.data

import android.graphics.Color
import androidx.annotation.ColorInt

val watchFaceColors: List<WatchFaceColor> by lazy {
    listOf(
        WatchFaceColor(0, '0', Color.parseColor("#00000000")),
        WatchFaceColor(1, '1', Color.parseColor("#ffa17a")),
        WatchFaceColor(2, '2', Color.parseColor("#fe7682")),

        WatchFaceColor(3, '3', Color.parseColor("#87b2bc")),
        WatchFaceColor(4, '4', Color.parseColor("#377e8f")),
        WatchFaceColor(5, '5', Color.parseColor("#2b3948")),

        WatchFaceColor(6, '6', Color.parseColor("#AA3939")),
        WatchFaceColor(7, '7', Color.parseColor("#801515")),
        WatchFaceColor(8, '8', Color.parseColor("#550000")),

        WatchFaceColor(9, '9', Color.parseColor("#AA9739")),
        WatchFaceColor(10, 'a', Color.parseColor("#806D15")),
        WatchFaceColor(11, 'b', Color.parseColor("#004400")),

        WatchFaceColor(12, 'c', Color.parseColor("#403075")),
        WatchFaceColor(13, 'd', Color.parseColor("#261758")),
        WatchFaceColor(14, 'e', Color.parseColor("#13073A")),

        WatchFaceColor(15, 'f', Color.parseColor("#2D882D")),
        WatchFaceColor(16, 'g', Color.parseColor("#116611")),
        WatchFaceColor(17, 'h', Color.parseColor("#004400")),

        WatchFaceColor(18, 'i', Color.parseColor("#6F256F")),
        WatchFaceColor(19, 'j', Color.parseColor("#530E53")),
        WatchFaceColor(20, 'k', Color.parseColor("#370037")),

        WatchFaceColor(21, 'l', Color.parseColor("#AA7939")),
        WatchFaceColor(22, 'm', Color.parseColor("#805215")),
        WatchFaceColor(23, 'n', Color.parseColor("#553100")),

        WatchFaceColor(24, 'o', Color.parseColor("#29506D")),
        WatchFaceColor(25, 'p', Color.parseColor("#123652")),
        WatchFaceColor(26, 'q', Color.parseColor("#042037")),

        WatchFaceColor(27, 'r', Color.parseColor("#91A437")),
        WatchFaceColor(28, 's', Color.parseColor("#6A7B15")),
        WatchFaceColor(29, 't', Color.parseColor("#445200")),

        WatchFaceColor(30, 'u', Color.parseColor("#ffffff")),
        WatchFaceColor(31, 'v', Color.parseColor("#cccccc")),
        WatchFaceColor(32, 'w', Color.parseColor("#999999")),

        WatchFaceColor(33, 'x', Color.parseColor("#777777")),
        WatchFaceColor(34, 'y', Color.parseColor("#444444")),
        WatchFaceColor(35, 'z', Color.parseColor("#000000")),
    )
}

data class WatchFaceColor(
    val id: Int,
    val code: Char,
    @ColorInt val color: Int
)
