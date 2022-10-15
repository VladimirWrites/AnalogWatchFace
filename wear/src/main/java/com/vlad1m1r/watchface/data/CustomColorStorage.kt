package com.vlad1m1r.watchface.data

import android.content.SharedPreferences
import android.graphics.Color
import androidx.annotation.ColorInt

private const val KEY_CUSTOM_COLORS = "custom_colors"

class CustomColorStorage(
    private val sharedPreferences: SharedPreferences
) {
    fun getCustomColors(): List<Int> {
        return sharedPreferences.getString(KEY_CUSTOM_COLORS, defaultColors)!!.split("#").map {
            it.toInt()
        }
    }

    fun addCustomColor(@ColorInt color: Int) {
        val colors = getCustomColors().toMutableList()
        colors.add(color)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_CUSTOM_COLORS, colors.joinToString(separator = "#"))
        editor.apply()
    }

    fun removeCustomColor(@ColorInt color: Int) {
        val colors = getCustomColors().toMutableList()
        colors.remove(color)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_CUSTOM_COLORS, colors.joinToString(separator = "#"))
        editor.apply()
    }

    private val defaultColors: String by lazy {
        "${Color.parseColor("#00000000")}#" +
                "${Color.parseColor("#ffa17a")}#" +
                "${Color.parseColor("#fe7682")}#" +

                "${Color.parseColor("#87b2bc")}#" +
                "${Color.parseColor("#377e8f")}#" +
                "${Color.parseColor("#2b3948")}#" +

                "${Color.parseColor("#AA3939")}#" +
                "${Color.parseColor("#801515")}#" +
                "${Color.parseColor("#550000")}#" +

                "${Color.parseColor("#AA9739")}#" +
                "${Color.parseColor("#806D15")}#" +
                "${Color.parseColor("#004400")}#" +

                "${Color.parseColor("#403075")}#" +
                "${Color.parseColor("#261758")}#" +
                "${Color.parseColor("#13073A")}#" +

                "${Color.parseColor("#2D882D")}#" +
                "${Color.parseColor("#116611")}#" +
                "${Color.parseColor("#004400")}#" +

                "${Color.parseColor("#6F256F")}#" +
                "${Color.parseColor("#530E53")}#" +
                "${Color.parseColor("#370037")}#" +

                "${Color.parseColor("#AA7939")}#" +
                "${Color.parseColor("#805215")}#" +
                "${Color.parseColor("#553100")}#" +

                "${Color.parseColor("#29506D")}#" +
                "${Color.parseColor("#123652")}#" +
                "${Color.parseColor("#042037")}#" +

                "${Color.parseColor("#91A437")}#" +
                "${Color.parseColor("#6A7B15")}#" +
                "${Color.parseColor("#445200")}#" +

                "${Color.parseColor("#ffffff")}#" +
                "${Color.parseColor("#cccccc")}#" +
                "${Color.parseColor("#999999")}#" +

                "${Color.parseColor("#777777")}#" +
                "${Color.parseColor("#444444")}#" +
                "${Color.parseColor("#000000")}"
    }
}