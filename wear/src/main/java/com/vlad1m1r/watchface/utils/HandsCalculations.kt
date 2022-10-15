package com.vlad1m1r.watchface.utils

import java.time.ZonedDateTime
import java.util.*

fun Calendar.secondsRotation() = (get(Calendar.SECOND) + get(Calendar.MILLISECOND) / 1000f) * 6f

fun Calendar.minutesRotation() = get(Calendar.MINUTE) * 6f

fun Calendar.hoursRotation() = get(Calendar.HOUR) * 30f + get(Calendar.MINUTE) / 2f

fun ZonedDateTime.secondsRotation() = (second + nano / 1000000000f) * 6f

fun ZonedDateTime.minutesRotation() = minute * 6f

fun ZonedDateTime.hoursRotation() = hour * 30f + minute / 2f