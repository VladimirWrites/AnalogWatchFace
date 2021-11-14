package com.vlad1m1r.watchface.settings.complications.colors

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
import com.vlad1m1r.watchface.settings.*
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val KEY_COMPLICATION_COLORS_TITLE = "complication_colors_title"

@AndroidEntryPoint
class ComplicationColorsActivity : BaseRecyclerActivity() {

    private lateinit var adapter: ComplicationColorsAdapter

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val title = intent.getIntExtra(KEY_COMPLICATION_COLORS_TITLE, 0)

        val complicationsTextColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsTextColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setComplicationsTextColor(complicationsTextColor)
                adapter.notifyDataSetChanged()
            }
        }

        val complicationsTitleColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsTitleColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setComplicationsTitleColor(complicationsTitleColor)
                adapter.notifyDataSetChanged()
            }
        }

        val complicationsIconColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsIconColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setComplicationsIconColor(complicationsIconColor)
                adapter.notifyDataSetChanged()
            }
        }

        val complicationsBorderColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsBorderColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setComplicationsBorderColor(complicationsBorderColor)
                adapter.notifyDataSetChanged()
            }
        }

        val complicationsRangedValuePrimaryColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsRangedValuePrimaryColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setComplicationsRangedValuePrimaryColor(complicationsRangedValuePrimaryColor)
                adapter.notifyDataSetChanged()
            }
        }

        val complicationsRangedValueSecondaryColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsRangedValueSecondaryColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setComplicationsRangedValueSecondaryColor(complicationsRangedValueSecondaryColor)
                adapter.notifyDataSetChanged()
            }
        }

        val complicationsBackgroundColorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationsBackgroundColor = result.data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                colorStorage.setComplicationsBackgroundColor(complicationsBackgroundColor)
                adapter.notifyDataSetChanged()
            }
        }

        adapter = ComplicationColorsAdapter(
            colorStorage,
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
            return Intent(context, ComplicationColorsActivity::class.java)
                .putExtra(KEY_COMPLICATION_COLORS_TITLE, title)
        }
    }
}
