package com.vlad1m1r.watchface.settings.tickslayoutpicker

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicksLayoutPickerActivity : BaseRecyclerActivity() {

    private lateinit var adapter: TickLayoutPickerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val sharedPref = getSharedPreferences(
            KEY_ANALOG_WATCH_FACE,
            Context.MODE_PRIVATE
        )
        val dataProvider = DataStorage(sharedPref)

        adapter = TickLayoutPickerAdapter(dataProvider)
        wearableRecyclerView =
            findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                isEdgeItemsCenteringEnabled = true
                isHorizontalScrollBarEnabled = true
                isVerticalScrollBarEnabled = true
                isVerticalFadingEdgeEnabled = false
                isCircularScrollingGestureEnabled = false
            }

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(wearableRecyclerView)

        wearableRecyclerView.adapter = adapter
    }
}
