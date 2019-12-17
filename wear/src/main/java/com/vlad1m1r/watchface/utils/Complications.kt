package com.vlad1m1r.watchface.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.rendering.ComplicationDrawable
import com.vlad1m1r.watchface.R

const val LEFT_COMPLICATION_ID = 100
const val RIGHT_COMPLICATION_ID = 101
const val TOP_COMPLICATION_ID = 102
const val BOTTOM_COMPLICATION_ID = 103

val COMPLICATION_SUPPORTED_TYPES = mapOf(
    LEFT_COMPLICATION_ID to intArrayOf(
        ComplicationData.TYPE_RANGED_VALUE,
        ComplicationData.TYPE_ICON,
        ComplicationData.TYPE_SHORT_TEXT,
        ComplicationData.TYPE_SMALL_IMAGE
    ),
    RIGHT_COMPLICATION_ID to intArrayOf(
        ComplicationData.TYPE_RANGED_VALUE,
        ComplicationData.TYPE_ICON,
        ComplicationData.TYPE_SHORT_TEXT,
        ComplicationData.TYPE_SMALL_IMAGE
    ),
    TOP_COMPLICATION_ID to intArrayOf(
        ComplicationData.TYPE_RANGED_VALUE,
        ComplicationData.TYPE_ICON,
        ComplicationData.TYPE_SHORT_TEXT,
        ComplicationData.TYPE_SMALL_IMAGE,
        ComplicationData.TYPE_LONG_TEXT
    ),
    BOTTOM_COMPLICATION_ID to intArrayOf(
        ComplicationData.TYPE_RANGED_VALUE,
        ComplicationData.TYPE_ICON,
        ComplicationData.TYPE_SHORT_TEXT,
        ComplicationData.TYPE_SMALL_IMAGE,
        ComplicationData.TYPE_LONG_TEXT
    )
)

class Complications(context: Context): WatchView(context) {

    private val complicationDrawables = mutableMapOf<Int, ComplicationDrawable>().apply {
        put(LEFT_COMPLICATION_ID, (context.getDrawable(R.drawable.complication_drawable) as ComplicationDrawable).apply {
            setContext(context)
        })
        put(RIGHT_COMPLICATION_ID, (context.getDrawable(R.drawable.complication_drawable) as ComplicationDrawable).apply {
            setContext(context)
        })
        put(TOP_COMPLICATION_ID, (context.getDrawable(R.drawable.complication_drawable) as ComplicationDrawable).apply {
            setContext(context)
        })
        put(BOTTOM_COMPLICATION_ID, (context.getDrawable(R.drawable.complication_drawable) as ComplicationDrawable).apply {
            setContext(context)
        })
    }
    private val topBottomComplicationTypes = mutableMapOf<Int, Int>().apply {
        put(
            TOP_COMPLICATION_ID,
            ComplicationData.TYPE_EMPTY
        )
        put(
            BOTTOM_COMPLICATION_ID,
            ComplicationData.TYPE_EMPTY
        )
    }

    fun setMode(mode: Mode) {
        COMPLICATION_SUPPORTED_TYPES.keys.forEach {
            complicationDrawables[it]?.apply {
                setInAmbientMode(mode.isAmbient)
                setLowBitAmbient(mode.isLowBitAmbient)
                setBurnInProtection(mode.isBurnInProtection)
            }
        }
    }

    operator fun get(id: Int): ComplicationDrawable {
        return complicationDrawables[id]?: throw IllegalArgumentException("Unsupported ComplicationDrawable id: $id")
    }

    fun setComplicationData(id: Int, complicationData: ComplicationData?) {
        if (topBottomComplicationTypes.containsKey(id)) {
            topBottomComplicationTypes[id] = complicationData?.type ?: ComplicationData.TYPE_NOT_CONFIGURED
        }
        complicationDrawables[id]?.setComplicationData(complicationData)
            ?: throw IllegalArgumentException("Unsupported ComplicationDrawable id: $id")
    }

    override fun setCenter(center: Point) {
        setBoundsToLeftRightComplications(center.x, center.y)
        setBoundsToTopBottomComplications(center.x, center.y)
    }

    fun draw(canvas: Canvas, currentTime: Long) {
        COMPLICATION_SUPPORTED_TYPES.keys.forEach {
            complicationDrawables[it]!!.draw(canvas, currentTime)
        }
    }

    private fun setBoundsToLeftRightComplications(centerX: Float, centerY: Float) {
        val sizeOfComplication = centerX.toInt() / 2
        val horizontalOffset = (centerX.toInt() - sizeOfComplication) / 2
        val verticalOffset = centerY.toInt() - sizeOfComplication / 2

        val leftBounds = Rect(
            horizontalOffset,
            verticalOffset,
            horizontalOffset + sizeOfComplication,
            verticalOffset + sizeOfComplication
        )

        val rightBounds = Rect(
            centerX.toInt() + horizontalOffset,
            verticalOffset,
            centerX.toInt() + horizontalOffset + sizeOfComplication,
            verticalOffset + sizeOfComplication
        )

        complicationDrawables[LEFT_COMPLICATION_ID]!!.bounds = leftBounds
        complicationDrawables[RIGHT_COMPLICATION_ID]!!.bounds = rightBounds
    }

    private fun setBoundsToTopBottomComplications(centerX: Float, centerY: Float) {
        val sizeOfComplicationSmall = centerX.toInt() / 2
        val horizontalOffsetSmall = centerX.toInt() - (sizeOfComplicationSmall / 2)
        val verticalOffsetSmall = (centerY.toInt() - sizeOfComplicationSmall) / 2

        val heightOfComplicationLarge = centerX.toInt() / 3
        val widthOfComplicationLarge = centerX.toInt()
        val horizontalOffsetLarge = centerX.toInt() / 2
        val verticalOffsetLarge = (centerY.toInt() - heightOfComplicationLarge) / 2

        val topBounds =
            if (topBottomComplicationTypes[TOP_COMPLICATION_ID] == ComplicationData.TYPE_LONG_TEXT) {
                Rect(
                    horizontalOffsetLarge,
                    verticalOffsetLarge,
                    horizontalOffsetLarge + widthOfComplicationLarge,
                    verticalOffsetLarge + heightOfComplicationLarge
                )
            } else {
                Rect(
                    horizontalOffsetSmall,
                    verticalOffsetSmall,
                    horizontalOffsetSmall + sizeOfComplicationSmall,
                    verticalOffsetSmall + sizeOfComplicationSmall
                )
            }

        val bottomBounds =
            if (topBottomComplicationTypes[BOTTOM_COMPLICATION_ID] == ComplicationData.TYPE_LONG_TEXT) {
                Rect(
                    horizontalOffsetLarge,
                    centerY.toInt() + verticalOffsetLarge,
                    horizontalOffsetLarge + widthOfComplicationLarge,
                    centerY.toInt() + verticalOffsetLarge + heightOfComplicationLarge
                )
            } else {
                Rect(
                    horizontalOffsetSmall,
                    centerY.toInt() + verticalOffsetSmall,
                    horizontalOffsetSmall + sizeOfComplicationSmall,
                    centerY.toInt() + verticalOffsetSmall + sizeOfComplicationSmall
                )
            }

        complicationDrawables[TOP_COMPLICATION_ID]!!.bounds = topBounds
        complicationDrawables[BOTTOM_COMPLICATION_ID]!!.bounds = bottomBounds
    }
}