//package com.vlad1m1r.watchface.components
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Rect
//import android.support.wearable.complications.ComplicationData
//import android.support.wearable.complications.rendering.ComplicationDrawable
//import androidx.appcompat.content.res.AppCompatResources
//import com.vlad1m1r.watchface.R
//import com.vlad1m1r.watchface.data.ColorStorage
//import com.vlad1m1r.watchface.data.DataStorage
//import com.vlad1m1r.watchface.model.Mode
//import com.vlad1m1r.watchface.model.Point
//import com.vlad1m1r.watchface.utils.WatchView
//import com.vlad1m1r.watchface.utils.getDarkerGrayscale
//import com.vlad1m1r.watchface.utils.getLighterGrayscale
//import dagger.hilt.android.qualifiers.ApplicationContext
//import javax.inject.Inject
//
//const val LEFT_COMPLICATION_ID = 100
//const val RIGHT_COMPLICATION_ID = 101
//const val TOP_COMPLICATION_ID = 102
//const val BOTTOM_COMPLICATION_ID = 103
//
//val COMPLICATION_SUPPORTED_TYPES = mapOf(
//    LEFT_COMPLICATION_ID to intArrayOf(
//        ComplicationData.TYPE_RANGED_VALUE,
//        ComplicationData.TYPE_ICON,
//        ComplicationData.TYPE_SHORT_TEXT,
//        ComplicationData.TYPE_SMALL_IMAGE
//    ),
//    RIGHT_COMPLICATION_ID to intArrayOf(
//        ComplicationData.TYPE_RANGED_VALUE,
//        ComplicationData.TYPE_ICON,
//        ComplicationData.TYPE_SHORT_TEXT,
//        ComplicationData.TYPE_SMALL_IMAGE
//    ),
//    TOP_COMPLICATION_ID to intArrayOf(
//        ComplicationData.TYPE_LONG_TEXT,
//        ComplicationData.TYPE_RANGED_VALUE,
//        ComplicationData.TYPE_ICON,
//        ComplicationData.TYPE_SHORT_TEXT,
//        ComplicationData.TYPE_SMALL_IMAGE
//    ),
//    BOTTOM_COMPLICATION_ID to intArrayOf(
//        ComplicationData.TYPE_LONG_TEXT,
//        ComplicationData.TYPE_RANGED_VALUE,
//        ComplicationData.TYPE_ICON,
//        ComplicationData.TYPE_SHORT_TEXT,
//        ComplicationData.TYPE_SMALL_IMAGE
//    )
//)
//
//class Complications @Inject constructor(
//    @ApplicationContext private val context: Context,
//    private val dataStorage: DataStorage,
//    private val colorStorage: ColorStorage
//) : WatchView {
//
//    var bottomInset: Int = 0
//    private val complicationDrawables = mutableMapOf<Int, ComplicationDrawable?>().apply {
//        put(LEFT_COMPLICATION_ID, null)
//        put(RIGHT_COMPLICATION_ID, null)
//        put(TOP_COMPLICATION_ID, null)
//        put(BOTTOM_COMPLICATION_ID, null)
//    }
//    private val complicationData = mutableMapOf<Int, ComplicationData?>()
//    var centerInvalidated = true
//        private set
//
//    fun setMode(mode: Mode) {
//        COMPLICATION_SUPPORTED_TYPES.keys.forEach {
//            complicationDrawables[it]?.apply {
//                setInAmbientMode(mode.isAmbient)
//                setLowBitAmbient(mode.isLowBitAmbient)
//                setBurnInProtection(mode.isBurnInProtection)
//            }
//        }
//        invalidateColors()
//        invalidateTextSize()
//    }
//
//    operator fun get(id: Int): ComplicationDrawable {
//        return complicationDrawables[id]
//            ?: throw IllegalArgumentException("Unsupported ComplicationDrawable id: $id")
//    }
//
//    fun setComplicationData(id: Int, complicationData: ComplicationData?) {
//        this.complicationData[id] = complicationData
//        complicationDrawables[id]?.setComplicationData(complicationData)
//            ?: throw IllegalArgumentException("Unsupported ComplicationDrawable id: $id")
//        centerInvalidated = true
//    }
//
//    fun setComplicationDrawable(drawableResId: Int) {
//        complicationDrawables.keys.forEach { complicationId ->
//            complicationDrawables[complicationId] =
//                (AppCompatResources.getDrawable(
//                    context,
//                    drawableResId
//                ) as ComplicationDrawable).apply {
//                    setContext(context)
//                    complicationData[complicationId]?.let { setComplicationData(it) }
//                }
//        }
//        centerInvalidated = true
//    }
//
//    override fun setCenter(center: Point) {
//        centerInvalidated = false
//        setBoundsToLeftRightComplications(center.x, center.y)
//        setBoundsToTopBottomComplications(
//            center.x,
//            center.y,
//            dataStorage.hasBiggerTopAndBottomComplications()
//        )
//    }
//
//    fun draw(canvas: Canvas, currentTime: Long) {
//        COMPLICATION_SUPPORTED_TYPES.keys.forEach {
//            complicationDrawables[it]?.draw(canvas, currentTime)
//        }
//    }
//
//    private fun setBoundsToLeftRightComplications(centerX: Float, centerY: Float) {
//        val sizeOfComplication = centerX.toInt() / 2
//        val horizontalOffset = (centerX.toInt() - sizeOfComplication) / 2
//        val verticalOffset = centerY.toInt() - sizeOfComplication / 2
//
//        val leftBounds = Rect(
//            horizontalOffset,
//            verticalOffset,
//            horizontalOffset + sizeOfComplication,
//            verticalOffset + sizeOfComplication
//        )
//
//        val rightBounds = Rect(
//            centerX.toInt() + horizontalOffset,
//            verticalOffset,
//            centerX.toInt() + horizontalOffset + sizeOfComplication,
//            verticalOffset + sizeOfComplication
//        )
//
//        complicationDrawables[LEFT_COMPLICATION_ID]?.bounds = leftBounds
//        complicationDrawables[RIGHT_COMPLICATION_ID]?.bounds = rightBounds
//    }
//
//    private fun setBoundsToTopBottomComplications(centerX: Float, centerY: Float, wider: Boolean) {
//
//        val height = if (dataStorage.shouldAdjustToSquareScreen()) {
//            centerY.toInt()
//        } else {
//            centerX.toInt()
//        }
//
//        val sizeOfComplicationSmall = centerX.toInt() / 2
//        val horizontalOffsetSmall = centerX.toInt() - (sizeOfComplicationSmall / 2)
//        val verticalOffsetSmall = (height - sizeOfComplicationSmall) / 2
//
//        val widthOfComplicationLarge = centerX.toInt() + if (wider) centerX.toInt() / 3 else 0
//        val heightOfComplicationLarge = centerX.toInt() / 3
//        val horizontalOffsetLarge = centerX.toInt() - widthOfComplicationLarge / 2
//        val verticalOffsetLarge = (height - heightOfComplicationLarge) / 2
//
//        val topBounds =
//            if (complicationData[TOP_COMPLICATION_ID]?.type == ComplicationData.TYPE_LONG_TEXT) {
//                Rect(
//                    horizontalOffsetLarge,
//                    centerY.toInt() - verticalOffsetLarge - heightOfComplicationLarge,
//                    horizontalOffsetLarge + widthOfComplicationLarge,
//                    centerY.toInt() - verticalOffsetLarge
//                )
//            } else {
//                Rect(
//                    horizontalOffsetSmall,
//                    centerY.toInt() - verticalOffsetSmall - sizeOfComplicationSmall,
//                    horizontalOffsetSmall + sizeOfComplicationSmall,
//                    centerY.toInt() - verticalOffsetSmall
//                )
//            }
//
//        val bottomBounds =
//            if (complicationData[BOTTOM_COMPLICATION_ID]?.type == ComplicationData.TYPE_LONG_TEXT) {
//                Rect(
//                    horizontalOffsetLarge,
//                    centerY.toInt() + verticalOffsetLarge - bottomInset / 4,
//                    horizontalOffsetLarge + widthOfComplicationLarge,
//                    centerY.toInt() + verticalOffsetLarge + heightOfComplicationLarge - bottomInset / 4
//                )
//            } else {
//                Rect(
//                    horizontalOffsetSmall,
//                    centerY.toInt() + verticalOffsetSmall - bottomInset / 4,
//                    horizontalOffsetSmall + sizeOfComplicationSmall,
//                    centerY.toInt() + verticalOffsetSmall + sizeOfComplicationSmall - bottomInset / 4
//                )
//            }
//
//        complicationDrawables[TOP_COMPLICATION_ID]?.bounds = topBounds
//        complicationDrawables[BOTTOM_COMPLICATION_ID]?.bounds = bottomBounds
//    }
//
//    private fun invalidateColors() {
//        val backgroundColor = colorStorage.getComplicationsBackgroundColor()
//        val titleColor = colorStorage.getComplicationsTitleColor()
//        val textColor = colorStorage.getComplicationsTextColor()
//        val borderColor = colorStorage.getComplicationsBorderColor()
//        val iconColor = colorStorage.getComplicationsIconColor()
//        val rangedValuePrimaryColor = colorStorage.getComplicationsRangedValuePrimaryColor()
//        val rangedValueSecondaryColor = colorStorage.getComplicationsRangedValueSecondaryColor()
//
//        COMPLICATION_SUPPORTED_TYPES.keys.forEach {
//            complicationDrawables[it]?.apply {
//                setBackgroundColorActive(backgroundColor)
//                setTitleColorActive(titleColor)
//                setTextColorActive(textColor)
//                setBorderColorActive(borderColor)
//                setIconColorActive(iconColor)
//                setRangedValuePrimaryColorActive(rangedValuePrimaryColor)
//                setRangedValueSecondaryColorActive(rangedValueSecondaryColor)
//                setBackgroundColorAmbient(getDarkerGrayscale(backgroundColor))
//                setTitleColorAmbient(getLighterGrayscale(titleColor))
//                setTextColorAmbient(getLighterGrayscale(textColor))
//                setBorderColorAmbient(getLighterGrayscale(borderColor))
//                setIconColorAmbient(getLighterGrayscale(iconColor))
//                setRangedValuePrimaryColorAmbient(getLighterGrayscale(rangedValuePrimaryColor))
//                setRangedValueSecondaryColorAmbient(getLighterGrayscale(rangedValueSecondaryColor))
//            }
//        }
//    }
//
//    private fun invalidateTextSize() {
//        COMPLICATION_SUPPORTED_TYPES.keys.forEach {
//            complicationDrawables[it]?.apply {
//                val defaultTextSize = context.resources.getDimensionPixelSize(R.dimen.text_size_normal)
//                setTitleSizeActive(defaultTextSize)
//                setTextSizeActive(defaultTextSize)
//                setTitleSizeAmbient(defaultTextSize)
//                setTextSizeAmbient(defaultTextSize)
//            }
//        }
//    }
//
//    fun invalidate() {
//        centerInvalidated = true
//    }
//}