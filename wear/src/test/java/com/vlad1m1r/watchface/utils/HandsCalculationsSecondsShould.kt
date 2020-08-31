package com.vlad1m1r.watchface.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

@RunWith(Parameterized::class)
class HandsCalculationsSecondsShould(private val timeInMillis: Long, private val rotation: Float) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(1540054913000, 318),
            arrayOf(1540054713000, 198),
            arrayOf(1540014613000, 78),
            arrayOf(1540054311000, 306),
            arrayOf(1540052110000, 60),
            arrayOf(1540050000000, 0),
            arrayOf(1540054913100, 318.59998f),
            arrayOf(1540052913020, 198.12f),
            arrayOf(1540050213003, 198.01799f),
            arrayOf(1540054824123, 144.73799f)
        )
    }

    val calendar: Calendar = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone("CET")
    }

    @Test
    fun calculateSecondsRotation() {
        calendar.timeInMillis = timeInMillis

        assertThat(calendar.secondsRotation()).isEqualTo(rotation)
    }
}