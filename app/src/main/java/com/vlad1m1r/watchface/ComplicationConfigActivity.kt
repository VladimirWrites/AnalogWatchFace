package com.vlad1m1r.watchface

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.wearable.complications.*
import android.view.View
import android.widget.ImageView
import com.vlad1m1r.watchface.utils.COMPLICATION_SUPPORTED_TYPES
import com.vlad1m1r.watchface.utils.LEFT_COMPLICATION_ID
import com.vlad1m1r.watchface.utils.RIGHT_COMPLICATION_ID
import java.util.concurrent.Executors

const val COMPLICATION_CONFIG_REQUEST_CODE = 1001

class ComplicationConfigActivity: Activity(), View.OnClickListener {

    enum class ComplicationLocation(val id: Int) {
        LEFT(LEFT_COMPLICATION_ID),
        RIGHT(RIGHT_COMPLICATION_ID);

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

    private var selectedComplication: ComplicationLocation? = null

    private lateinit var providerInfoRetriever: ProviderInfoRetriever

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complication_config)

        leftComplication = findViewById(R.id.left_complication)
        leftComplication.setOnClickListener(this)

        rightComplication = findViewById(R.id.right_complication)
        rightComplication.setOnClickListener(this)

        providerInfoRetriever = ProviderInfoRetriever(this, Executors.newCachedThreadPool())
        providerInfoRetriever.init()
        providerInfoRetriever.retrieveProviderInfo(
                object: ProviderInfoRetriever.OnProviderInfoReceivedCallback() {
                    override fun onProviderInfoReceived(watchFaceComplicationId: Int, complicationProviderInfo: ComplicationProviderInfo?) {
                        setComplication(complicationProviderInfo, watchFaceComplicationId)
                    }
                },
                ComponentName(this, WatchFace::class.java),
                *COMPLICATION_SUPPORTED_TYPES.keys.toIntArray()
        )
    }

    override fun onClick(view: View) {
        when(view.id) {
            leftComplication.id -> launchComplicationHelperActivity(ComplicationLocation.LEFT)
            rightComplication.id -> launchComplicationHelperActivity(ComplicationLocation.RIGHT)
        }
    }

    override fun onDestroy() {
        providerInfoRetriever.release()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == COMPLICATION_CONFIG_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val complicationProviderInfo =
                    data?.getParcelableExtra<ComplicationProviderInfo>(ProviderChooserIntent.EXTRA_PROVIDER_INFO)
            if(selectedComplication != null) {
                setComplication(complicationProviderInfo, selectedComplication!!.id)
            }
        }
    }

    private fun setComplication(complicationProviderInfo: ComplicationProviderInfo?, watchFaceComplicationId: Int) {
        val complicationLocation = ComplicationLocation.getFromId(watchFaceComplicationId)
        val complicationView = complicationLocation.getComplicationView()
        if(complicationProviderInfo != null) {
            complicationView?.apply {
                setImageIcon(complicationProviderInfo.providerIcon)
                contentDescription = getString(R.string.edit_complication, complicationProviderInfo.providerName)
                setBackgroundResource(R.drawable.added_complication)
            }
        } else {
            complicationView?.apply {
                setImageResource(R.drawable.add_complication)
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
                        *COMPLICATION_SUPPORTED_TYPES[complicationLocation.id]!!),
                COMPLICATION_CONFIG_REQUEST_CODE
        )
    }

    private fun ComplicationLocation?.getComplicationView(): ImageView? {
        return when(this) {
            ComplicationLocation.LEFT -> leftComplication
            ComplicationLocation.RIGHT -> rightComplication
            else -> null
        }
    }
}
