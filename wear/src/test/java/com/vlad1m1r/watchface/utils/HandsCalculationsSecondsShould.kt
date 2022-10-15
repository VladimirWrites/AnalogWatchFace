package com.vlad1m1r.watchface.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.time.*
import java.util.*

@RunWith(Parameterized::class)
class HandsCalculationsSecondsShould(private val timeInMillis: Long, private val rotation: Float) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(1540054913000L, 318),
            arrayOf(1540054713000L, 198),
            arrayOf(1540014613000L, 78),
            arrayOf(1540054311000L, 306),
            arrayOf(1540052110000L, 60),
            arrayOf(1540050000000L, 0),
            arrayOf(1540054913100L, 318.59998f),
            arrayOf(1540052913020L, 198.12f),
            arrayOf(1540050213003L, 198.01799f),
            arrayOf(1540054824123L, 144.73799f)
        )
    }

    @Test
    fun calculateSecondsRotation() {
        val zonedDateTime = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(timeInMillis),
            ZoneId.of("CET")
        )

        assertThat(zonedDateTime.secondsRotation()).isEqualTo(rotation)
    }
}