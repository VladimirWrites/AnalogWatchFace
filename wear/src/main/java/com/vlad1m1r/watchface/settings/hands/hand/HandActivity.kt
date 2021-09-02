package com.vlad1m1r.watchface.settings.hands.hand

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
import com.vlad1m1r.watchface.settings.HOURS_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.MINUTES_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.SECONDS_HAND_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val KEY_HAND_TYPE = "hand_type"
const val KEY_HAND_TITLE = "hand_title"

@AndroidEntryPoint
class HandActivity : BaseRecyclerActivity() {

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var sizeStorage: SizeStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    private lateinit var adapter: HandAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val handType = intent.getSerializableExtra(KEY_HAND_TYPE) as HandType
        val title = intent.getIntExtra(KEY_HAND_TITLE, 0)

        adapter = HandAdapter(colorStorage, dataStorage, sizeStorage, handType, title)
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
                HOURS_HAND_COLOR_PICKER_REQUEST_CODE -> {
                    val hoursColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setHoursHandColor(hoursColor)
                }
                MINUTES_HAND_COLOR_PICKER_REQUEST_CODE -> {
                    val minutesColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setMinutesHandColor(minutesColor)
                }
                SECONDS_HAND_COLOR_PICKER_REQUEST_CODE -> {
                    val secondsColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setSecondsHandColor(secondsColor)
                }
            }
            adapter.notifyDataSetChanged()
        }
    }
}