package com.vlad1m1r.watchface.settings.complications

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val KEY_COMPLICATIONS_TITLE = "complications_title"

@AndroidEntryPoint
class ComplicationsActivity : BaseRecyclerActivity() {

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    private lateinit var adapter: ComplicationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val title = intent.getIntExtra(KEY_COMPLICATIONS_TITLE, 0)

        adapter = ComplicationsAdapter(dataStorage, title)
        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
    }
}
