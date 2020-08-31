package com.vlad1m1r.watchface.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

@RunWith(Parameterized::class)
class HandsCalculationsMinutesShould(private val timeInMillis: Long, private val rotation: Float) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(1540054913000, 6),
            arrayOf(1540054713000, 348),
            arrayOf(1540014613000, 300),
            arrayOf(1540054311000, 306),
            arrayOf(1540052110000, 90),
            arrayOf(1540050000000, 240),
            arrayOf(1540054913100, 6),
            arrayOf(1540052913020, 168),
            arrayOf(1540050213003, 258),
            arrayOf(1540054824123, 0)
        )
    }

    val calendar: Calendar = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone("CET")
    }

    @Test
    fun calculateMinutesRotation() {
        calendar.timeInMillis = timeInMillis

        assertThat(calendar.minutesRotation()).isEqualTo(rotation)
    }
}