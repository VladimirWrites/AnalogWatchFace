package com.vlad1m1r.watchface.config.viewholders

import android.app.Activity
import android.content.ComponentName
import android.support.wearable.complications.ComplicationHelperActivity
import android.support.wearable.complications.ComplicationProviderInfo
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.WatchFace
import com.vlad1m1r.watchface.config.COMPLICATION_CONFIG_REQUEST_CODE
import com.vlad1m1r.watchface.utils.*

class ComplicationsPickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    enum class ComplicationLocation(val id: Int, val isBig: Boolean) {
        LEFT(LEFT_COMPLICATION_ID, false),
        RIGHT(RIGHT_COMPLICATION_ID, false),
        TOP(TOP_COMPLICATION_ID, true),
        BOTTOM(BOTTOM_COMPLICATION_ID, true);

        companion object {
            fun getFromId(id: Int): ComplicationLocation {
                return enumValues<ComplicationLocation>().first {
                    it.id == id
                }
            }
        }
    }

    private val leftComplication = itemView.findViewById<ImageView>(R.id.left_complication).apply {
        setOnClickListener(this@ComplicationsPickerViewHolder)
    }
    private val rightComplication = itemView.findViewById<ImageView>(R.id.right_complication).apply {
        setOnClickListener(this@ComplicationsPickerViewHolder)
    }
    private val topComplication = itemView.findViewById<ImageView>(R.id.top_complication).apply {
        setOnClickListener(this@ComplicationsPickerViewHolder)
    }
    private val bottomComplication = itemView.findViewById<ImageView>(R.id.bottom_complication).apply {
        setOnClickListener(this@ComplicationsPickerViewHolder)
    }

    private var selectedComplication: ComplicationLocation? = null

    override fun onClick(v: View?) {
        when (v?.id) {
            leftComplication.id -> launchComplicationHelperActivity(ComplicationLocation.LEFT)
            rightComplication.id -> launchComplicationHelperActivity(ComplicationLocation.RIGHT)
            topComplication.id -> launchComplicationHelperActivity(ComplicationLocation.TOP)
            bottomComplication.id -> launchComplicationHelperActivity(ComplicationLocation.BOTTOM)
        }
    }

    fun setComplication(complicationProviderInfo: ComplicationProviderInfo?, watchFaceComplicationId: Int) {
        val complicationLocation =
            ComplicationLocation.getFromId(
                watchFaceComplicationId
            )
        val complicationView = complicationLocation.getComplicationView()
        if (complicationProviderInfo != null) {
            complicationView?.apply {
                setImageIcon(complicationProviderInfo.providerIcon)
                contentDescription = itemView.context.getString(R.string.edit_complication, complicationProviderInfo.providerName)
                setBackgroundResource(
                    if (complicationLocation.isBig) R.drawable.added_big_complication else R.drawable.added_complication
                )
            }
        } else {
            complicationView?.apply {
                setImageResource(if (complicationLocation.isBig) R.drawable.add_big_complication else R.drawable.add_complication)
                contentDescription = itemView.context.getString(R.string.add_complication)
                setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    private fun launchComplicationHelperActivity(complicationLocation: ComplicationLocation) {
        selectedComplication = complicationLocation

        val activity = itemView.context as Activity

        activity.startActivityForResult(
            ComplicationHelperActivity.createProviderChooserHelperIntent(
                itemView.context,
                ComponentName(itemView.context, WatchFace::class.java),
                complicationLocation.id,
                *COMPLICATION_SUPPORTED_TYPES.getValue(complicationLocation.id)
            ),
            COMPLICATION_CONFIG_REQUEST_CODE
        )
    }

    private fun ComplicationLocation?.getComplicationView(): ImageView? {
        return when (this) {
            ComplicationLocation.LEFT -> leftComplication
            ComplicationLocation.RIGHT -> rightComplication
            ComplicationLocation.TOP -> topComplication
            ComplicationLocation.BOTTOM -> bottomComplication
            else -> null
        }
    }

    fun updateComplicationViews(complicationProviderInfo: ComplicationProviderInfo?) {
        if (selectedComplication != null) {
            setComplication(complicationProviderInfo, selectedComplication?.id ?: return)
        }
    }
}