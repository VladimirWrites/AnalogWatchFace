package com.vlad1m1r.watchface

import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import androidx.core.graphics.toRectF
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.complications.ComplicationSlotBounds
import androidx.wear.watchface.complications.DefaultComplicationDataSourcePolicy
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.style.CurrentUserStyleRepository
import kotlin.math.max
import kotlin.math.min

fun createComplicationSlotManager(
    context: Context,
    currentUserStyleRepository: CurrentUserStyleRepository,
): ComplicationSlotsManager {

    val screenWidthDp = context.resources.configuration.screenWidthDp
    val screenHeightDp = context.resources.configuration.screenHeightDp
    val minDimen = min(screenWidthDp, screenHeightDp).toFloat()
    val maxDimen = max(screenWidthDp, screenHeightDp).toFloat()

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
            getLeftBounds(minDimen/2f, maxDimen/2f).customToRectF(minDimen/2f, maxDimen/2f)
        )
    ).build()

    val rightComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Right.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Right.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(),
        bounds = ComplicationSlotBounds(
            getRightBounds(minDimen/2f, maxDimen/2f).customToRectF(minDimen/2f, maxDimen/2f)
        )
    ).build()


    val topComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Top.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Top.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(),
        bounds = ComplicationSlotBounds(
            getTopBounds(minDimen/2f, maxDimen/2f, false).customToRectF(minDimen/2f, maxDimen/2f)
        )

    ).build()

    val bottomComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Bottom.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Bottom.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(),
        bounds = ComplicationSlotBounds(
            getBottomBounds(minDimen/2f, maxDimen/2f, false).customToRectF(minDimen/2f, maxDimen/2f)
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

private fun getLeftBounds(centerX: Float, centerY: Float): Rect {
    val sizeOfComplication = centerX.toInt() / 2
    val horizontalOffset = (centerX.toInt() - sizeOfComplication) / 2
    val verticalOffset = centerY.toInt() - sizeOfComplication / 2

    return Rect(
        horizontalOffset,
        verticalOffset,
        horizontalOffset + sizeOfComplication,
        verticalOffset + sizeOfComplication
    )
}

private fun getRightBounds(centerX: Float, centerY: Float): Rect {
    val sizeOfComplication = centerX.toInt() / 2
    val horizontalOffset = (centerX.toInt() - sizeOfComplication) / 2
    val verticalOffset = centerY.toInt() - sizeOfComplication / 2

    return Rect(
        centerX.toInt() + horizontalOffset,
        verticalOffset,
        centerX.toInt() + horizontalOffset + sizeOfComplication,
        verticalOffset + sizeOfComplication
    )
}

private fun getTopBounds(centerX: Float, centerY: Float, wider: Boolean): Rect {

    val sizeOfComplicationSmall = centerX.toInt() / 2
    val horizontalOffsetSmall = centerX.toInt() - (sizeOfComplicationSmall / 2)
    val verticalOffsetSmall = (centerY.toInt() - sizeOfComplicationSmall) / 2

    return Rect(
        horizontalOffsetSmall,
        centerY.toInt() - verticalOffsetSmall - sizeOfComplicationSmall,
        horizontalOffsetSmall + sizeOfComplicationSmall,
        centerY.toInt() - verticalOffsetSmall
    )
}

private fun getBottomBounds(centerX: Float, centerY: Float, wider: Boolean, bottomInset: Int = 0): Rect {

    val sizeOfComplicationSmall = centerX.toInt() / 2
    val horizontalOffsetSmall = centerX.toInt() - (sizeOfComplicationSmall / 2)
    val verticalOffsetSmall = (centerY.toInt() - sizeOfComplicationSmall) / 2

    return Rect(
        horizontalOffsetSmall,
        centerY.toInt() + verticalOffsetSmall - bottomInset / 4,
        horizontalOffsetSmall + sizeOfComplicationSmall,
        centerY.toInt() + verticalOffsetSmall + sizeOfComplicationSmall - bottomInset / 4
    )
}

private fun Rect.customToRectF(centerX: Float, centerY: Float): RectF {
    return RectF(this.left/(2*centerX), this.top/(2*centerY), this.right/(2*centerX), this.bottom/(2*centerY))
}


