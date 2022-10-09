package com.vlad1m1r.watchface

import android.graphics.Canvas
import android.graphics.Rect
import android.view.SurfaceHolder
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.DrawMode
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationStyle
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyle
import com.vlad1m1r.watchface.components.Complications
import com.vlad1m1r.watchface.components.Layouts
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.state.WatchFaceState
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.toWatchFaceState
import kotlinx.coroutines.*
import java.time.ZonedDateTime

// Default for how long each frame is displayed at expected frame rate.
private const val FRAME_PERIOD_MS_DEFAULT: Long = 16L

class AnalogWatchCanvasRenderer(
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    private val complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository,
    canvasType: Int,
    private val layouts: Layouts,
    private val complications: Complications,
) : Renderer.CanvasRenderer2<AnalogWatchCanvasRenderer.AnalogSharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    FRAME_PERIOD_MS_DEFAULT,
    clearWithBackgroundTintBeforeRenderingHighlightLayer = false
) {

    private lateinit var state: WatchFaceState

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
        val watchFaceState = userStyle.toWatchFaceState()
        this.state = watchFaceState

        layouts.setState(watchFaceState)
    }

    private fun drawComplications(canvas: Canvas, zonedDateTime: ZonedDateTime) {
        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complications.applyComplicationState(complication, state.complicationsState)
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
        if(this::state.isInitialized) {
            if (
                layouts.ticks.centerInvalidated
            ) {
                val center = Point(canvas.width / 2f, canvas.height / 2f)
                layouts.setCenter(center)
            }

            layouts.background.draw(canvas)

            if (renderParameters.drawMode != DrawMode.AMBIENT || state.complicationsState.hasInAmbientMode) {
                drawComplications(canvas, zonedDateTime)
            }

            if ((renderParameters.drawMode == DrawMode.AMBIENT && state.ticksState.hasInAmbientMode) ||
                (renderParameters.drawMode != DrawMode.AMBIENT && state.ticksState.hasInInteractiveMode)
            ) {
                layouts.ticks.draw(canvas)
            }

            layouts.hands.draw(canvas, zonedDateTime)
        }
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