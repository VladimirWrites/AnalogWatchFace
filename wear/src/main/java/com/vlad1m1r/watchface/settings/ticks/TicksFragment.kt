package com.vlad1m1r.watchface.settings.ticks

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.data.TicksLayoutType
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.BaseRecyclerFragment
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import com.vlad1m1r.watchface.settings.tickslayoutpicker.KEY_SELECTED_LAYOUT_TYPE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TicksFragment(@StringRes private val title: Int) : BaseRecyclerFragment() {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: TicksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val watchFacePickerLauncher = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val selectedTicksLayoutType = TicksLayoutType.fromId(
                    result.data!!.getIntExtra(KEY_SELECTED_LAYOUT_TYPE, 0)
                )
                val newTicksState = getStateHolder().currentState.ticksState.copy(layoutType = selectedTicksLayoutType)
                getStateHolder().setTicksState(newTicksState)

                adapter.updateWatchFacePicker()
            }
        }

        val hourTickColorLauncher = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val hourTicksColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                val newTicksState = getStateHolder().currentState.ticksState.copy(hourTicksColor = hourTicksColor)
                getStateHolder().setTicksState(newTicksState)

                adapter.notifyDataSetChanged()
            }
        }

        val minuteTickColorLauncher = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val minuteTicksColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                val newTicksState = getStateHolder().currentState.ticksState.copy(minuteTicksColor = minuteTicksColor)
                getStateHolder().setTicksState(newTicksState)

                adapter.notifyDataSetChanged()
            }
        }

        adapter = TicksAdapter(
            resources,
            getStateHolder(),
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