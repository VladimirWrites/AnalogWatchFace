package com.vlad1m1r.watchface.settings.colorpicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import dagger.hilt.android.AndroidEntryPoint

const val KEY_SELECTED_COLOR = "selected_color"

private const val KEY_SHOW_NO_COLOR = "show_no_color"
private const val KEY_ALREADY_SELECTED_COLOR = "already_selected_color"

@AndroidEntryPoint
class ColorPickerActivity : BaseRecyclerActivity() {

    private lateinit var adapter: ColorPickerAdapter

    private val onColorSelected = object : OnColorSelected {
        override fun colorSelected(color: Int) {
            val data = Intent()
            data.putExtra(KEY_SELECTED_COLOR, color)
            setResult(RESULT_OK, data)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val showNoColor = intent.getBooleanExtra(KEY_SHOW_NO_COLOR, true)
        val preselectedColor = intent.getIntExtra(KEY_ALREADY_SELECTED_COLOR, 0)

        adapter = ColorPickerAdapter(ColorProvider(this), onColorSelected, showNoColor, preselectedColor)
        wearableRecyclerView =
            findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                isEdgeItemsCenteringEnabled = true
                isCircularScrollingGestureEnabled = false
            }

        wearableRecyclerView.adapter = adapter
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
