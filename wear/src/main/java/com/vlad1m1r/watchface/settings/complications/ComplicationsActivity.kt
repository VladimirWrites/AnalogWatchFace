package com.vlad1m1r.watchface.settings.complications

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.wearable.complications.ComplicationProviderInfo
import android.support.wearable.complications.ProviderChooserIntent
import android.support.wearable.complications.ProviderInfoRetriever
import android.support.wearable.input.RotaryEncoder
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.view.ViewConfigurationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.WatchFace
import com.vlad1m1r.watchface.components.COMPLICATION_SUPPORTED_TYPES
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import com.vlad1m1r.watchface.settings.config.*
import java.util.concurrent.Executors
import kotlin.math.roundToInt

class ComplicationsActivity : Activity() {

    private lateinit var wearableRecyclerView: WearableRecyclerView
    private lateinit var adapter: ComplicationsAdapter
    private lateinit var providerInfoRetriever: ProviderInfoRetriever
    private lateinit var colorStorage: ColorStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val sharedPref = getSharedPreferences(
            KEY_ANALOG_WATCH_FACE,
            Context.MODE_PRIVATE
        )
        val dataProvider = DataStorage(sharedPref)
        colorStorage = ColorStorage(this.applicationContext, sharedPref)

        adapter = ComplicationsAdapter(dataProvider, colorStorage)
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
                ComponentName(this@ComplicationsActivity, WatchFace::class.java),
                *COMPLICATION_SUPPORTED_TYPES.keys.toIntArray()
            )
        }
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_SCROLL && RotaryEncoder.isFromRotaryEncoder(event)) {
            val delta = -RotaryEncoder.getRotaryAxisValue(event) * ViewConfigurationCompat.getScaledVerticalScrollFactor(
                ViewConfiguration.get(this), this
            )
            wearableRecyclerView.scrollBy(0, delta.roundToInt())
            return true
        }
        return super.onGenericMotionEvent(event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when(requestCode) {
                COMPLICATION_CONFIG_REQUEST_CODE -> {
                    val complicationProviderInfo =
                        data?.getParcelableExtra<ComplicationProviderInfo>(ProviderChooserIntent.EXTRA_PROVIDER_INFO)
                    adapter.updateSelectedComplication(complicationProviderInfo)
                }
                COMPLICATIONS_TEXT_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsTextColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsTextColor(complicationsTextColor)
                }
                COMPLICATIONS_TITLE_COLOR_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsTitleColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsTitleColor(complicationsTitleColor)
                }
                COMPLICATIONS_ICON_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsIconColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsIconColor(complicationsIconColor)
                }
                COMPLICATIONS_BORDER_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsBorderColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsBorderColor(complicationsBorderColor)
                }
                COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsRangedValuePrimaryColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsRangedValuePrimaryColor(complicationsRangedValuePrimaryColor)
                }
                COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsRangedValueSecondaryColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsRangedValueSecondaryColor(complicationsRangedValueSecondaryColor)
                }
                COMPLICATIONS_BACKGROUND_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsBackgroundColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsBackgroundColor(complicationsBackgroundColor)
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        providerInfoRetriever.release()
        super.onDestroy()
    }
}
