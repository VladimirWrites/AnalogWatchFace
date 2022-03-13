package com.vlad1m1r.watchface.settings.colorpicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.CustomColorStorage
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import com.vlad1m1r.watchface.settings.colorpicker.customcolor.KEY_NEW_COLOR
import com.vlad1m1r.watchface.settings.confirm.KEY_CHOICE
import com.vlad1m1r.watchface.settings.confirm.KEY_COLOR_TO_DELETE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val KEY_SELECTED_COLOR = "selected_color"

private const val KEY_SHOW_NO_COLOR = "show_no_color"
private const val KEY_ALREADY_SELECTED_COLOR = "already_selected_color"

@AndroidEntryPoint
class ColorPickerActivity : BaseRecyclerActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var customColorStorage: CustomColorStorage

    private lateinit var adapter: ColorPickerAdapter

    private val onColorSelected = object : OnColorAction {
        override fun colorSelected(color: Int) {
            val data = Intent()
            data.putExtra(KEY_SELECTED_COLOR, color)
            setResult(RESULT_OK, data)
            finish()
        }

        override fun colorDeleted(color: Int) {
            navigator.goToConfirmDeleteColor(
                confirmDeleteColorLauncher,
                this@ColorPickerActivity,
                R.string.wear_custom_color_delete,
                color
            )
        }
    }

    private val customColorColorLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                if (result.data!!.hasExtra(KEY_NEW_COLOR)) {
                    val color = result.data!!.getIntExtra(KEY_NEW_COLOR, 0)
                    customColorStorage.addCustomColor(color)
                    adapter.refreshData()
                }
            }
        }

    private val confirmDeleteColorLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                if (result.data!!.hasExtra(KEY_CHOICE)) {
                    val accepted = result.data!!.getBooleanExtra(KEY_CHOICE, false)
                    if(accepted) {
                        val color = result.data!!.getIntExtra(KEY_COLOR_TO_DELETE, 0)
                        customColorStorage.removeCustomColor(color)
                        adapter.refreshData()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val showNoColor = intent.getBooleanExtra(KEY_SHOW_NO_COLOR, true)
        val preselectedColor = intent.getIntExtra(KEY_ALREADY_SELECTED_COLOR, 0)

        adapter = ColorPickerAdapter(
            customColorStorage,
            onColorSelected,
            showNoColor,
            preselectedColor,
            navigator,
            customColorColorLauncher
        )
        wearableRecyclerView =
            findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
                layoutManager = GridLayoutManager(context, 3)
                isEdgeItemsCenteringEnabled = true
                isCircularScrollingGestureEnabled = false
                clipToPadding = false
                setPaddingRelative(
                    resources.getDimensionPixelSize(R.dimen.screen_percentage_05),
                    0,
                    resources.getDimensionPixelSize(R.dimen.screen_percentage_05),
                    0
                )
            }

        wearableRecyclerView.adapter = adapter
        adapter.refreshData()
    }

    companion object {
        fun newInstance(
            context: Context,
            showNoColor: Boolean,
            @ColorInt preselectedColor: Int
        ): Intent {
            return Intent(context, ColorPickerActivity::class.java).apply {
                putExtra(KEY_SHOW_NO_COLOR, showNoColor)
                putExtra(KEY_ALREADY_SELECTED_COLOR, preselectedColor)
            }
        }
    }
}
