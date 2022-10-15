package com.vlad1m1r.watchface

import androidx.wear.watchface.complications.data.ComplicationExperimental
import androidx.wear.watchface.complications.data.ComplicationType

const val LEFT_COMPLICATION_ID = 100
const val RIGHT_COMPLICATION_ID = 101
const val TOP_COMPLICATION_ID = 102
const val BOTTOM_COMPLICATION_ID = 103
const val BACKGROUND_COMPLICATION_ID = 200

@OptIn(ComplicationExperimental::class)
sealed class ComplicationConfig(val id: Int, val supportedTypes: List<ComplicationType>) {

    object Left : ComplicationConfig(
        LEFT_COMPLICATION_ID,
        listOf(
            ComplicationType.SHORT_TEXT,
            ComplicationType.DISCRETE_RANGED_VALUE,
            ComplicationType.GOAL_PROGRESS,
            ComplicationType.WEIGHTED_ELEMENTS,
            ComplicationType.LIST,
            ComplicationType.PROTO_LAYOUT,
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SMALL_IMAGE,
            ComplicationType.PHOTO_IMAGE,
        )
    )

    object Right : ComplicationConfig(
        RIGHT_COMPLICATION_ID,
        listOf(
            ComplicationType.SHORT_TEXT,
            ComplicationType.DISCRETE_RANGED_VALUE,
            ComplicationType.GOAL_PROGRESS,
            ComplicationType.WEIGHTED_ELEMENTS,
            ComplicationType.LIST,
            ComplicationType.PROTO_LAYOUT,
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SMALL_IMAGE,
            ComplicationType.PHOTO_IMAGE,
        )
    )

    object Top : ComplicationConfig(
        TOP_COMPLICATION_ID,
        listOf(
            ComplicationType.LONG_TEXT,
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE,
            ComplicationType.LIST,
            ComplicationType.PROTO_LAYOUT,
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SMALL_IMAGE,
            ComplicationType.PHOTO_IMAGE,
        )
    )

    object Bottom : ComplicationConfig(
        BOTTOM_COMPLICATION_ID,
        listOf(
            ComplicationType.LONG_TEXT,
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE,
            ComplicationType.LIST,
            ComplicationType.PROTO_LAYOUT,
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SMALL_IMAGE,
            ComplicationType.PHOTO_IMAGE,
        )
    )

    object Background : ComplicationConfig(
        BACKGROUND_COMPLICATION_ID,
        listOf(
            ComplicationType.PHOTO_IMAGE,
            ComplicationType.MONOCHROMATIC_IMAGE,
        )
    )

    companion object {
        fun getSupportedComplicationTypes(id: Int): List<ComplicationType> {
            return when (id) {
                Top.id -> Top.supportedTypes
                Bottom.id -> Bottom.supportedTypes
                Left.id -> Left.supportedTypes
                Right.id -> Right.supportedTypes
                Background.id -> Background.supportedTypes
                else -> emptyList()
            }
        }
    }
}
