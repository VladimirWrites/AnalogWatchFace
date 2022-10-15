package com.vlad1m1r.watchface.utils

import java.time.ZonedDateTime

fun ZonedDateTime.secondsRotation() = (second + nano / 1000000000f) * 6f

fun ZonedDateTime.minutesRotation() = minute * 6f

fun ZonedDateTime.hoursRotation() = hour * 30f + minute / 2f