package com.vlad1m1r.watchface.settings.complications.picker

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.wearable.complications.ComplicationHelperActivity
import android.support.wearable.complications.ComplicationProviderInfo
import android.support.wearable.complications.ProviderChooserIntent
import android.support.wearable.complications.ProviderInfoRetriever
import android.view.View
import android.widget.ImageView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.WatchFace
import com.vlad1m1r.watchface.components.COMPLICATION_SUPPORTED_TYPES
import com.vlad1m1r.watchface.settings.COMPLICATION_CONFIG_REQUEST_CODE
import java.util.concurrent.Executors

class ComplicationPickerActivity : Activity(), View.OnClickListener {

    private var selectedComplication: ComplicationLocation? = null
    private lateinit var leftComplication: ImageView
    private lateinit var rightComplication: ImageView
    private lateinit var topComplication: ImageView
    private lateinit var bottomComplication: ImageView

    private lateinit var providerInfoRetriever: ProviderInfoRetriever

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_complications)

        leftComplication = findViewById<ImageView>(R.id.left_complication).apply {
            setOnClickListener(this@ComplicationPickerActivity)
        }
        rightComplication = findViewById<ImageView>(R.id.right_complication).apply {
            setOnClickListener(this@ComplicationPickerActivity)
        }
        topComplication = findViewById<ImageView>(R.id.top_complication).apply {
            setOnClickListener(this@ComplicationPickerActivity)
        }
        bottomComplication = findViewById<ImageView>(R.id.bottom_complication).apply {
            setOnClickListener(this@ComplicationPickerActivity)
        }

        providerInfoRetriever = ProviderInfoRetriever(this, Executors.newCachedThreadPool()).apply {
            init()
            retrieveProviderInfo(
                object : ProviderInfoRetriever.OnProviderInfoReceivedCallback() {
                    override fun onProviderInfoReceived(
                        watchFaceComplicationId: Int,
                        complicationProviderInfo: ComplicationProviderInfo?
                    ) {
                        setComplication(complicationProviderInfo, watchFaceComplicationId)
                    }
                },
                ComponentName(this@ComplicationPickerActivity, WatchFace::class.java),
                *COMPLICATION_SUPPORTED_TYPES.keys.toIntArray()
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            leftComplication.id -> launchComplicationHelperActivity(ComplicationLocation.LEFT)
            rightComplication.id -> launchComplicationHelperActivity(ComplicationLocation.RIGHT)
            topComplication.id -> launchComplicationHelperActivity(ComplicationLocation.TOP)
            bottomComplication.id -> launchComplicationHelperActivity(ComplicationLocation.BOTTOM)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                COMPLICATION_CONFIG_REQUEST_CODE -> {
                    val complicationProviderInfo =
                        data?.getParcelableExtra<ComplicationProviderInfo>(ProviderChooserIntent.EXTRA_PROVIDER_INFO)
                    updateComplicationViews(complicationProviderInfo)
                }
            }
        }
    }

    override fun onDestroy() {
        providerInfoRetriever.release()
        super.onDestroy()
    }

    private fun launchComplicationHelperActivity(complicationLocation: ComplicationLocation) {
        selectedComplication = complicationLocation

        startActivityForResult(
            ComplicationHelperActivity.createProviderChooserHelperIntent(
                this,
                ComponentName(this, WatchFace::class.java),
                complicationLocation.id,
                *COMPLICATION_SUPPORTED_TYPES.getValue(complicationLocation.id)
            ),
            COMPLICATION_CONFIG_REQUEST_CODE
        )
    }

    private fun updateComplicationViews(complicationProviderInfo: ComplicationProviderInfo?) {
        if (selectedComplication != null) {
            setComplication(complicationProviderInfo, selectedComplication?.id ?: throw IllegalArgumentException())
        }
    }

    private fun setComplication(complicationProviderInfo: ComplicationProviderInfo?, watchFaceComplicationId: Int) {
        val complicationLocation =
            ComplicationLocation.getFromId(
                watchFaceComplicationId
            )
        val complicationView = complicationLocation.getComplicationView()
        if (complicationProviderInfo != null) {
            complicationView?.apply {
                setImageIcon(complicationProviderInfo.providerIcon)
                contentDescription = getString(R.string.wear_edit_complication, complicationProviderInfo.providerName)
                setBackgroundResource(
                    if (complicationLocation.isBig) R.drawable.added_big_complication else R.drawable.added_complication
                )
                val padding = if (complicationLocation.isBig) {
                    context.resources.getDimensionPixelSize(R.dimen.complication_padding_preview_big)
                } else {
                    context.resources.getDimensionPixelSize(R.dimen.complication_padding_preview_small)
                }

                setPadding(padding, padding, padding, padding)
            }
        } else {
            complicationView?.apply {
                contentDescription = getString(R.string.wear_add_complication)
                setImageResource(android.R.color.transparent)
                setBackgroundResource(if (complicationLocation.isBig) R.drawable.add_big_complication else R.drawable.add_complication)
            }
        }
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
}
