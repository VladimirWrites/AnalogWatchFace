package com.vlad1m1r.watchface.settings.ticks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import com.vlad1m1r.watchface.settings.config.*
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity

class TicksActivity : BaseRecyclerActivity() {

    private lateinit var colorStorage: ColorStorage
    private lateinit var adapter: TicksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val sharedPref = getSharedPreferences(
            KEY_ANALOG_WATCH_FACE,
            Context.MODE_PRIVATE
        )
        val dataProvider = DataStorage(sharedPref)

        colorStorage = ColorStorage(this.applicationContext, sharedPref)

        adapter = TicksAdapter(resources, dataProvider, colorStorage)
        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                HOUR_TICKS_COLOR_PICKER_REQUEST_CODE -> {
                    val hourTicksColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setHourTicksColor(hourTicksColor)
                }
                MINUTE_TICKS_COLOR_PICKER_REQUEST_CODE -> {
                    val minuteTicksColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setMinuteTicksColor(minuteTicksColor)
                }
                FACE_PICKER_REQUEST_CODE -> {
                    adapter.updateWatchFacePicker()
                }
            }
            adapter.notifyDataSetChanged()
        }
    }
}