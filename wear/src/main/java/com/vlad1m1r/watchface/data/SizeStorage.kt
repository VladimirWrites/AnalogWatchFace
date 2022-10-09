package com.vlad1m1r.watchface.data

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.DimenRes
import com.vlad1m1r.watchface.R

const val KEY_CIRCLE_WIDTH = "circle_width"
const val KEY_CIRCLE_RADIUS = "circle_radius"

const val KEY_HAND_HOURS_WIDTH = "hand_hours_width"
const val KEY_HAND_MINUTES_WIDTH = "hand_minutes_width"
const val KEY_HAND_SECONDS_WIDTH = "hand_seconds_width"

const val KEY_HAND_HOURS_SCALE = "hand_hours_scale"
const val KEY_HAND_MINUTES_SCALE = "hand_minutes_scale"
const val KEY_HAND_SECONDS_SCALE = "hand_seconds_scale"

class SizeStorage(
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) {

    fun getCircleWidth() = sharedPreferences.getInt(KEY_CIRCLE_WIDTH, getDimensionPixelSize(R.dimen.circle_width))

    fun getCircleRadius() = sharedPreferences.getInt(KEY_CIRCLE_RADIUS, getDimensionPixelSize(R.dimen.circle_radius))

    fun getSecondsHandWidth() = sharedPreferences.getInt(KEY_HAND_SECONDS_WIDTH, getDimensionPixelSize(R.dimen.hand_width_second))

    fun getMinutesHandWidth() = sharedPreferences.getInt(KEY_HAND_MINUTES_WIDTH, getDimensionPixelSize(R.dimen.hand_width_minute))

    fun getHoursHandWidth() = sharedPreferences.getInt(KEY_HAND_HOURS_WIDTH, getDimensionPixelSize(R.dimen.hand_width_hour))

    fun getSecondsHandScale() = sharedPreferences.getFloat(KEY_HAND_SECONDS_SCALE, 0.875f)

    fun getMinutesHandScale() = sharedPreferences.getFloat(KEY_HAND_MINUTES_SCALE, 0.75f)

    fun getHoursHandScale() = sharedPreferences.getFloat(KEY_HAND_HOURS_SCALE, 0.5f)

    private fun getDimensionPixelSize(@DimenRes res: Int) =  context.resources.getDimensionPixelSize(res)
}