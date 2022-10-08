package com.vlad1m1r.watchface

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.SurfaceHolder
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.DrawMode
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyle
import com.vlad1m1r.watchface.components.Layouts
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.CustomColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.model.Point
import kotlinx.coroutines.*
import java.time.ZonedDateTime

// Default for how long each frame is displayed at expected frame rate.
private const val FRAME_PERIOD_MS_DEFAULT: Long = 16L

const val WATCH_BACKGROUND_MODIFIED = "WATCH_BACKGROUND_MODIFIED"

const val WATCH_HANDS_MODIFIED = "WATCH_HANDS_MODIFIED"

const val WATCH_COMPLICATION_MODIFIED = "WATCH_COMPLICATION_MODIFIED"

const val WATCH_TICKS_MODIFIED = "WATCH_TICKS_MODIFIED"

class AnalogWatchCanvasRenderer(
    private val context: Context,
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    private val complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository,
    canvasType: Int,
    private val colorStorage: ColorStorage,
    private val dataStorage: DataStorage,
    private val sizeStorage: SizeStorage,
    private val customColorStorage: CustomColorStorage,
    private val layouts: Layouts
) : Renderer.CanvasRenderer2<AnalogWatchCanvasRenderer.AnalogSharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    FRAME_PERIOD_MS_DEFAULT,
    clearWithBackgroundTintBeforeRenderingHighlightLayer = false
) {

    class AnalogSharedAssets : SharedAssets {
        override fun onDestroy() {
        }
    }

    private val scope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        scope.launch {
            currentUserStyleRepository.userStyle.collect { userStyle ->
                updateWatchFaceData(userStyle)
            }
        }
    }

    /*
     * Triggered when the user makes changes to the watch face through the settings activity. The
     * function is called by a flow.
     */
    private fun updateWatchFaceData(userStyle: UserStyle) {

        // Loops through user style and applies new values to watchFaceData.
        for (options in userStyle) {
            when (options.key.id.toString()) {
                WATCH_BACKGROUND_MODIFIED -> {
                    layouts.invalidateBackground()
                    layouts.invalidateBackgroundComplication()
                }
                WATCH_HANDS_MODIFIED -> {
                    layouts.invalidateHands()
                }
                WATCH_COMPLICATION_MODIFIED -> {
                    layouts.invalidateComplications()
                }
                WATCH_TICKS_MODIFIED -> {
                    layouts.initTicks()
                }
            }
        }
    }

    private fun drawComplications(canvas: Canvas, zonedDateTime: ZonedDateTime) {
        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complication.render(canvas, zonedDateTime, renderParameters)
            }
        }
    }

    companion object {
        private const val TAG = "AnalogWatchCanvasRenderer"
    }

    override suspend fun createSharedAssets(): AnalogSharedAssets {
        return AnalogSharedAssets()
    }

    override fun onDestroy() {
        scope.cancel("AnalogWatchCanvasRenderer scope clear() request")
        super.onDestroy()
    }

    override fun render(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: AnalogSharedAssets
    ) {
        if (
            layouts.ticks.centerInvalidated
        ) {
            val center = Point(canvas.width / 2f, canvas.height / 2f)
            layouts.setCenter(center)
        }

        layouts.background.draw(canvas)

        if (renderParameters.drawMode != DrawMode.AMBIENT || dataStorage.hasComplicationsInAmbientMode()) {
            drawComplications(canvas, zonedDateTime)
        }

        if ((renderParameters.drawMode == DrawMode.AMBIENT && dataStorage.hasTicksInAmbientMode()) ||
            (renderParameters.drawMode != DrawMode.AMBIENT && dataStorage.hasTicksInInteractiveMode())
        ) {
            layouts.ticks.draw(canvas)
        }

        layouts.hands.draw(canvas, zonedDateTime)
    }

    override fun renderHighlightLayer(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: AnalogSharedAssets
    ) {
        canvas.drawColor(renderParameters.highlightLayer!!.backgroundTint)

        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complication.renderHighlightLayer(canvas, zonedDateTime, renderParameters)
            }
        }
    }
}