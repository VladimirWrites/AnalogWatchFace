package com.vlad1m1r.watchface.settings.complications.colors

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.settings.*
import com.vlad1m1r.watchface.settings.base.BaseRecyclerFragment
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ComplicationColorsFragment(@StringRes private val title: Int) : BaseRecyclerFragment() {

    private lateinit var adapter: ComplicationColorsAdapter

    @Inject
    lateinit var navigator: Navigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val complicationsTextColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsTextColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                val newComplicationState = getStateHolder().currentState.complicationsState.copy(textColor = complicationsTextColor)
                getStateHolder().setComplicationsState(newComplicationState)

                adapter.notifyDataSetChanged()
            }
        }

        val complicationsTitleColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsTitleColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                val newComplicationState = getStateHolder().currentState.complicationsState.copy(titleColor = complicationsTitleColor)
                getStateHolder().setComplicationsState(newComplicationState)

                adapter.notifyDataSetChanged()
            }
        }

        val complicationsIconColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsIconColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                val newComplicationState = getStateHolder().currentState.complicationsState.copy(iconColor = complicationsIconColor)
                getStateHolder().setComplicationsState(newComplicationState)

                adapter.notifyDataSetChanged()
            }
        }

        val complicationsBorderColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsBorderColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                val newComplicationState = getStateHolder().currentState.complicationsState.copy(borderColor = complicationsBorderColor)
                getStateHolder().setComplicationsState(newComplicationState)

                adapter.notifyDataSetChanged()
            }
        }

        val complicationsRangedValuePrimaryColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsRangedValuePrimaryColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                val newComplicationState = getStateHolder().currentState.complicationsState.copy(rangedValuePrimaryColor = complicationsRangedValuePrimaryColor)
                getStateHolder().setComplicationsState(newComplicationState)

                adapter.notifyDataSetChanged()
            }
        }

        val complicationsRangedValueSecondaryColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsRangedValueSecondaryColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                val newComplicationState = getStateHolder().currentState.complicationsState.copy(rangedValueSecondaryColor = complicationsRangedValueSecondaryColor)
                getStateHolder().setComplicationsState(newComplicationState)

                adapter.notifyDataSetChanged()
            }
        }

        val complicationsBackgroundColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsBackgroundColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)

                val newComplicationState = getStateHolder().currentState.complicationsState.copy(backgroundColor = complicationsBackgroundColor)
                getStateHolder().setComplicationsState(newComplicationState)

                adapter.notifyDataSetChanged()
            }
        }

        adapter = ComplicationColorsAdapter(
            getStateHolder(),
            navigator,
            title,
            complicationsTextColorLauncher,
            complicationsTitleColorLauncher,
            complicationsIconColorLauncher,
            complicationsBorderColorLauncher,
            complicationsRangedValuePrimaryColorLauncher,
            complicationsRangedValueSecondaryColorLauncher,
            complicationsBackgroundColorLauncher
        )
        wearableRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
    }
}
