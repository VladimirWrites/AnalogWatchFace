package com.vlad1m1r.watchface.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.rendering.ComplicationDrawable
import com.vlad1m1r.watchface.R

const val LEFT_COMPLICATION_ID = 100
const val RIGHT_COMPLICATION_ID = 101

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

    override fun setCenter(centerX: Float, centerY: Float) {
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

    fun draw(canvas: Canvas, currentTime: Long) {
        COMPLICATION_SUPPORTED_TYPES.keys.forEach {
            complicationDrawables[it]!!.draw(canvas, currentTime)
        }
    }
}