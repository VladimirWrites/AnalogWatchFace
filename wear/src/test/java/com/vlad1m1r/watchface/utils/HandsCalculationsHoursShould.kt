package com.vlad1m1r.watchface.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

@RunWith(Parameterized::class)
class HandsCalculationsHoursShould(private val timeInMillis: Long, private val rotation: Float) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(1540114563000, 348),
            arrayOf(1540255423100, 81.5f),
            arrayOf(1540115913000, 359),
            arrayOf(1540256898000, 94),
            arrayOf(1540052710000, 192.5f),
            arrayOf(1540050000000, 170),
            arrayOf(1540214113100, 97.5f),
            arrayOf(1540052913020, 194),
            arrayOf(1540050213003, 171.5f),
            arrayOf(1540054824123, 210)
        )
    }

    val calendar = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone("CET")
    }

    @Test
    fun calculateHoursRotation() {
        calendar.timeInMillis = timeInMillis

        assertThat(calendar.hoursRotation()).isEqualTo(rotation)
    }
}