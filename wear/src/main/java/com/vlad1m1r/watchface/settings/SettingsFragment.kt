package com.vlad1m1r.watchface.settings

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.base.BaseRecyclerFragment
import com.vlad1m1r.watchface.settings.hands.hand.WatchFaceStateHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseRecyclerFragment() {

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var navigator: Navigator

    internal lateinit var adapter: SettingsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStorage.increaseSettingsOpenCount()

        adapter = SettingsAdapter(getStateHolder(), navigator)
        wearableRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }
        wearableRecyclerView.adapter = adapter
    }
}
