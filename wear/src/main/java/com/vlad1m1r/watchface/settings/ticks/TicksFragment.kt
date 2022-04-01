package com.vlad1m1r.watchface.settings.ticks

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.BaseRecyclerFragment
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TicksFragment(@StringRes private val title: Int) : BaseRecyclerFragment() {

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: TicksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        wearableRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
    }
}