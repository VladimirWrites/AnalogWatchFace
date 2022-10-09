package com.vlad1m1r.watchface.settings.tickslayoutpicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.TicksLayoutType
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import dagger.hilt.android.AndroidEntryPoint

private const val KEY_LAYOUT_TYPE = "layout_type"
const val KEY_SELECTED_LAYOUT_TYPE = "selected_layout_type"

@AndroidEntryPoint
class TicksLayoutPickerActivity : BaseRecyclerActivity() {

    private lateinit var adapter: TickLayoutPickerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val ticksLayoutType = TicksLayoutType.fromId(intent.getIntExtra(KEY_LAYOUT_TYPE, 0))

        adapter = TickLayoutPickerAdapter {
            val data = Intent()
            data.putExtra(KEY_SELECTED_LAYOUT_TYPE, it.id)
            setResult(RESULT_OK, data)
            finish()
        }
        wearableRecyclerView =
            findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                isEdgeItemsCenteringEnabled = true
                isHorizontalScrollBarEnabled = true
                isVerticalScrollBarEnabled = true
                isVerticalFadingEdgeEnabled = false
                isCircularScrollingGestureEnabled = false
            }
        wearableRecyclerView.scrollToPosition(ticksLayoutType.id)

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(wearableRecyclerView)

        wearableRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(
            context: Context,
            ticksLayoutType: TicksLayoutType
        ): Intent {
            return Intent(context, TicksLayoutPickerActivity::class.java).apply {
                putExtra(KEY_LAYOUT_TYPE, ticksLayoutType.id)
            }
        }
    }
}
