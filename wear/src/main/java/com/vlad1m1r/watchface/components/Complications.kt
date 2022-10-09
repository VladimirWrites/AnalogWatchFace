package com.vlad1m1r.watchface.components

import android.content.Context
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.state.ComplicationsState
import com.vlad1m1r.watchface.utils.getDarkerGrayscale
import com.vlad1m1r.watchface.utils.getLighterGrayscale
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Complications @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun applyComplicationState(complication: ComplicationSlot, complicationsState: ComplicationsState) {
        ComplicationDrawable.getDrawable(context, R.drawable.complication_drawable)!!.apply {
            activeStyle.run {
                textColor = complicationsState.textColor
                titleColor = complicationsState.titleColor
                iconColor = complicationsState.iconColor
                borderColor = complicationsState.borderColor
                rangedValuePrimaryColor = complicationsState.rangedValuePrimaryColor
                rangedValueSecondaryColor = complicationsState.rangedValueSecondaryColor
                backgroundColor = complicationsState.backgroundColor

                ambientStyle.textColor = getLighterGrayscale(complicationsState.textColor)
                ambientStyle.titleColor = getLighterGrayscale(complicationsState.titleColor)
                ambientStyle.iconColor = getLighterGrayscale(complicationsState.iconColor)
                ambientStyle.borderColor = getLighterGrayscale(complicationsState.borderColor)
                ambientStyle.rangedValuePrimaryColor = getLighterGrayscale(complicationsState.rangedValuePrimaryColor)
                ambientStyle.rangedValueSecondaryColor = getLighterGrayscale(complicationsState.rangedValueSecondaryColor)
                ambientStyle.backgroundColor = getDarkerGrayscale(complicationsState.backgroundColor)
            }
            (complication.renderer as CanvasComplicationDrawable).drawable = this
        }
    }
}
