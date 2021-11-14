package com.vlad1m1r.watchface

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.graphics.Canvas
import android.graphics.Rect
import android.os.*
import android.support.wearable.complications.ComplicationData
import android.support.wearable.watchface.CanvasWatchFaceService
import android.support.wearable.watchface.WatchFaceService
import android.support.wearable.watchface.WatchFaceStyle
import android.view.SurfaceHolder
import com.vlad1m1r.watchface.components.COMPLICATION_SUPPORTED_TYPES
import com.vlad1m1r.watchface.components.Layouts
import com.vlad1m1r.watchface.components.background.BACKGROUND_COMPLICATION_ID
import com.vlad1m1r.watchface.data.*
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import java.lang.ref.WeakReference
import java.util.*
import android.view.WindowInsets
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val INTERACTIVE_UPDATE_RATE_SLOW_MS = 1000
private const val INTERACTIVE_UPDATE_RATE_FAST_MS = 33

private const val MESSAGE_UPDATE_ID = 0

@AndroidEntryPoint
class WatchFace : CanvasWatchFaceService() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var sizeStorage: SizeStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var layouts: Layouts

    override fun onCreateEngine(): CanvasWatchFaceService.Engine {
        return Engine(layouts)
    }

    private class EngineHandler(engine: Engine) : Handler(Looper.getMainLooper()) {
        private val weakReferenceEngine = WeakReference(engine)
        override fun handleMessage(message: Message) {
            val engine = weakReferenceEngine.get()
            if (engine != null) {
                when (message.what) {
                    MESSAGE_UPDATE_ID -> engine.handleUpdateTimeMessage()
                }
            }
        }
    }

    inner class Engine(
        private val layouts: Layouts
    ) : CanvasWatchFaceService.Engine(false) {

        private lateinit var calendar: Calendar

        private val updateTimeHandler = EngineHandler(this)

        private val mode: Mode = Mode()

        private var hasSmoothSecondsHand: Boolean = false

        private val prefsChangeListener =
            OnSharedPreferenceChangeListener { _, key ->
                // only update layouts if the changed preference is the preference
                // to choose the layouts
                if (key == KEY_WATCH_FACE_TYPE || key == KEY_HOUR_TICKS_COLOR ||
                    key == KEY_MINUTE_TICKS_COLOR || key == KEY_SHOULD_ADJUST_TO_SQUARE_SCREEN
                ) {
                    layouts.initTicks()
                } else if (key == KEY_HOURS_HAND_COLOR || key == KEY_MINUTES_HAND_COLOR ||
                    key == KEY_SECONDS_HAND_COLOR || key == KEY_HAS_SECOND_HAND ||
                    key == KEY_HAS_HANDS || key == KEY_CENTRAL_CIRCLE_COLOR ||
                    key == KEY_CIRCLE_WIDTH || key == KEY_CIRCLE_RADIUS ||
                    key == KEY_HAND_HOURS_WIDTH || key == KEY_HAND_MINUTES_WIDTH ||
                    key == KEY_HAND_SECONDS_WIDTH || key == KEY_HAND_HOURS_SCALE ||
                    key == KEY_HAND_MINUTES_SCALE || key == KEY_HAND_SECONDS_SCALE ||
                    key == KEY_USE_ANTI_ALIASING_IN_AMBIENT_MODE || key == KEY_HAS_CENTER_CIRCLE_IN_AMBIENT_MODE
                ) {
                    layouts.invalidateHands()
                } else if (key == KEY_BACKGROUND_LEFT_COLOR || key == KEY_BACKGROUND_RIGHT_COLOR || key == KEY_HAS_BLACK_AMBIENT_BACKGROUND) {
                    layouts.invalidateBackground()
                } else if (key == KEY_COMPLICATIONS_TEXT_COLOR || key == KEY_COMPLICATIONS_TITLE_COLOR ||
                    key == KEY_COMPLICATIONS_ICON_COLOR || key == KEY_COMPLICATIONS_BORDER_COLOR ||
                    key == KEY_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR || key == KEY_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR ||
                    key == KEY_COMPLICATIONS_BACKGROUND_COLOR || key == KEY_HAS_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS ||
                    key == KEY_HAS_BIGGER_COMPLICATION_TEXT
                ) {
                    layouts.invalidateComplications()
                } else if (key == KEY_HAS_SMOOTH_SECONDS_HAND) {
                    hasSmoothSecondsHand = dataStorage.hasSmoothSecondsHand()
                }
            }

        override fun onCreate(holder: SurfaceHolder) {
            super.onCreate(holder)

            setWatchFaceStyle(
                WatchFaceStyle.Builder(this@WatchFace)
                    .setAcceptsTapEvents(true)
                    .build()
            )

            hasSmoothSecondsHand = dataStorage.hasSmoothSecondsHand()

            calendar = Calendar.getInstance()

            val supportedComplications = COMPLICATION_SUPPORTED_TYPES.keys.toMutableList().apply {
                add(BACKGROUND_COMPLICATION_ID)
            }.toIntArray()

            setActiveComplications(*supportedComplications)

            updateTimeHandler.sendEmptyMessage(MESSAGE_UPDATE_ID)

            sharedPreferences.registerOnSharedPreferenceChangeListener(prefsChangeListener)
        }

        override fun onDestroy() {
            updateTimeHandler.removeMessages(MESSAGE_UPDATE_ID)
            val sharedPref = getSharedPreferences(
                KEY_ANALOG_WATCH_FACE,
                Context.MODE_PRIVATE
            )
            sharedPref.unregisterOnSharedPreferenceChangeListener(prefsChangeListener)
            super.onDestroy()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            updateTimer()
        }

        override fun onTimeTick() {
            super.onTimeTick()
            calendar.timeZone = TimeZone.getDefault()
            invalidate()
        }

        override fun onAmbientModeChanged(inAmbientMode: Boolean) {
            super.onAmbientModeChanged(inAmbientMode)

            mode.isAmbient = isInAmbientMode
            layouts.setMode(mode)

            updateTimer()
        }

        override fun onPropertiesChanged(properties: Bundle) {
            super.onPropertiesChanged(properties)

            mode.isLowBitAmbient =
                properties.getBoolean(WatchFaceService.PROPERTY_LOW_BIT_AMBIENT, false)
            mode.isBurnInProtection =
                properties.getBoolean(WatchFaceService.PROPERTY_BURN_IN_PROTECTION, false)
            layouts.setMode(mode)
        }

        override fun onTapCommand(tapType: Int, x: Int, y: Int, eventTime: Long) {
            when (tapType) {
                WatchFaceService.TAP_TYPE_TAP -> {
                    COMPLICATION_SUPPORTED_TYPES.keys.forEach {
                        val successfulTap = layouts.complications[it].onTap(x, y)
                        if (successfulTap) {
                            return
                        }
                    }
                }
            }
            invalidate()
        }

        override fun onDraw(canvas: Canvas, bounds: Rect) {
            calendar.timeInMillis = System.currentTimeMillis()
            if (layouts.complications.centerInvalidated ||
                layouts.backgroundComplication.centerInvalidated ||
                layouts.ticks.centerInvalidated
            ) {
                val center = Point(canvas.width / 2f, canvas.height / 2f)
                layouts.complications.setCenter(center)
                layouts.backgroundComplication.setCenter(center)
                layouts.ticks.setCenter(center)
            }
            canvas.save()
            layouts.background.draw(canvas)
            layouts.backgroundComplication.draw(canvas, System.currentTimeMillis())
            if ((mode.isAmbient && dataStorage.hasTicksInAmbientMode()) ||
                (!mode.isAmbient && dataStorage.hasTicksInInteractiveMode())
            ) {
                layouts.ticks.draw(canvas)
            }
            if (!mode.isAmbient || dataStorage.hasComplicationsInAmbientMode()) {
                layouts.complications.draw(canvas, System.currentTimeMillis())
            }
            layouts.hands.draw(canvas, calendar)
            canvas.restore()
        }

        override fun onComplicationDataUpdate(watchFaceComplicationId: Int, data: ComplicationData) {
            super.onComplicationDataUpdate(watchFaceComplicationId, data)
            if(watchFaceComplicationId == BACKGROUND_COMPLICATION_ID) {
                if(data.largeImage != null) {
                    layouts.backgroundComplication.isVisible = true
                    layouts.backgroundComplication.setComplicationData(data)
                } else {
                    layouts.backgroundComplication.isVisible = false
                }
            } else {
                layouts.complications.setComplicationData(watchFaceComplicationId, data)
            }

            invalidate()
        }

        @Suppress("deprecation")
        override fun onApplyWindowInsets(insets: WindowInsets) {
            super.onApplyWindowInsets(insets)

            val systemWindowBottomInsets = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                insets.getInsets(WindowInsets.Type.systemBars()).bottom
            } else {
                insets.systemWindowInsetBottom
            }

            layouts.setBottomInset(systemWindowBottomInsets)
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)

            val center = Point(width / 2f, height / 2f)

            layouts.setCenter(center)
        }

        private fun updateTimer() {
            updateTimeHandler.removeMessages(MESSAGE_UPDATE_ID)
            if (shouldTimerBeRunning()) {
                updateTimeHandler.sendEmptyMessage(MESSAGE_UPDATE_ID)
            }
        }

        private fun shouldTimerBeRunning(): Boolean {
            return isVisible && !isInAmbientMode
        }

        fun handleUpdateTimeMessage() {
            invalidate()
            val timeMs = System.currentTimeMillis()
            val updateRate = getUpdateRate()
            val delayMs = updateRate - timeMs % updateRate
            updateTimeHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE_ID, delayMs)
        }

        private fun getUpdateRate(): Int {
            return if (hasSmoothSecondsHand) {
                INTERACTIVE_UPDATE_RATE_FAST_MS
            } else {
                INTERACTIVE_UPDATE_RATE_SLOW_MS
            }
        }
    }
}