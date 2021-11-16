package com.vlad1m1r.watchface.settings.complications.picker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.wearable.complications.ComplicationHelperActivity
import android.support.wearable.complications.ComplicationProviderInfo
import android.support.wearable.complications.ProviderChooserIntent
import android.support.wearable.complications.ProviderInfoRetriever
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.WatchFace
import com.vlad1m1r.watchface.components.COMPLICATION_SUPPORTED_TYPES
import com.vlad1m1r.watchface.data.DataStorage
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class ComplicationPickerActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var dataStorage: DataStorage

    private var selectedComplication: ComplicationLocation? = null
    private lateinit var leftComplication: ImageView
    private lateinit var rightComplication: ImageView
    private lateinit var topComplication: ImageView
    private lateinit var bottomComplication: ImageView

    private lateinit var providerInfoRetriever: ProviderInfoRetriever

    private lateinit var complicationHelperActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_complications)

        leftComplication = findViewById<ImageView>(R.id.left_complication).apply {
            setOnClickListener(this@ComplicationPickerActivity)
        }
        rightComplication = findViewById<ImageView>(R.id.right_complication).apply {
            setOnClickListener(this@ComplicationPickerActivity)
        }
        topComplication = findViewById<ImageView>(R.id.top_complication).apply {
            setOnClickListener(this@ComplicationPickerActivity)
        }
        bottomComplication = findViewById<ImageView>(R.id.bottom_complication).apply {
            setOnClickListener(this@ComplicationPickerActivity)
        }

        providerInfoRetriever = ProviderInfoRetriever(this, Executors.newCachedThreadPool()).apply {
            init()
            retrieveProviderInfo(
                object : ProviderInfoRetriever.OnProviderInfoReceivedCallback() {
                    override fun onProviderInfoReceived(
                        watchFaceComplicationId: Int,
                        complicationProviderInfo: ComplicationProviderInfo?
                    ) {
                        setComplication(complicationProviderInfo, watchFaceComplicationId)
                    }
                },
                ComponentName(this@ComplicationPickerActivity, WatchFace::class.java),
                *COMPLICATION_SUPPORTED_TYPES.keys.toIntArray()
            )
        }

        complicationHelperActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val complicationProviderInfo =
                    result.data?.getParcelableExtra<ComplicationProviderInfo>(ProviderChooserIntent.EXTRA_PROVIDER_INFO)
                updateComplicationViews(complicationProviderInfo)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            leftComplication.id -> launchComplicationHelperActivity(ComplicationLocation.LEFT)
            rightComplication.id -> launchComplicationHelperActivity(ComplicationLocation.RIGHT)
            topComplication.id -> launchComplicationHelperActivity(ComplicationLocation.TOP)
            bottomComplication.id -> launchComplicationHelperActivity(ComplicationLocation.BOTTOM)
        }
    }

    override fun onDestroy() {
        providerInfoRetriever.release()
        super.onDestroy()
    }

    private fun launchComplicationHelperActivity(complicationLocation: ComplicationLocation) {
        selectedComplication = complicationLocation

        complicationHelperActivityLauncher.launch(
            ComplicationHelperActivity.createProviderChooserHelperIntent(
                this,
                ComponentName(this, WatchFace::class.java),
                complicationLocation.id,
                *COMPLICATION_SUPPORTED_TYPES.getValue(complicationLocation.id)
            )
        )
    }

    private fun updateComplicationViews(complicationProviderInfo: ComplicationProviderInfo?) {
        if (selectedComplication != null) {
            setComplication(complicationProviderInfo, selectedComplication?.id ?: throw IllegalArgumentException())
        }
    }

    private fun setComplication(complicationProviderInfo: ComplicationProviderInfo?, watchFaceComplicationId: Int) {

        dataStorage.setComplicationProviderName(
            watchFaceComplicationId,
            complicationProviderInfo?.providerName ?: ""
        )

        val complicationLocation =
            ComplicationLocation.getFromId(
                watchFaceComplicationId
            )
        val complicationView = complicationLocation.getComplicationView()
        if (complicationProviderInfo != null) {
            complicationView?.apply {
                setImageIcon(complicationProviderInfo.providerIcon)
                contentDescription = getString(R.string.wear_edit_complication, complicationProviderInfo.providerName)
                setBackgroundResource(
                    if (complicationLocation.isBig) R.drawable.added_big_complication else R.drawable.added_complication
                )
                val padding = if (complicationLocation.isBig) {
                    context.resources.getDimensionPixelSize(R.dimen.complication_padding_preview_big)
                } else {
                    context.resources.getDimensionPixelSize(R.dimen.complication_padding_preview_small)
                }

                setPadding(padding, padding, padding, padding)
            }
        } else {
            complicationView?.apply {
                contentDescription = getString(R.string.wear_add_complication)
                setImageResource(android.R.color.transparent)
                setBackgroundResource(if (complicationLocation.isBig) R.drawable.add_big_complication else R.drawable.add_complication)
            }
        }
    }

    private fun ComplicationLocation?.getComplicationView(): ImageView? {
        return when (this) {
            ComplicationLocation.LEFT -> leftComplication
            ComplicationLocation.RIGHT -> rightComplication
            ComplicationLocation.TOP -> topComplication
            ComplicationLocation.BOTTOM -> bottomComplication
            else -> null
        }
    }

    companion object {
        fun newInstance(
            context: Context
        ): Intent {
            return Intent(context, ComplicationPickerActivity::class.java)
        }
    }
}
