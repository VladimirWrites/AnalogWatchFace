package com.vlad1m1r.watchface.settings.background

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.wearable.complications.ComplicationProviderInfo
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.StringRes
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.watchface.complications.ComplicationDataSourceInfo
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.MainActivity
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.BaseRecyclerFragment
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import com.vlad1m1r.watchface.settings.complications.picker.ComplicationLocation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class BackgroundFragment(@StringRes private val title: Int) : BaseRecyclerFragment() {

    private lateinit var complicationHelperActivityLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: BackgroundAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val leftBackgroundColorLauncher = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val leftBackgroundColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setBackgroundLeftColor(leftBackgroundColor)
                adapter.notifyDataSetChanged()
            }
        }

        val rightBackgroundColorLauncher = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val rightBackgroundColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setBackgroundRightColor(rightBackgroundColor)
                adapter.notifyDataSetChanged()
            }
        }

        adapter = BackgroundAdapter(colorStorage, dataStorage, navigator, title, leftBackgroundColorLauncher, rightBackgroundColorLauncher) {
            launchComplicationHelperActivity()
        }
        wearableRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.Main.immediate) {
            (requireActivity() as MainActivity).stateHolder.editorSession.complicationsDataSourceInfo.collect { result ->
                    setComplication(result[ComplicationLocation.BACKGROUND.id])
            }
        }
    }


    private fun setComplication(complicationDataSourceInfo: ComplicationDataSourceInfo?) {
        if (complicationDataSourceInfo != null) {
            adapter.updateBackgroundComplicationView(
                getString(R.string.wear_edit_background_complication),
                complicationDataSourceInfo.name,
                complicationDataSourceInfo.icon
            )
        } else {
            adapter.updateBackgroundComplicationView(
                getString(R.string.wear_add_background_complication),
                null,
                null
            )
        }
    }

    private fun launchComplicationHelperActivity() {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            val result =
                (requireActivity() as MainActivity).stateHolder.editorSession.openComplicationDataSourceChooser(
                    ComplicationLocation.BACKGROUND.id
                )
            if (result?.complicationSlotId != null) {
                setComplication(result.complicationDataSourceInfo)
            }
        }
    }
}