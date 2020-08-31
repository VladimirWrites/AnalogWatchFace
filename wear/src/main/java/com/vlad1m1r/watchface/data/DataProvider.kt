package com.vlad1m1r.watchface.data

import android.content.SharedPreferences

const val KEY_ANALOG_WATCH_FACE = "analog_watch_face_key"

const val KEY_WATCH_FACE_TYPE = "watch_face_type"
private const val KEY_HAS_COMPLICATIONS_IN_AMBIENT_MODE = "has_complications_in_ambient_mode"
private const val KEY_HAS_TICKS_IN_AMBIENT_MODE = "has_ticks_in_ambient_mode"
private const val KEY_HAS_TICKS_IN_INTERACTIVE_MODE = "has_ticks_in_interactive_mode"
private const val KEY_HAS_BLACK_BACKGROUND = "has_black_background"
private const val KEY_HAS_SECOND_HAND = "has_second_hand"

class DataProvider(private val sharedPreferences: SharedPreferences) {

    fun getWatchFaceType(): WatchFaceType {
        return WatchFaceType.fromId(sharedPreferences.getInt(KEY_WATCH_FACE_TYPE, WatchFaceType.ORIGINAL.id))
    }

    fun setWatchFaceType(watchFaceType: WatchFaceType) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_WATCH_FACE_TYPE, watchFaceType.id)
        editor.apply()
    }

    fun hasComplicationsInAmbientMode() = sharedPreferences.getBoolean(KEY_HAS_COMPLICATIONS_IN_AMBIENT_MODE, true)

    fun setHasComplicationsInAmbientMode(complicationsInAmbientMode: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_HAS_COMPLICATIONS_IN_AMBIENT_MODE, complicationsInAmbientMode)
        editor.apply()
    }

    fun hasTicksInAmbientMode() = sharedPreferences.getBoolean(KEY_HAS_TICKS_IN_AMBIENT_MODE, true)

    fun setHasTicksInAmbientMode(ticksInAmbientMode: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_HAS_TICKS_IN_AMBIENT_MODE, ticksInAmbientMode)
        editor.apply()
    }

    fun hasTicksInInteractiveMode() = sharedPreferences.getBoolean(KEY_HAS_TICKS_IN_INTERACTIVE_MODE, true)

    fun setHasTicksInInteractiveMode(ticksInInteractiveMode: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_HAS_TICKS_IN_INTERACTIVE_MODE, ticksInInteractiveMode)
        editor.apply()
    }

    fun hasBlackBackground() = sharedPreferences.getBoolean(KEY_HAS_BLACK_BACKGROUND, false)

    fun setHasBlackBackground(hasBlackBackground: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_HAS_BLACK_BACKGROUND, hasBlackBackground)
        editor.apply()
    }

    fun hasSecondHand() = sharedPreferences.getBoolean(KEY_HAS_SECOND_HAND, true)

    fun setHasSecondHand(hasSecondHand: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_HAS_SECOND_HAND, hasSecondHand)
        editor.apply()
    }
}