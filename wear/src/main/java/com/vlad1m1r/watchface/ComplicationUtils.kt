package com.vlad1m1r.watchface

import android.content.Context
import android.graphics.RectF
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.complications.ComplicationSlotBounds
import androidx.wear.watchface.complications.DefaultComplicationDataSourcePolicy
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.style.CurrentUserStyleRepository
import com.vlad1m1r.watchface.utils.ScreenMetricsCompat

fun createComplicationSlotManager(
    context: Context,
    currentUserStyleRepository: CurrentUserStyleRepository,
): ComplicationSlotsManager {

    val screenWidthDp = ScreenMetricsCompat.getScreenSize(context).width
    val screenHeightDp = ScreenMetricsCompat.getScreenSize(context).height

    val defaultCanvasComplicationFactory =
        CanvasComplicationFactory { watchState, listener ->
            CanvasComplicationDrawable(
                ComplicationDrawable.getDrawable(context, R.drawable.complication_drawable)!!,
                watchState,
                listener
            )
        }

    val leftComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Left.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Left.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(),

        bounds = ComplicationSlotBounds(
            getLeftBounds(screenWidthDp / 2f, screenHeightDp / 2f)
        )
    ).build()

    val rightComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Right.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Right.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(),
        bounds = ComplicationSlotBounds(
            getRightBounds(screenWidthDp / 2f, screenHeightDp / 2f)
        )
    ).build()


    val topComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Top.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Top.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(),
        bounds = ComplicationSlotBounds(
            ComplicationType.values().associate {
                val default = getTopBounds(screenWidthDp / 2f, screenHeightDp / 2f)
                if (it == ComplicationType.LONG_TEXT) {
                    it to getTopWideBounds(screenWidthDp / 2f, screenHeightDp / 2f)
                } else {
                    it to default
                }
            }
        )

    ).build()

    val bottomComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Bottom.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Bottom.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(),
        bounds = ComplicationSlotBounds(
            ComplicationType.values().associate {
                val default = getBottomBounds(screenWidthDp / 2f, screenHeightDp / 2f)
                if (it == ComplicationType.LONG_TEXT) {
                    it to getBottomWideBounds(screenWidthDp / 2f, screenHeightDp / 2f)
                } else {
                    it to default
                }
            }
        )
    ).build()

    val backgroundComplication = ComplicationSlot.createBackgroundComplicationSlotBuilder(
        id = ComplicationConfig.Background.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Background.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(),
    ).build()

    return ComplicationSlotsManager(
        listOf(
            backgroundComplication,
            leftComplication,
            rightComplication,
            topComplication,
            bottomComplication,
        ),
        currentUserStyleRepository
    )
}

private const val ROUND_COMPLICATION_SIZE = 0.25f
private const val WIDE_COMPLICATION_WIDTH = 0.6f
private const val WIDE_COMPLICATION_HEIGHT = 0.15f
private const val CENTER = 0.5f
private const val PADDING_FROM_CENTER = 0.1f
private const val PADDING_FROM_CENTER_WIDE = 0.2f

private fun getLeftBounds(centerX: Float, centerY: Float): RectF {
    val height = ROUND_COMPLICATION_SIZE * centerX / centerY
    val width = ROUND_COMPLICATION_SIZE
    val right = CENTER - PADDING_FROM_CENTER * centerY / centerX

    return RectF(
        right - width,
        CENTER - height / 2,
        right,
        CENTER + height / 2
    )
}

private fun getRightBounds(centerX: Float, centerY: Float): RectF {
    val height = ROUND_COMPLICATION_SIZE * centerX / centerY
    val width = ROUND_COMPLICATION_SIZE
    val left = CENTER + PADDING_FROM_CENTER * centerY / centerX

    return RectF(
        left,
        CENTER - height / 2,
        left + width,
        CENTER + height / 2
    )
}

private fun getTopBounds(centerX: Float, centerY: Float): RectF {
    val height = ROUND_COMPLICATION_SIZE * centerX / centerY
    val width = ROUND_COMPLICATION_SIZE
    val bottom = CENTER - PADDING_FROM_CENTER * centerX / centerY

    return RectF(
        CENTER - width / 2,
        bottom - height,
        CENTER + width / 2,
        bottom
    )
}

private fun getTopWideBounds(centerX: Float, centerY: Float): RectF {
    val height = WIDE_COMPLICATION_HEIGHT * centerX / centerY
    val width = WIDE_COMPLICATION_WIDTH
    val bottom = CENTER - PADDING_FROM_CENTER_WIDE * centerX / centerY

    return RectF(
        CENTER - width / 2,
        bottom - height,
        CENTER + width / 2,
        bottom
    )
}

private fun getBottomBounds(centerX: Float, centerY: Float): RectF {
    val height = ROUND_COMPLICATION_SIZE * centerX / centerY
    val width = ROUND_COMPLICATION_SIZE
    val top = CENTER + PADDING_FROM_CENTER * centerX / centerY
    return RectF(
        CENTER - width / 2,
        top,
        CENTER + width / 2,
        (top + height)
    )
}

private fun getBottomWideBounds(centerX: Float, centerY: Float): RectF {
    val height = WIDE_COMPLICATION_HEIGHT * centerX / centerY
    val width = WIDE_COMPLICATION_WIDTH
    val top = CENTER + PADDING_FROM_CENTER_WIDE * centerX / centerY
    return RectF(
        CENTER - width / 2,
        top,
        CENTER + width / 2,
        (top + height)
    )
}


