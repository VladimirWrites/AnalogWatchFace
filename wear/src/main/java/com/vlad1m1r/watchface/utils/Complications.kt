package com.vlad1m1r.watchface.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.rendering.ComplicationDrawable

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
        ComplicationData.TYPE_LONG_TEXT,
        ComplicationData.TYPE_RANGED_VALUE,
        ComplicationData.TYPE_ICON,
        ComplicationData.TYPE_SHORT_TEXT,
        ComplicationData.TYPE_SMALL_IMAGE
    ),
    BOTTOM_COMPLICATION_ID to intArrayOf(
        ComplicationData.TYPE_LONG_TEXT,
        ComplicationData.TYPE_RANGED_VALUE,
        ComplicationData.TYPE_ICON,
        ComplicationData.TYPE_SHORT_TEXT,
        ComplicationData.TYPE_SMALL_IMAGE
    )
)

class Complications(val context: Context): WatchView(context) {

    private val complicationDrawables = mutableMapOf<Int, ComplicationDrawable?>().apply {
        put(LEFT_COMPLICATION_ID, null)
        put(RIGHT_COMPLICATION_ID, null)
        put(TOP_COMPLICATION_ID, null)
        put(BOTTOM_COMPLICATION_ID, null)
    }
    private val complicationData = mutableMapOf<Int, ComplicationData?>()
    var centerInvalidated = true
        private set

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
        this.complicationData[id] = complicationData
        complicationDrawables[id]?.setComplicationData(complicationData)
            ?: throw IllegalArgumentException("Unsupported ComplicationDrawable id: $id")
        centerInvalidated = true
    }

    fun setComplicationDrawable(drawableResId: Int) {
        complicationDrawables.keys.forEach { complicationId ->
            complicationDrawables[complicationId] =
                (context.getDrawable(drawableResId) as ComplicationDrawable).apply {
                    setContext(context)
                    complicationData[complicationId]?.let { setComplicationData(it) }
                }
        }
        centerInvalidated = true
    }

    override fun setCenter(center: Point) {
        centerInvalidated = false
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
            if (complicationData[TOP_COMPLICATION_ID]?.type == ComplicationData.TYPE_LONG_TEXT) {
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
            if (complicationData[BOTTOM_COMPLICATION_ID]?.type == ComplicationData.TYPE_LONG_TEXT) {
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