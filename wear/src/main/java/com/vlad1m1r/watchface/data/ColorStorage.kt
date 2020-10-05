package com.vlad1m1r.watchface.data

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.vlad1m1r.watchface.R

private const val KEY_HAS_BLACK_BACKGROUND = "has_black_background"
const val KEY_HOURS_HAND_COLOR = "hours_hand_color"
const val KEY_MINUTES_HAND_COLOR = "minutes_hand_color"
const val KEY_SECONDS_HAND_COLOR = "seconds_hand_color"
const val KEY_CENTRAL_CIRCLE_COLOR = "central_circle_color"

const val KEY_HOUR_TICKS_COLOR = "hour_ticks_color"
const val KEY_MINUTE_TICKS_COLOR = "minute_ticks_color"

const val KEY_BACKGROUND_LEFT_COLOR = "background_left_color"
const val KEY_BACKGROUND_RIGHT_COLOR = "background_right_color"

const val KEY_COMPLICATIONS_TEXT_COLOR = "complications_text_color"
const val KEY_COMPLICATIONS_TITLE_COLOR = "complications_title_color"
const val KEY_COMPLICATIONS_ICON_COLOR = "complications_icon_color"
const val KEY_COMPLICATIONS_BORDER_COLOR = "complications_border_color"
const val KEY_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR = "complications_ranged_value_primary_color"
const val KEY_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR =
    "complications_ranged_value_secondary_color"
const val KEY_COMPLICATIONS_BACKGROUND_COLOR = "complications_background_color"

class ColorStorage(private val context: Context, private val sharedPreferences: SharedPreferences) {

    private fun hasBlackBackground() = sharedPreferences.getBoolean(KEY_HAS_BLACK_BACKGROUND, false)

    private fun hasSecondHand() = sharedPreferences.getBoolean(KEY_HAS_SECOND_HAND, true)

    private fun hasHands() = sharedPreferences.getBoolean(KEY_HAS_HANDS, true)

    fun getHoursHandColor(): Int {
        val defaultColor by lazy {
            if (hasHands())
                ContextCompat.getColor(context, R.color.watch_hand_hour)
            else
                Color.TRANSPARENT
        }

        return sharedPreferences.getInt(
            KEY_HOURS_HAND_COLOR,
            defaultColor
        )
    }

