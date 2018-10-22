package com.vlad1m1r.watchface

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.wearable.complications.*
import android.view.View
import android.widget.ImageView
import com.vlad1m1r.watchface.utils.*
import java.util.concurrent.Executors

const val COMPLICATION_CONFIG_REQUEST_CODE = 1001

class ComplicationConfigActivity : Activity(), View.OnClickListener {

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

    private lateinit var leftComplication: ImageView
    private lateinit var rightComplication: ImageView

    private lateinit var topComplication: ImageView
    private lateinit var bottomComplication: ImageView

    private var selectedComplication: ComplicationLocation? = null

    private lateinit var providerInfoRetriever: ProviderInfoRetriever

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complication_config)

        leftComplication = findViewById(R.id.left_complication)
        leftComplication.setOnClickListener(this)

        rightComplication = findViewById(R.id.right_complication)
        rightComplication.setOnClickListener(this)

        topComplication = findViewById(R.id.top_complication)
        topComplication.setOnClickListener(this)

        bottomComplication = findViewById(R.id.bottom_complication)
        bottomComplication.setOnClickListener(this)

        providerInfoRetriever = ProviderInfoRetriever(this, Executors.newCachedThreadPool())
        providerInfoRetriever.init()
        providerInfoRetriever.retrieveProviderInfo(
            object : ProviderInfoRetriever.OnProviderInfoReceivedCallback() {
                override fun onProviderInfoReceived(
                    watchFaceComplicationId: Int,
                    complicationProviderInfo: ComplicationProviderInfo?
                ) {
                    setComplication(complicationProviderInfo, watchFaceComplicationId)
                }
            },
            ComponentName(this, WatchFace::class.java),
            *COMPLICATION_SUPPORTED_TYPES.keys.toIntArray()
        )
    }

    override fun onClick(view: View) {
        when (view.id) {
            leftComplication.id -> launchComplicationHelperActivity(ComplicationLocation.LEFT)
            rightComplication.id -> launchComplicationHelperActivity(ComplicationLocation.RIGHT)
            topComplication.id -> launchComplicationHelperActivity(ComplicationLocation.TOP)
            bottomComplication.id -> launchComplicationHelperActivity(ComplicationLocation.BOTTOM)
        }
    }

    override fun onDestroy() {
        providerInfoRetriever.release()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == COMPLICATION_CONFIG_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val complicationProviderInfo =
                data?.getParcelableExtra<ComplicationProviderInfo>(ProviderChooserIntent.EXTRA_PROVIDER_INFO)
            if (selectedComplication != null) {
                setComplication(complicationProviderInfo, selectedComplication!!.id)
            }
        }
    }

    private fun setComplication(complicationProviderInfo: ComplicationProviderInfo?, watchFaceComplicationId: Int) {
        val complicationLocation = ComplicationLocation.getFromId(watchFaceComplicationId)
        val complicationView = complicationLocation.getComplicationView()
        if (complicationProviderInfo != null) {
            complicationView?.apply {
                setImageIcon(complicationProviderInfo.providerIcon)
                contentDescription = getString(R.string.edit_complication, complicationProviderInfo.providerName)
                setBackgroundResource(
                    if (complicationLocation.isBig) R.drawable.added_big_complication else R.drawable.added_complication
                )
            }
        } else {
            complicationView?.apply {
                setImageResource(if (complicationLocation.isBig) R.drawable.add_big_complication else R.drawable.add_complication)
                contentDescription = getString(R.string.add_complication)
                setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    private fun launchComplicationHelperActivity(complicationLocation: ComplicationLocation) {
        selectedComplication = complicationLocation

        startActivityForResult(
            ComplicationHelperActivity.createProviderChooserHelperIntent(
                this,
                ComponentName(this, WatchFace::class.java),
                complicationLocation.id,
                *COMPLICATION_SUPPORTED_TYPES[complicationLocation.id]!!
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
}
