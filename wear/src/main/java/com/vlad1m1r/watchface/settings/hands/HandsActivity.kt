package com.vlad1m1r.watchface.settings.hands

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import dagger.hilt.android.AndroidEntryPoint

const val KEY_HANDS_TITLE = "hands_title"

@AndroidEntryPoint
class HandsActivity : BaseRecyclerActivity() {

    private lateinit var adapter: HandsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val title = intent.getIntExtra(KEY_HANDS_TITLE, 0)

        adapter = HandsAdapter(title)
        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
    }
}