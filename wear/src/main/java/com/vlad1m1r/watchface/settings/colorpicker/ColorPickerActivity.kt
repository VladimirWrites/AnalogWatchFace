package com.vlad1m1r.watchface.settings.colorpicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.wearable.input.RotaryEncoder
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.view.ViewConfigurationCompat.getScaledVerticalScrollFactor
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import kotlin.math.roundToInt

const val KEY_SELECTED_COLOR = "selected_color"

private const val KEY_SHOW_NO_COLOR = "show_no_color"

class ColorPickerActivity : Activity() {

    private lateinit var wearableRecyclerView: WearableRecyclerView
    private lateinit var adapter: ColorPickerAdapter

    private val onColorSelected = object: OnColorSelected {
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

        adapter = ColorPickerAdapter(ColorProvider(this), onColorSelected, showNoColor)
        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_SCROLL && RotaryEncoder.isFromRotaryEncoder(event)) {
            val delta = -RotaryEncoder.getRotaryAxisValue(event) * getScaledVerticalScrollFactor(
                ViewConfiguration.get(this), this
            )
            wearableRecyclerView.scrollBy(0, delta.roundToInt())
            return true
        }
        return super.onGenericMotionEvent(event)
    }

    companion object {
        fun newInstance(context: Context, showNoColor: Boolean): Intent {
            return Intent(context, ColorPickerActivity::class.java).apply {
                putExtra(KEY_SHOW_NO_COLOR, showNoColor)
            }
        }
    }
}
