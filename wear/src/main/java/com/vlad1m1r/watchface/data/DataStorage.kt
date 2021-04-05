package com.vlad1m1r.watchface.data

import android.content.SharedPreferences

const val KEY_ANALOG_WATCH_FACE = "analog_watch_face_key"

const val KEY_WATCH_FACE_TYPE = "watch_face_type"
private const val KEY_HAS_COMPLICATIONS_IN_AMBIENT_MODE = "has_complications_in_ambient_mode"
private const val KEY_HAS_TICKS_IN_AMBIENT_MODE = "has_ticks_in_ambient_mode"
private const val KEY_HAS_TICKS_IN_INTERACTIVE_MODE = "has_ticks_in_interactive_mode"

const val KEY_HAS_SECOND_HAND = "has_second_hand"
const val KEY_HAS_HANDS = "has_hands"
const val KEY_HAS_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS = "has_bigger_top_and_bottom_complications"
const val KEY_HAS_SMOOTH_SECONDS_HAND = "has_smooth_seconds_hand"

const val KEY_SHOULD_ADJUST_TO_SQUARE_SCREEN = "should_adjust_to_square_screen"

const val KEY_HAS_BLACK_AMBIENT_BACKGROUND = "has_black_ambient_background"

class DataStorage(private val sharedPreferences: SharedPreferences) {

    fun getTicksLayoutType(): TicksLayoutType {
        return TicksLayoutType.fromId(sharedPreferences.getInt(KEY_WATCH_FACE_TYPE, TicksLayoutType.ORIGINAL.id))
    }

    fun setTicksLayoutType(ticksLayoutType: TicksLayoutType) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_WATCH_FACE_TYPE, ticksLayoutType.id)
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

    fun hasBiggerTopAndBottomComplications() = sharedPreferences.getBoolean(KEY_HAS_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS, false)

    fun setHasBiggerTopAndBottomComplications(biggerTopAndBottomComplications: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_HAS_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS, biggerTopAndBottomComplications)
        editor.apply()
    }

    fun hasSmoothSecondsHand() = sharedPreferences.getBoolean(KEY_HAS_SMOOTH_SECONDS_HAND, false)

    fun setHasSmoothSecondsHand(hasSmoothSecondsHand: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_HAS_SMOOTH_SECONDS_HAND, hasSmoothSecondsHand)
        editor.apply()
    }

    fun shouldAdjustToSquareScreen() = sharedPreferences.getBoolean(KEY_SHOULD_ADJUST_TO_SQUARE_SCREEN, false)

    fun setShouldAdjustToSquareScreen(shouldAdjustToSquareScreen: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_SHOULD_ADJUST_TO_SQUARE_SCREEN, shouldAdjustToSquareScreen)
        editor.apply()
    }

    fun hasBlackAmbientBackground() = sharedPreferences.getBoolean(KEY_HAS_BLACK_AMBIENT_BACKGROUND, false)

    fun setHasBlackAmbientBackground(hasBlackAmbientBackground: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_HAS_BLACK_AMBIENT_BACKGROUND, hasBlackAmbientBackground)
        editor.apply()
    }
}