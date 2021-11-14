package com.vlad1m1r.watchface.settings

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val COMPLICATION_CONFIG_REQUEST_CODE = 1001
const val FACE_PICKER_REQUEST_CODE = 1002

const val HOURS_HAND_COLOR_PICKER_REQUEST_CODE = 1011
const val MINUTES_HAND_COLOR_PICKER_REQUEST_CODE = 1012
const val SECONDS_HAND_COLOR_PICKER_REQUEST_CODE = 1013
const val CENTRAL_CIRCLE_COLOR_PICKER_REQUEST_CODE = 1014

const val BACKGROUND_LEFT_COLOR_PICKER_REQUEST_CODE = 1021
const val BACKGROUND_RIGHT_COLOR_PICKER_REQUEST_CODE = 1022

const val HOUR_TICKS_COLOR_PICKER_REQUEST_CODE = 1031
const val MINUTE_TICKS_COLOR_PICKER_REQUEST_CODE = 1032

const val COMPLICATIONS_TEXT_COLOR_PICKER_REQUEST_CODE = 1041
const val COMPLICATIONS_TITLE_COLOR_COLOR_PICKER_REQUEST_CODE = 1042
const val COMPLICATIONS_ICON_COLOR_PICKER_REQUEST_CODE = 1043
const val COMPLICATIONS_BORDER_COLOR_PICKER_REQUEST_CODE = 1044
const val COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR_PICKER_REQUEST_CODE = 1045
const val COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR_PICKER_REQUEST_CODE = 1046
const val COMPLICATIONS_BACKGROUND_COLOR_PICKER_REQUEST_CODE = 1047

@AndroidEntryPoint
class SettingsActivity : BaseRecyclerActivity() {

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var navigator: Navigator

    internal lateinit var adapter: SettingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        dataStorage.increaseSettingsOpenCount()

        adapter = SettingsAdapter(dataStorage, navigator)
        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter
    }
}
