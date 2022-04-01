package com.vlad1m1r.watchface.settings.hands.centralcircle

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
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.BaseRecyclerFragment
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CentralCircleFragment(@StringRes private val title: Int) : BaseRecyclerFragment() {

    @Inject
    lateinit var sizeStorage: SizeStorage

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: CentralCircleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val centralCircleColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val centralCircleColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setCentralCircleColor(centralCircleColor)
                adapter.notifyDataSetChanged()
            }
        }

        adapter = CentralCircleAdapter(colorStorage, sizeStorage, dataStorage, navigator, title, centralCircleColorLauncher)
        wearableRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
    }
}