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
            arrayOf(1540054913000L, 6),
            arrayOf(1540054713000L, 348),
            arrayOf(1540014613000L, 300),
            arrayOf(1540054311000L, 306),
            arrayOf(1540052110000L, 90),
            arrayOf(1540050000000L, 240),
            arrayOf(1540054913100L, 6),
            arrayOf(1540052913020L, 168),
            arrayOf(1540050213003L, 258),
            arrayOf(1540054824123L, 0)
        )
    }

    private val calendar: Calendar = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone("CET")
    }

    @Test
    fun calculateMinutesRotation() {
        calendar.timeInMillis = timeInMillis

        assertThat(calendar.minutesRotation()).isEqualTo(rotation)
    }
}