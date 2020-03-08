package com.vlad1m1r.watchface.config

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.wearable.complications.ComplicationProviderInfo
import android.support.wearable.complications.ProviderChooserIntent
import android.support.wearable.complications.ProviderInfoRetriever
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.WatchFace
import com.vlad1m1r.watchface.utils.COMPLICATION_SUPPORTED_TYPES
import java.util.concurrent.Executors
import com.vlad1m1r.watchface.data.DataProvider
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE

const val COMPLICATION_CONFIG_REQUEST_CODE = 1001
const val FACE_PICKER_REQUEST_CODE = 1002

class ConfigActivity : Activity() {

    private lateinit var wearableRecyclerView: WearableRecyclerView
    private lateinit var adapter: ConfigAdapter
    private lateinit var providerInfoRetriever: ProviderInfoRetriever

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        val sharedPref = getSharedPreferences(
            KEY_ANALOG_WATCH_FACE,
            Context.MODE_PRIVATE
        )
        val dataProvider = DataProvider(sharedPref)

        adapter = ConfigAdapter(dataProvider)
        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter

        providerInfoRetriever = ProviderInfoRetriever(this, Executors.newCachedThreadPool()).apply {
            init()
            retrieveProviderInfo(
                object : ProviderInfoRetriever.OnProviderInfoReceivedCallback() {
                    override fun onProviderInfoReceived(
                        watchFaceComplicationId: Int,
                        complicationProviderInfo: ComplicationProviderInfo?
                    ) {
                        adapter.setComplication(complicationProviderInfo, watchFaceComplicationId)
                    }
                },
                ComponentName(this@ConfigActivity, WatchFace::class.java),
                *COMPLICATION_SUPPORTED_TYPES.keys.toIntArray()
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == COMPLICATION_CONFIG_REQUEST_CODE && resultCode == RESULT_OK) {

            val complicationProviderInfo =
                data?.getParcelableExtra<ComplicationProviderInfo>(ProviderChooserIntent.EXTRA_PROVIDER_INFO)
            adapter.updateSelectedComplication(complicationProviderInfo)

        }
        if( requestCode == FACE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.updateWatchFacePicker()
        }
    }

    override fun onDestroy() {
        providerInfoRetriever.release()
        super.onDestroy()
    }
}
