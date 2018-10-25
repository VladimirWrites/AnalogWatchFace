package com.vlad1m1r.watchface.utils

import android.content.SharedPreferences

const val KEY_ANALOG_WATCH_FACE = "analog_watch_face_key"

private const val KEY_HAS_COMPLICATIONS_IN_AMBIENT_MODE = "has_complications_in_ambient_mode"

class DataProvider(private val sharedPreferences: SharedPreferences) {

    fun hasComplicationsInAmbientMode() = sharedPreferences.getBoolean(KEY_HAS_COMPLICATIONS_IN_AMBIENT_MODE, true)

    fun setHasComplicationsInAmbientMode(complicationsInAmbientMode: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_HAS_COMPLICATIONS_IN_AMBIENT_MODE, complicationsInAmbientMode)
        editor.apply()
    }
}