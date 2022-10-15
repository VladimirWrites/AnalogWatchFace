package com.vlad1m1r.watchface.settings.hands

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.BaseRecyclerFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HandsFragment(@StringRes private val title: Int) : BaseRecyclerFragment() {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: HandsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HandsAdapter(navigator, getStateHolder(), title)
        wearableRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
    }
}