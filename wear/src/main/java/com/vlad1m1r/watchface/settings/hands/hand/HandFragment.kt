package com.vlad1m1r.watchface.settings.hands.hand

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    lateinit var navigator: Navigator

    private lateinit var adapter: HandAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hoursHandColorLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val hoursHandColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                    val newHoursHandState = getStateHolder().currentState.handsState.hoursHand.copy(color = hoursHandColor)
                    val newHandsState = getStateHolder().currentState.handsState.copy(hoursHand = newHoursHandState)
                    getStateHolder().setHandsState(newHandsState)

                    adapter.notifyDataSetChanged()
                }
            }

        val minutesHandColorLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val minutesHandColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                    val newMinutesHandState = getStateHolder().currentState.handsState.minutesHand.copy(color = minutesHandColor)
                    val newHandsState = getStateHolder().currentState.handsState.copy(minutesHand = newMinutesHandState)
                    getStateHolder().setHandsState(newHandsState)

                    adapter.notifyDataSetChanged()
                }
            }

        val secondsHandColorLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val secondsHandColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                    val newSecondsHandState = getStateHolder().currentState.handsState.secondsHand.copy(color = secondsHandColor)
                    val newHandsState = getStateHolder().currentState.handsState.copy(secondsHand = newSecondsHandState)
                    getStateHolder().setHandsState(newHandsState)

                    adapter.notifyDataSetChanged()
                }
            }

        adapter = HandAdapter(
            getStateHolder(),
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