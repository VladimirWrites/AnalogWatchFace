package com.vlad1m1r.watchface.settings.ticks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.FACE_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.HOUR_TICKS_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.MINUTE_TICKS_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val KEY_TICKS_TITLE = "ticks_title"

@AndroidEntryPoint
class TicksActivity : BaseRecyclerActivity() {

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: TicksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val title = intent.getIntExtra(KEY_TICKS_TITLE, 0)

        adapter = TicksAdapter(resources, dataStorage, colorStorage, navigator, title)
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

    companion object {
        fun newInstance(
            context: Context,
            @StringRes title: Int
        ): Intent {
            return Intent(context, TicksActivity::class.java)
                .putExtra(KEY_TICKS_TITLE, title)
        }
    }
}