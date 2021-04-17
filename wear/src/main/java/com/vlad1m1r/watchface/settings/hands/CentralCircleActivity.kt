package com.vlad1m1r.watchface.settings.hands

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import com.vlad1m1r.watchface.settings.config.HOURS_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.MINUTES_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.SECONDS_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import com.vlad1m1r.watchface.settings.config.CENTRAL_CIRCLE_COLOR_PICKER_REQUEST_CODE

const val KEY_CENTRAL_CIRCLE_TITLE = "central_circle_title"

class CentralCircleActivity : BaseRecyclerActivity() {

    private lateinit var colorStorage: ColorStorage
    private lateinit var adapter: CentralCircleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val title = intent.getIntExtra(KEY_CENTRAL_CIRCLE_TITLE, 0)

        val sharedPref = getSharedPreferences(
            KEY_ANALOG_WATCH_FACE,
            Context.MODE_PRIVATE
        )

        colorStorage = ColorStorage(this.applicationContext, sharedPref)

        adapter = CentralCircleAdapter(colorStorage, title)
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
                CENTRAL_CIRCLE_COLOR_PICKER_REQUEST_CODE -> {
                    val centralCircleColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setCentralCircleColor(centralCircleColor)
                }
            }
            adapter.notifyDataSetChanged()
        }
    }
}