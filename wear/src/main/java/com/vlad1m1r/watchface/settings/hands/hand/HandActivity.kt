package com.vlad1m1r.watchface.settings.hands.hand

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val KEY_HAND_TYPE = "hand_type"
private const val KEY_HAND_TITLE = "hand_title"

@AndroidEntryPoint
class HandActivity : BaseRecyclerActivity() {

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var sizeStorage: SizeStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: HandAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val handType = intent.getSerializableExtra(KEY_HAND_TYPE) as HandType
        val title = intent.getIntExtra(KEY_HAND_TITLE, 0)

        val hoursHandColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val hoursHandColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setHoursHandColor(hoursHandColor)
                adapter.notifyDataSetChanged()
            }
        }

        val minutesHandColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val minutesHandColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setMinutesHandColor(minutesHandColor)
                adapter.notifyDataSetChanged()
            }
        }

        val secondsHandColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
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
            @StringRes title: Int,
            handType: HandType
        ): Intent {
            return Intent(context, HandActivity::class.java)
                .putExtra(KEY_HAND_TYPE, handType)
                .putExtra(KEY_HAND_TITLE, title)
        }
    }
}