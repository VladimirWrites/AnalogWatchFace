package com.vlad1m1r.watchface.settings.hands.hand

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.settings.MainActivity
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.BaseRecyclerFragment
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HandFragment(
    @StringRes private val title: Int,
    private val handType: HandType
) : BaseRecyclerFragment() {

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var sizeStorage: SizeStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: HandAdapter

    private lateinit var watchFaceCurrentSate: WatchFaceStateHolder.WatchFaceCurrentState


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hoursHandColorLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val hoursHandColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setHoursHandColor(hoursHandColor)

                    (requireActivity() as MainActivity).stateHolder.setHandsState(!watchFaceCurrentSate.handsStyle)

                    adapter.notifyDataSetChanged()
                }
            }

        val minutesHandColorLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val minutesHandColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setMinutesHandColor(minutesHandColor)
                    adapter.notifyDataSetChanged()
                }
            }

        val secondsHandColorLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val secondsHandColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setSecondsHandColor(secondsHandColor)
                    adapter.notifyDataSetChanged()
                }
            }

        adapter = HandAdapter(
            colorStorage,
            dataStorage,
            sizeStorage,
            navigator,
            handType,
            title,
            hoursHandColorLauncher,
            minutesHandColorLauncher,
            secondsHandColorLauncher
        )
        wearableRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                isEdgeItemsCenteringEnabled = true
                isCircularScrollingGestureEnabled = false
            }

        wearableRecyclerView.adapter = adapter
    }
}