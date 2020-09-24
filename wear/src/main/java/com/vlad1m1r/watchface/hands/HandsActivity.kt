package com.vlad1m1r.watchface.hands

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.wearable.input.RotaryEncoder
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.view.ViewConfigurationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataProvider
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE
import kotlin.math.roundToInt

class HandsActivity : Activity() {

    private lateinit var wearableRecyclerView: WearableRecyclerView
    private lateinit var adapter: HandsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hands)

        val sharedPref = getSharedPreferences(
            KEY_ANALOG_WATCH_FACE,
            Context.MODE_PRIVATE
        )
        val dataProvider = DataProvider(sharedPref)

        adapter = HandsAdapter(dataProvider)
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
}