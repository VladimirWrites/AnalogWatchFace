package com.vlad1m1r.watchface.settings.background

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.wearable.complications.ComplicationHelperActivity
import android.support.wearable.complications.ComplicationProviderInfo
import android.support.wearable.complications.ProviderChooserIntent
import android.support.wearable.complications.ProviderInfoRetriever
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.WatchFace
import com.vlad1m1r.watchface.components.background.BACKGROUND_COMPLICATION_ID
import com.vlad1m1r.watchface.components.background.BACKGROUND_COMPLICATION_SUPPORTED_TYPES
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.BaseRecyclerActivity
import com.vlad1m1r.watchface.settings.colorpicker.KEY_SELECTED_COLOR
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors
import javax.inject.Inject

private const val KEY_BACKGROUND_TITLE = "background_title"

@AndroidEntryPoint
class BackgroundActivity : BaseRecyclerActivity() {

    private lateinit var complicationHelperActivityLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: BackgroundAdapter
    private lateinit var providerInfoRetriever: ProviderInfoRetriever

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val title = intent.getIntExtra(KEY_BACKGROUND_TITLE, 0)

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

        complicationHelperActivityLauncher = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationProviderInfo =
                    result.data?.getParcelableExtra<ComplicationProviderInfo>(ProviderChooserIntent.EXTRA_PROVIDER_INFO)
                updateComplicationViews(complicationProviderInfo)
                adapter.notifyDataSetChanged()
            }
        }

        adapter = BackgroundAdapter(colorStorage, dataStorage, navigator, title, leftBackgroundColorLauncher, rightBackgroundColorLauncher) {
            launchComplicationHelperActivity()
        }
        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.wearable_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = false
        }

        wearableRecyclerView.adapter = adapter

        providerInfoRetriever = ProviderInfoRetriever(this, Executors.newCachedThreadPool()).apply {
            init()
            retrieveProviderInfo(
                object : ProviderInfoRetriever.OnProviderInfoReceivedCallback() {
                    override fun onProviderInfoReceived(
                        watchFaceComplicationId: Int,
                        complicationProviderInfo: ComplicationProviderInfo?
                    ) {
                        setComplication(complicationProviderInfo)
                    }
                },
                ComponentName(this@BackgroundActivity, WatchFace::class.java),
                BACKGROUND_COMPLICATION_ID
            )
        }
    }

    override fun onDestroy() {
        providerInfoRetriever.release()
        super.onDestroy()
    }

    private fun updateComplicationViews(complicationProviderInfo: ComplicationProviderInfo?) {
        setComplication(complicationProviderInfo)
    }

    private fun setComplication(complicationProviderInfo: ComplicationProviderInfo?) {
        if (complicationProviderInfo != null) {
            adapter.updateBackgroundComplicationView(
                getString(R.string.wear_edit_background_complication),
                complicationProviderInfo.providerName,
                complicationProviderInfo.providerIcon
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

        complicationHelperActivityLauncher.launch(
            ComplicationHelperActivity.createProviderChooserHelperIntent(
                this,
                ComponentName(this, WatchFace::class.java),
                BACKGROUND_COMPLICATION_ID,
                *BACKGROUND_COMPLICATION_SUPPORTED_TYPES
            )
        )
    }

    companion object {
        fun newInstance(
            context: Context,
            @StringRes title: Int
        ): Intent {
            return Intent(context, BackgroundActivity::class.java)
                .putExtra(KEY_BACKGROUND_TITLE, title)
        }
    }
}