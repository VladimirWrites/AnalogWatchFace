package com.vlad1m1r.watchface

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.wearable.complications.ComplicationData
import android.support.wearable.watchface.CanvasWatchFaceService
import android.support.wearable.watchface.WatchFaceService
import android.support.wearable.watchface.WatchFaceStyle
import android.view.SurfaceHolder
import com.vlad1m1r.watchface.data.DataProvider
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE
import com.vlad1m1r.watchface.utils.*
import com.vlad1m1r.watchface.utils.Point
import java.lang.ref.WeakReference
import java.util.*

private const val INTERACTIVE_UPDATE_RATE_MS = 1000
private const val MESSAGE_UPDATE_ID = 0

class WatchFace : CanvasWatchFaceService() {

    override fun onCreateEngine(): CanvasWatchFaceService.Engine {
        return Engine()
    }

    private class EngineHandler(engine: Engine) : Handler() {
        private val weakReferenceEngine = WeakReference<Engine>(engine)

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

        private lateinit var background: Background
        private lateinit var complications: Complications
        private lateinit var ticks: Ticks
        private lateinit var hands: Hands

        private lateinit var calendar: Calendar

        private val updateTimeHandler = EngineHandler(this)

        private val mode: Mode = Mode()

        private lateinit var dataProvider: DataProvider

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
            dataProvider = DataProvider(sharedPref)
            calendar = Calendar.getInstance()

            background = Background(dataProvider)
            ticks = Ticks(this@WatchFace)
            complications = Complications(this@WatchFace)
            hands = Hands(this@WatchFace)

            setActiveComplications(*COMPLICATION_SUPPORTED_TYPES.keys.toIntArray())

            updateTimeHandler.sendEmptyMessage(MESSAGE_UPDATE_ID)
        }

        override fun onDestroy() {
            updateTimeHandler.removeMessages(MESSAGE_UPDATE_ID)
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
                        val successfulTap = complications[it].onTap(x, y)
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
            canvas.save()
            background.draw(canvas)
            if ((mode.isAmbient && dataProvider.hasTicksInAmbientMode()) ||
                (!mode.isAmbient && dataProvider.hasTicksInInteractiveMode())
            ) {
                ticks.draw(canvas)
            }
            if (!mode.isAmbient || dataProvider.hasComplicationsInAmbientMode()) {
                val center = Point(canvas.width / 2f, canvas.height / 2f)
                complications.setCenter(center)
                complications.draw(canvas, System.currentTimeMillis())
            }
            hands.draw(canvas, calendar)
            canvas.restore()
        }

        override fun onComplicationDataUpdate(watchFaceComplicationId: Int, data: ComplicationData?) {
            super.onComplicationDataUpdate(watchFaceComplicationId, data)
            complications.setComplicationData(watchFaceComplicationId, data)
            invalidate()
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)

            val center = Point(width / 2f, height / 2f)

            background.setCenter(center)
            ticks.setCenter(center)
            complications.setCenter(center)
            hands.setCenter(center)
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
            val delayMs = INTERACTIVE_UPDATE_RATE_MS - timeMs % INTERACTIVE_UPDATE_RATE_MS
            updateTimeHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE_ID, delayMs)
        }

        private fun refreshMode() {
            background.setMode(mode)
            complications.setMode(mode)
            ticks.setMode(mode)
            hands.setMode(mode)
        }
    }
}