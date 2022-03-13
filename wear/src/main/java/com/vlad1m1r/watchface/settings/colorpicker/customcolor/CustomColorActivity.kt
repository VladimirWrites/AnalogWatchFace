package com.vlad1m1r.watchface.settings.colorpicker.customcolor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import dagger.hilt.android.AndroidEntryPoint

const val KEY_NEW_COLOR = "new_color"

@AndroidEntryPoint
class CustomColorActivity  : BaseRecyclerActivity() {

    private lateinit var adapter: CustomColorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        adapter = CustomColorAdapter(
            R.string.wear_add_custom_color
        ) { color ->
            val data = Intent()
            data.putExtra(KEY_NEW_COLOR, color)
            setResult(RESULT_OK, data)
            finish()
        }
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
        ): Intent {
            return Intent(context, CustomColorActivity::class.java)
        }
    }
}