package com.vlad1m1r.watchface.utils

import androidx.annotation.ColorInt
import androidx.core.graphics.toColorInt
import androidx.wear.watchface.style.UserStyle
import androidx.wear.watchface.style.UserStyleSetting
import com.vlad1m1r.watchface.data.TicksLayoutType
import com.vlad1m1r.watchface.data.state.*

val BACKGROUND_BLACK_IN_AMBIENT = UserStyleSetting.Id("background_black_in_ambient")
val BACKGROUND_LEFT_COLOR = UserStyleSetting.Id("background_left_color")
val BACKGROUND_RIGHT_COLOR = UserStyleSetting.Id("background_right_color")

val TICKS_HAS_IN_AMBIENT_MODE = UserStyleSetting.Id("ticks_has_in_ambient_mode")
val TICKS_HAS_IN_INTERACTIVE_MODE = UserStyleSetting.Id("ticks_has_in_interactive_mode")
val TICKS_LAYOUT_TYPE = UserStyleSetting.Id("ticks_layout_type")
val TICKS_HOUR_COLOR = UserStyleSetting.Id("ticks_hour_color")
val TICKS_MINUTE_COLOR = UserStyleSetting.Id("ticks_minute_color")
val TICKS_SHOULD_ADJUST_TO_SQUARE = UserStyleSetting.Id("ticks_should_adjust_to_square")

val HAND_SECONDS_IS_SMOOTH = UserStyleSetting.Id("hands_seconds_is_smooth")
val HAND_SECONDS_HAS = UserStyleSetting.Id("hand_seconds_has")
val HAND_SECONDS_COLOR = UserStyleSetting.Id("hand_seconds_color")
val HAND_SECONDS_WIDTH = UserStyleSetting.Id("hand_seconds_width")
val HAND_SECONDS_LENGTH_SCALE = UserStyleSetting.Id("hand_seconds_length_scale")

val HAND_MINUTES_COLOR = UserStyleSetting.Id("hand_minutes_color")
val HAND_MINUTES_WIDTH = UserStyleSetting.Id("hand_minutes_width")
val HAND_MINUTES_LENGTH_SCALE = UserStyleSetting.Id("hand_minutes_length_scale")

val HAND_HOURS_COLOR = UserStyleSetting.Id("hand_hours_color")
val HAND_HOURS_WIDTH = UserStyleSetting.Id("hand_hours_width")
val HAND_HOURS_LENGTH_SCALE = UserStyleSetting.Id("hand_hours_length_scale")

val HAND_CIRCLE_COLOR = UserStyleSetting.Id("hand_circle_color")
val HAND_CIRCLE_WIDTH = UserStyleSetting.Id("hand_circle_width")
val HAND_CIRCLE_RADIUS = UserStyleSetting.Id("hand_circle_radius")
val HAND_CIRCLE_HAS_IN_AMBIENT_MODE = UserStyleSetting.Id("hand_circle_has_in_ambient_mode")

val HANDS_HAS_IN_INTERACTIVE = UserStyleSetting.Id("hands_has_in_interactive")
val HANDS_KEEP_COLOR_IN_AMBIENT_MODE = UserStyleSetting.Id("hands_keep_color_in_ambient_mode")
val HANDS_HAS = UserStyleSetting.Id("hands_has")

val COMPLICATION_HAS_IN_AMBIENT_MODE = UserStyleSetting.Id("complication_has_in_ambient_mode")
val COMPLICATION_HAS_BIGGER_TOP_AND_BOTTOM = UserStyleSetting.Id("complication_has_bigger_top_and_bottom")
val COMPLICATION_TEXT_COLOR = UserStyleSetting.Id("complication_text_color")
val COMPLICATION_TITLE_COLOR = UserStyleSetting.Id("complication_title_color")
val COMPLICATION_ICON_COLOR = UserStyleSetting.Id("complication_icon_color")
val COMPLICATION_BORDER_COLOR = UserStyleSetting.Id("complication_border_color")
val COMPLICATION_RANGED_VALUE_PRIMARY_COLOR = UserStyleSetting.Id("complication_ranged_primary_color")
val COMPLICATION_RANGED_VALUE_SECONDARY_COLOR = UserStyleSetting.Id("complication_ranged_secondary_color")
val COMPLICATION_BACKGROUND_COLOR = UserStyleSetting.Id("complication_background_color")

val WATCH_USE_ANTIALIASING = UserStyleSetting.Id("watch_use_antialiasing")

fun UserStyle.toWatchFaceState(): WatchFaceState {
    return WatchFaceState(
        this.toBackgroundState(),
        this.toTicksState(),
        this.toHandsState(),
        this.toComplicationsState()
    )
}