    fun setHoursHandColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_HOURS_HAND_COLOR, color)
        editor.apply()
    }

    fun getMinutesHandColor(): Int {
        val defaultColor by lazy {
            if (hasHands())
                ContextCompat.getColor(context, R.color.watch_hand_minute)
            else
                Color.TRANSPARENT
        }

        return sharedPreferences.getInt(
            KEY_MINUTES_HAND_COLOR,
            defaultColor
        )
    }

    fun setMinutesHandColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_MINUTES_HAND_COLOR, color)
        editor.apply()
    }

    fun getSecondsHandColor(): Int {
        val defaultColor by lazy {
            if (hasHands() && hasSecondHand())
                ContextCompat.getColor(context, R.color.watch_hand_second)
            else
                Color.TRANSPARENT
        }

        return sharedPreferences.getInt(
            KEY_SECONDS_HAND_COLOR,
            defaultColor
        )
    }

    fun setSecondsHandColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_SECONDS_HAND_COLOR, color)
        editor.apply()
    }

    fun getCentralCircleColor(): Int {
        return sharedPreferences.getInt(
            KEY_CENTRAL_CIRCLE_COLOR,
            ContextCompat.getColor(context, R.color.watch_hand_second)
        )
    }

    fun setCentralCircleColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CENTRAL_CIRCLE_COLOR, color)
        editor.apply()
    }

    fun getHourTicksColor(): Int {
        return sharedPreferences.getInt(
            KEY_HOUR_TICKS_COLOR,
            ContextCompat.getColor(context, R.color.watch_hour_ticks)
        )
    }

    fun setHourTicksColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_HOUR_TICKS_COLOR, color)
        editor.apply()
    }

    fun getMinuteTicksColor(): Int {
        return sharedPreferences.getInt(
            KEY_MINUTE_TICKS_COLOR,
            ContextCompat.getColor(context, R.color.watch_minute_ticks)
        )
    }

    fun setMinuteTicksColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_MINUTE_TICKS_COLOR, color)
        editor.apply()
    }

    fun getBackgroundLeftColor(): Int {
        val defaultColor by lazy {
            if (hasBlackBackground()) ContextCompat.getColor(
                context,
                R.color.black
            ) else ContextCompat.getColor(context, R.color.watch_background_left)
        }
        return sharedPreferences.getInt(KEY_BACKGROUND_LEFT_COLOR, defaultColor)
    }

    fun setBackgroundLeftColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_BACKGROUND_LEFT_COLOR, color)
        editor.apply()
    }

    fun getBackgroundRightColor(): Int {
        val defaultColor by lazy {
            if (hasBlackBackground()) ContextCompat.getColor(
                context,
                R.color.black
            ) else ContextCompat.getColor(context, R.color.watch_background_right)
        }
        return sharedPreferences.getInt(KEY_BACKGROUND_RIGHT_COLOR, defaultColor)
    }


    fun setBackgroundRightColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_BACKGROUND_RIGHT_COLOR, color)
        editor.apply()
    }

    private fun getTicksLayoutType(): TicksLayoutType {
        return TicksLayoutType.fromId(
            sharedPreferences.getInt(
                KEY_WATCH_FACE_TYPE,
                TicksLayoutType.ORIGINAL.id
            )
        )
    }

    fun getComplicationsTextColor(): Int {
        val defaultColor by lazy {
            if (getTicksLayoutType() == TicksLayoutType.ORIGINAL)
                ContextCompat.getColor(context, R.color.watch_complications)
            else
                ContextCompat.getColor(context, R.color.watch_design2_complications_text)
        }
        return sharedPreferences.getInt(KEY_COMPLICATIONS_TEXT_COLOR, defaultColor)
    }

    fun setComplicationsTextColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_COMPLICATIONS_TEXT_COLOR, color)
        editor.apply()
    }

    fun getComplicationsTitleColor(): Int {
        val defaultColor by lazy {
            if (getTicksLayoutType() == TicksLayoutType.ORIGINAL)
                ContextCompat.getColor(context, R.color.watch_complications)
            else
                ContextCompat.getColor(context, R.color.watch_design2_complications)
        }
        return sharedPreferences.getInt(KEY_COMPLICATIONS_TITLE_COLOR, defaultColor)
    }

    fun setComplicationsTitleColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_COMPLICATIONS_TITLE_COLOR, color)
        editor.apply()
    }

    fun getComplicationsIconColor(): Int {
        val defaultColor by lazy {
            if (getTicksLayoutType() == TicksLayoutType.ORIGINAL)
                ContextCompat.getColor(context, R.color.watch_complications)
            else
                ContextCompat.getColor(context, R.color.watch_design2_complications)
        }
        return sharedPreferences.getInt(KEY_COMPLICATIONS_ICON_COLOR, defaultColor)
    }

    fun setComplicationsIconColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_COMPLICATIONS_ICON_COLOR, color)
        editor.apply()
    }

    fun getComplicationsBorderColor(): Int {
        val defaultColor by lazy {
            if (getTicksLayoutType() == TicksLayoutType.ORIGINAL)
                ContextCompat.getColor(context, R.color.watch_complications)
            else
                ContextCompat.getColor(context, R.color.watch_design2_complications_border)
        }
        return sharedPreferences.getInt(KEY_COMPLICATIONS_BORDER_COLOR, defaultColor)
    }

    fun setComplicationsBorderColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_COMPLICATIONS_BORDER_COLOR, color)
        editor.apply()
    }

    fun getComplicationsRangedValuePrimaryColor(): Int {
        val defaultColor by lazy {
            if (getTicksLayoutType() == TicksLayoutType.ORIGINAL)
                ContextCompat.getColor(context, R.color.watch_hand_hour)
            else
                ContextCompat.getColor(context, R.color.watch_design2_complications_ranged_primary)
        }
        return sharedPreferences.getInt(KEY_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR, defaultColor)
    }

    fun setComplicationsRangedValuePrimaryColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR, color)
        editor.apply()
    }

    fun getComplicationsRangedValueSecondaryColor(): Int {
        val defaultColor by lazy {
            if (getTicksLayoutType() == TicksLayoutType.ORIGINAL)
                ContextCompat.getColor(context, R.color.watch_hand_minute)
            else
                ContextCompat.getColor(
                    context,
                    R.color.watch_design2_complications_ranged_secondary
                )
        }
        return sharedPreferences.getInt(
            KEY_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR,
            defaultColor
        )
    }

    fun setComplicationsRangedValueSecondaryColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR, color)
        editor.apply()
    }

    fun getComplicationsBackgroundColor(): Int {
        val defaultColor by lazy {
            if (getTicksLayoutType() == TicksLayoutType.ORIGINAL)
                ContextCompat.getColor(context, R.color.watch_complication_background)
            else
                ContextCompat.getColor(context, R.color.watch_complication_background)
        }
        return sharedPreferences.getInt(KEY_COMPLICATIONS_BACKGROUND_COLOR, defaultColor)
    }

    fun setComplicationsBackgroundColor(@ColorInt color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_COMPLICATIONS_BACKGROUND_COLOR, color)
        editor.apply()
    }
}