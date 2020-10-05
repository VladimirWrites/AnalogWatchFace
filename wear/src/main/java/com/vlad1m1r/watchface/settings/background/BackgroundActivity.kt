package com.vlad1m1r.watchface.settings.background

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.wearable.input.RotaryEncoder
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.view.ViewConfigurationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import com.vlad1m1r.watchface.settings.config.*
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE
import kotlin.math.roundToInt

class BackgroundActivity : Activity() {

    private lateinit var colorStorage: ColorStorage
    private lateinit var wearableRecyclerView: WearableRecyclerView
    private lateinit var adapter: BackgroundAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val sharedPref = getSharedPreferences(
            KEY_ANALOG_WATCH_FACE,
            Context.MODE_PRIVATE
        )

        val dataStorage = DataStorage(sharedPref)
        colorStorage = ColorStorage(this.applicationContext, sharedPref)

        adapter = BackgroundAdapter(dataStorage, colorStorage)
        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
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
                BACKGROUND_LEFT_COLOR_PICKER_REQUEST_CODE -> {
                    val backgroundLeftColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setBackgroundLeftColor(backgroundLeftColor)
                }
                BACKGROUND_RIGHT_COLOR_PICKER_REQUEST_CODE -> {
                    val backgroundRightColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setBackgroundRightColor(backgroundRightColor)
                }

            }
            adapter.notifyDataSetChanged()
        }
    }
}