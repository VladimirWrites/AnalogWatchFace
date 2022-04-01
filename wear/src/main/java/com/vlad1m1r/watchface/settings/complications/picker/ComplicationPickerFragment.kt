package com.vlad1m1r.watchface.settings.complications.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.wear.watchface.complications.ComplicationDataSourceInfo
import androidx.wear.widget.SwipeDismissFrameLayout
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ComplicationPickerFragment : Fragment(), View.OnClickListener {

    private val callback = object : SwipeDismissFrameLayout.Callback() {
        override fun onSwipeStarted(layout: SwipeDismissFrameLayout) {}
        override fun onSwipeCanceled(layout: SwipeDismissFrameLayout) {}
        override fun onDismissed(layout: SwipeDismissFrameLayout) {
            requireActivity().onBackPressed()
        }
    }

    @Inject
    lateinit var dataStorage: DataStorage

    private lateinit var leftComplication: ImageView
    private lateinit var rightComplication: ImageView
    private lateinit var topComplication: ImageView
    private lateinit var bottomComplication: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = SwipeDismissFrameLayout(activity).apply {
            inflater.inflate(
                R.layout.fragment_choose_complications,
                this,
                false
            ).also { inflatedView ->
                addView(inflatedView)
            }
            addCallback(callback)
        }

        leftComplication = view.findViewById<ImageView>(R.id.left_complication).apply {
            setOnClickListener(this@ComplicationPickerFragment)
        }
        rightComplication = view.findViewById<ImageView>(R.id.right_complication).apply {
            setOnClickListener(this@ComplicationPickerFragment)
        }
        topComplication = view.findViewById<ImageView>(R.id.top_complication).apply {
            setOnClickListener(this@ComplicationPickerFragment)
        }
        bottomComplication = view.findViewById<ImageView>(R.id.bottom_complication).apply {
            setOnClickListener(this@ComplicationPickerFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            (requireActivity() as MainActivity).stateHolder.editorSession.complicationsDataSourceInfo.collect { result ->
                result.forEach { (i, complicationDataSourceInfo) ->
                    setComplication(complicationDataSourceInfo, i)
                }
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

    private fun launchComplicationHelperActivity(complicationLocation: ComplicationLocation) {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            val result =
                (requireActivity() as MainActivity).stateHolder.editorSession.openComplicationDataSourceChooser(
                    complicationLocation.id
                )
            if (result?.complicationSlotId != null) {
                setComplication(result.complicationDataSourceInfo,result.complicationSlotId)
            }
        }
    }

    private fun setComplication(
        complicationDataSourceInfo: ComplicationDataSourceInfo?,
        watchFaceComplicationId: Int
    ) {

        dataStorage.setComplicationProviderName(
            watchFaceComplicationId,
            complicationDataSourceInfo?.name ?: ""
        )
        val complicationLocation = ComplicationLocation.getFromId(
            watchFaceComplicationId
        )

        val complicationView = complicationLocation.getComplicationView()
        if (complicationDataSourceInfo != null) {
            complicationView?.apply {
                setImageIcon(complicationDataSourceInfo.icon)
                contentDescription =
                    getString(R.string.wear_edit_complication, complicationDataSourceInfo.name)
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
}
