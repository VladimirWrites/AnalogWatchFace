package com.vlad1m1r.watchface.settings.ticks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
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

        val watchFacePickerLauncher = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                adapter.updateWatchFacePicker()
            }
        }

        val hourTickColorLauncher = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val hourTicksColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setHourTicksColor(hourTicksColor)
                adapter.notifyDataSetChanged()
            }
        }

        val minuteTickColorLauncher = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val minuteTicksColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setMinuteTicksColor(minuteTicksColor)
                adapter.notifyDataSetChanged()
            }
        }

        adapter = TicksAdapter(
            resources,
            dataStorage,
            colorStorage,
            navigator,
            title,
            watchFacePickerLauncher,
            hourTickColorLauncher,
            minuteTickColorLauncher
        )
        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
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