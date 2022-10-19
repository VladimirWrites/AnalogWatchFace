package com.vlad1m1r.watchface

import android.graphics.Canvas
import android.graphics.Rect
import android.view.SurfaceHolder
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.DrawMode
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyle
import com.vlad1m1r.watchface.components.Layouts
import com.vlad1m1r.watchface.components.complications.BACKGROUND_COMPLICATION_ID
import com.vlad1m1r.watchface.components.complications.Complications
import com.vlad1m1r.watchface.data.state.WatchFaceState
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.utils.toWatchFaceState
import kotlinx.coroutines.*
import java.time.ZonedDateTime

private const val FRAME_PERIOD_MS_SMOOTH: Long = 16L
private const val FRAME_PERIOD_MS_TICKING: Long = 1000L

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
    FRAME_PERIOD_MS_TICKING,
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
        updateRefreshRate()
        layouts.setState(watchFaceState)
    }

    private fun updateRefreshRate() {
        if (state.handsState.hasSmoothSecondsHand) {
            this.interactiveDrawModeUpdateDelayMillis = FRAME_PERIOD_MS_SMOOTH
        } else {
            this.interactiveDrawModeUpdateDelayMillis = FRAME_PERIOD_MS_TICKING
        }
    }

    private fun drawComplications(canvas: Canvas, zonedDateTime: ZonedDateTime) {
        for ((id, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                if (id != BACKGROUND_COMPLICATION_ID) {
                    complications.applyComplicationState(complication, state.complicationsState)
                }

                val complicationType = complication.complicationData.value.type
                if (complicationType != ComplicationType.NO_DATA &&
                    complicationType != ComplicationType.NOT_CONFIGURED &&
                    complicationType != ComplicationType.NO_PERMISSION
                ) {
                    complication.render(canvas, zonedDateTime, renderParameters)
                }
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
        if (this::state.isInitialized) {
            val center = Point(canvas.width / 2f, canvas.height / 2f)

            layouts.background.draw(canvas, renderParameters.drawMode, center)

            if (renderParameters.drawMode != DrawMode.AMBIENT || state.complicationsState.hasInAmbientMode) {
                drawComplications(canvas, zonedDateTime)
            }

            if ((renderParameters.drawMode == DrawMode.AMBIENT && state.ticksState.hasInAmbientMode) ||
                (renderParameters.drawMode != DrawMode.AMBIENT && state.ticksState.hasInInteractiveMode)
            ) {
                layouts.ticks.draw(canvas, renderParameters.drawMode, center)
            }

            layouts.hands.draw(canvas, zonedDateTime, renderParameters.drawMode, center)
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