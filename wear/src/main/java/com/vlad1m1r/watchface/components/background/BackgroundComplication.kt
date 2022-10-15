package com.vlad1m1r.watchface.components.background

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.support.wearable.complications.ComplicationData
import androidx.appcompat.content.res.AppCompatResources
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.model.Mode
import com.vlad1m1r.watchface.utils.WatchView
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

//const val BACKGROUND_COMPLICATION_ID = 200

//val BACKGROUND_COMPLICATION_SUPPORTED_TYPES = intArrayOf(ComplicationData.TYPE_LARGE_IMAGE)

//class BackgroundComplication @Inject constructor(
//    @ApplicationContext private val context: Context,
//) : WatchView {
//
//    private var complicationDrawable: ComplicationDrawable? = null
//    private var complicationData: ComplicationData? = null
//    var centerInvalidated = true
//        private set
//
//    var isVisible = false
//
//    fun setMode(mode: Mode) {
//        isVisible = !(mode.isAmbient && (mode.isLowBitAmbient || mode.isBurnInProtection))
//        complicationDrawable?.apply {
//            setInAmbientMode(mode.isAmbient)
//            setLowBitAmbient(mode.isLowBitAmbient)
//            setBurnInProtection(mode.isBurnInProtection)
//        }
//    }
//
//    fun setComplicationData(complicationData: ComplicationData?) {
//        this.complicationData = complicationData
//        this.complicationDrawable?.setComplicationData(complicationData)
//            ?: throw IllegalArgumentException("Unsupported ComplicationDrawable")
//        centerInvalidated = true
//    }
//
//    fun setComplicationDrawable(drawableResId: Int) {
//        complicationDrawable = (AppCompatResources.getDrawable(context, drawableResId) as ComplicationDrawable).apply {
//            setContext(context)
//            complicationData?.let { setComplicationData(it) }
//            setBorderColorActive(0)
//            setBorderColorAmbient(0)
//        }
//
//        centerInvalidated = true
//    }
//
//    override fun setCenter(center: Point) {
//        centerInvalidated = false
//        setBoundsToBackgroundComplication(center.x, center.y)
//    }
//
//    fun draw(canvas: Canvas, currentTime: Long) {
//        if (isVisible) {
//            complicationDrawable?.draw(canvas, currentTime)
//        }
//    }
//
//    private fun setBoundsToBackgroundComplication(centerX: Float, centerY: Float) {
//        val widthOfComplication = 2 * centerX.toInt()
//        val heightOfComplication = 2 * centerY.toInt()
//        val bounds = Rect(
//            0,
//            0,
//            widthOfComplication,
//            heightOfComplication
//        )
//        complicationDrawable?.bounds = bounds
//    }
//
//    fun invalidate() {
//        centerInvalidated = true
//    }
//}