private fun UserStyle.toBackgroundState(): BackgroundState {
    return BackgroundState(
        this.getAsBooleanOption(BACKGROUND_BLACK_IN_AMBIENT),
        this.getAsColorOption(BACKGROUND_LEFT_COLOR),
        this.getAsColorOption(BACKGROUND_RIGHT_COLOR)
    )
}

private fun UserStyle.toTicksState(): TicksState {
    return TicksState(
        this.getAsBooleanOption(TICKS_HAS_IN_AMBIENT_MODE),
        this.getAsBooleanOption(TICKS_HAS_IN_INTERACTIVE_MODE),
        TicksLayoutType.fromId(this.getAsIntOption(TICKS_LAYOUT_TYPE)),
        this.getAsColorOption(TICKS_HOUR_COLOR),
        this.getAsColorOption(TICKS_MINUTE_COLOR),
        this.getAsBooleanOption(TICKS_SHOULD_ADJUST_TO_SQUARE),
        this.getAsBooleanOption(WATCH_USE_ANTIALIASING)
    )
}

private fun UserStyle.toHandsState(): HandsState {
    return HandsState(
        this.getAsBooleanOption(HAND_SECONDS_IS_SMOOTH),
        this.getAsBooleanOption(HAND_SECONDS_HAS),
        this.toSecondsHandState(),
        this.toMinutesHandState(),
        this.toHoursHandState(),
        this.toCircleState(),
        this.getAsBooleanOption(HANDS_HAS_IN_INTERACTIVE),
        this.getAsBooleanOption(HANDS_KEEP_COLOR_IN_AMBIENT_MODE),
        this.getAsBooleanOption(HANDS_HAS),
        this.getAsBooleanOption(WATCH_USE_ANTIALIASING)
    )
}

private fun UserStyle.toSecondsHandState(): HandState {
    return HandState(
        this.getAsColorOption(HAND_SECONDS_COLOR),
        this.getAsIntOption(HAND_SECONDS_WIDTH),
        this.getAsFloatOption(HAND_SECONDS_LENGTH_SCALE)
    )
}

private fun UserStyle.toMinutesHandState(): HandState {
    return HandState(
        this.getAsColorOption(HAND_MINUTES_COLOR),
        this.getAsIntOption(HAND_MINUTES_WIDTH),
        this.getAsFloatOption(HAND_MINUTES_LENGTH_SCALE)
    )
}

private fun UserStyle.toHoursHandState(): HandState {
    return HandState(
        this.getAsColorOption(HAND_HOURS_COLOR),
        this.getAsIntOption(HAND_HOURS_WIDTH),
        this.getAsFloatOption(HAND_HOURS_LENGTH_SCALE)
    )
}

private fun UserStyle.toCircleState(): CircleState {
    return CircleState(
        this.getAsColorOption(HAND_CIRCLE_COLOR),
        this.getAsIntOption(HAND_CIRCLE_WIDTH),
        this.getAsIntOption(HAND_CIRCLE_RADIUS),
        this.getAsBooleanOption(HAND_CIRCLE_HAS_IN_AMBIENT_MODE)
    )
}

private fun UserStyle.toComplicationsState(): ComplicationsState {
    return ComplicationsState(
        this.getAsBooleanOption(COMPLICATION_HAS_IN_AMBIENT_MODE),
        this.getAsBooleanOption(COMPLICATION_HAS_BIGGER_TOP_AND_BOTTOM),
        this.getAsColorOption(COMPLICATION_TEXT_COLOR),
        this.getAsColorOption(COMPLICATION_TITLE_COLOR),
        this.getAsColorOption(COMPLICATION_ICON_COLOR),
        this.getAsColorOption(COMPLICATION_BORDER_COLOR),
        this.getAsColorOption(COMPLICATION_RANGED_VALUE_PRIMARY_COLOR),
        this.getAsColorOption(COMPLICATION_RANGED_VALUE_SECONDARY_COLOR),
        this.getAsColorOption(COMPLICATION_BACKGROUND_COLOR)
    )
}

private fun UserStyle.getAsBooleanOption(id: UserStyleSetting.Id): Boolean {
    return (this[id] as UserStyleSetting.BooleanUserStyleSetting.BooleanOption).value
}

private fun UserStyle.getAsIntOption(id: UserStyleSetting.Id): Int {
    return (this[id] as UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption).value.toInt()
}

@ColorInt
private fun UserStyle.getAsColorOption(id: UserStyleSetting.Id): Int {
    return (this[id] as UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption).value.toInt()
}

private fun UserStyle.getAsFloatOption(id: UserStyleSetting.Id): Float {
    return (this[id] as UserStyleSetting.DoubleRangeUserStyleSetting.DoubleRangeOption).value.toFloat()
}