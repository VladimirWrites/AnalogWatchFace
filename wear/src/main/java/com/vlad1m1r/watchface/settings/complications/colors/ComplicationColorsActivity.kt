package com.vlad1m1r.watchface.settings.complications.colors

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import com.vlad1m1r.watchface.settings.config.*

const val KEY_COMPLICATION_COLORS_TITLE = "complication_colors_title"

class ComplicationColorsActivity : BaseRecyclerActivity() {

    private lateinit var adapter: ComplicationColorsAdapter
    private lateinit var colorStorage: ColorStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val title = intent.getIntExtra(KEY_COMPLICATION_COLORS_TITLE, 0)

        val sharedPref = getSharedPreferences(
            KEY_ANALOG_WATCH_FACE,
            Context.MODE_PRIVATE
        )
        colorStorage = ColorStorage(this.applicationContext, sharedPref)

        adapter = ComplicationColorsAdapter(colorStorage, title)
        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                COMPLICATIONS_TEXT_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsTextColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsTextColor(complicationsTextColor)
                }
                COMPLICATIONS_TITLE_COLOR_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsTitleColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsTitleColor(complicationsTitleColor)
                }
                COMPLICATIONS_ICON_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsIconColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsIconColor(complicationsIconColor)
                }
                COMPLICATIONS_BORDER_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsBorderColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsBorderColor(complicationsBorderColor)
                }
                COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsRangedValuePrimaryColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsRangedValuePrimaryColor(complicationsRangedValuePrimaryColor)
                }
                COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsRangedValueSecondaryColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsRangedValueSecondaryColor(complicationsRangedValueSecondaryColor)
                }
                COMPLICATIONS_BACKGROUND_COLOR_PICKER_REQUEST_CODE -> {
                    val complicationsBackgroundColor = data!!.getIntExtra(KEY_SELECTED_COLOR, 0)
                    colorStorage.setComplicationsBackgroundColor(complicationsBackgroundColor)
                }
            }
            adapter.notifyDataSetChanged()
        }
    }
}
