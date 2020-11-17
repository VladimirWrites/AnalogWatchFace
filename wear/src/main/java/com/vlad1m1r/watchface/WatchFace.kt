package com.vlad1m1r.watchface

import android.content.Context
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.wearable.complications.ComplicationData
import android.support.wearable.watchface.CanvasWatchFaceService
import android.support.wearable.watchface.WatchFaceService
import android.support.wearable.watchface.WatchFaceStyle
import android.view.SurfaceHolder
import com.vlad1m1r.watchface.components.COMPLICATION_SUPPORTED_TYPES
import com.vlad1m1r.watchface.components.Layouts
import com.vlad1m1r.watchface.data.*
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.model.Point
import java.lang.ref.WeakReference
import java.util.*

private const val INTERACTIVE_UPDATE_RATE_SLOW_MS = 1000
private const val INTERACTIVE_UPDATE_RATE_FAST_MS = 33


private const val MESSAGE_UPDATE_ID = 0

class WatchFace : CanvasWatchFaceService() {

    override fun onCreateEngine(): CanvasWatchFaceService.Engine {
        return Engine()
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

    inner class Engine : CanvasWatchFaceService.Engine() {

        private lateinit var layouts: Layouts

        private lateinit var calendar: Calendar

        private val updateTimeHandler = EngineHandler(this)

        private val mode: Mode = Mode()

        private lateinit var dataStorage: DataStorage

        private lateinit var colorStorage: ColorStorage

        private var hasSmoothSecondsHand: Boolean = false

        private val prefsChangeListener =
            OnSharedPreferenceChangeListener { _, key ->
                // only update layouts if the changed preference is the preference
                // to choose the layouts
                if (key == KEY_WATCH_FACE_TYPE || key == KEY_HOUR_TICKS_COLOR || key == KEY_MINUTE_TICKS_COLOR) {
                    layouts.initTicks()
                }
                else if (key == KEY_HOURS_HAND_COLOR || key == KEY_MINUTES_HAND_COLOR ||
                    key == KEY_SECONDS_HAND_COLOR || key == KEY_HAS_SECOND_HAND ||
                    key == KEY_HAS_HANDS || key == KEY_CENTRAL_CIRCLE_COLOR
                ) {
                    layouts.invalidateHands()
                }
                else if (key == KEY_BACKGROUND_LEFT_COLOR || key == KEY_BACKGROUND_RIGHT_COLOR) {
                    layouts.invalidateBackground()
                }
                else if (key == KEY_COMPLICATIONS_TEXT_COLOR || key == KEY_COMPLICATIONS_TITLE_COLOR ||
                    key == KEY_COMPLICATIONS_ICON_COLOR || key == KEY_COMPLICATIONS_BORDER_COLOR ||
                    key == KEY_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR || key == KEY_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR ||
                    key == KEY_COMPLICATIONS_BACKGROUND_COLOR || key == KEY_HAS_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS
                ) {
                    layouts.invalidateComplications()
                }
                else if(key == KEY_HAS_SMOOTH_SECONDS_HAND) {
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
            val sharedPref = getSharedPreferences(
                KEY_ANALOG_WATCH_FACE,
                Context.MODE_PRIVATE
            )
            dataStorage = DataStorage(sharedPref)
            hasSmoothSecondsHand = dataStorage.hasSmoothSecondsHand()

            colorStorage = ColorStorage(this@WatchFace.applicationContext, sharedPref)

            calendar = Calendar.getInstance()
            layouts = Layouts(dataStorage, colorStorage, this@WatchFace)

            setActiveComplications(*COMPLICATION_SUPPORTED_TYPES.keys.toIntArray())

            updateTimeHandler.sendEmptyMessage(MESSAGE_UPDATE_ID)

            sharedPref.registerOnSharedPreferenceChangeListener(prefsChangeListener)
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
            refreshMode()

            updateTimer()
        }

        override fun onPropertiesChanged(properties: Bundle) {
            super.onPropertiesChanged(properties)

            mode.isLowBitAmbient =
                properties.getBoolean(WatchFaceService.PROPERTY_LOW_BIT_AMBIENT, false)
            mode.isBurnInProtection =
                properties.getBoolean(WatchFaceService.PROPERTY_BURN_IN_PROTECTION, false)

            refreshMode()
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
            if (layouts.complications.centerInvalidated || layouts.ticks.centerInvalidated) {
                val center = Point(canvas.width / 2f, canvas.height / 2f)
                layouts.complications.setCenter(center)
                layouts.ticks.setCenter(center)
            }
            canvas.save()
            layouts.background.draw(canvas)
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

        override fun onComplicationDataUpdate(watchFaceComplicationId: Int, data: ComplicationData?) {
            super.onComplicationDataUpdate(watchFaceComplicationId, data)
            layouts.complications.setComplicationData(watchFaceComplicationId, data)
            invalidate()
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)

            val center = Point(width / 2f, height / 2f)

            layouts.background.setCenter(center)
            layouts.ticks.setCenter(center)
            layouts.complications.setCenter(center)
            layouts.hands.setCenter(center)
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

        private fun refreshMode() {
            layouts.background.setMode(mode)
            layouts.complications.setMode(mode)
            layouts.ticks.setMode(mode)
            layouts.hands.setMode(mode)
        }
    }
}