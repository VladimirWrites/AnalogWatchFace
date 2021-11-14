package com.vlad1m1r.watchface.settings.hands.centralcircle

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
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val KEY_CENTRAL_CIRCLE_TITLE = "central_circle_title"

@AndroidEntryPoint
class CentralCircleActivity : BaseRecyclerActivity() {

    @Inject
    lateinit var sizeStorage: SizeStorage

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: CentralCircleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val title = intent.getIntExtra(KEY_CENTRAL_CIRCLE_TITLE, 0)

        val centralCircleColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val centralCircleColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setCentralCircleColor(centralCircleColor)
                adapter.notifyDataSetChanged()
            }
        }

        adapter = CentralCircleAdapter(colorStorage, sizeStorage, dataStorage, navigator, title, centralCircleColorLauncher)
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
            @StringRes title: Int
        ): Intent {
            return Intent(context, CentralCircleActivity::class.java)
                .putExtra(KEY_CENTRAL_CIRCLE_TITLE, title)
        }
    }
}