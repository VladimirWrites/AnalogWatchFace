package com.vlad1m1r.watchface.utils

import java.util.*

fun Calendar.secondsRotation() = (get(Calendar.SECOND) + get(Calendar.MILLISECOND) / 1000f) * 6f

fun Calendar.minutesRotation() = get(Calendar.MINUTE) * 6f

fun Calendar.hoursRotation() = get(Calendar.HOUR) * 30f + get(Calendar.MINUTE) / 2f