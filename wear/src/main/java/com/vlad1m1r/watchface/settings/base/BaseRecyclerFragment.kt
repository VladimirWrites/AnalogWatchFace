package com.vlad1m1r.watchface.settings.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.wear.widget.SwipeDismissFrameLayout
import androidx.wear.widget.WearableRecyclerView
import com.google.android.wearable.input.RotaryEncoderHelper
import com.vlad1m1r.watchface.R
import kotlin.math.roundToInt

open class BaseRecyclerFragment : Fragment() {

    protected lateinit var wearableRecyclerView: WearableRecyclerView

    private val callback = object : SwipeDismissFrameLayout.Callback() {
        override fun onSwipeStarted(layout: SwipeDismissFrameLayout) {
            // optional
        }

        override fun onSwipeCanceled(layout: SwipeDismissFrameLayout) {
            // optional
        }

        override fun onDismissed(layout: SwipeDismissFrameLayout) {
            requireActivity().onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = SwipeDismissFrameLayout(activity).apply {
            inflater.inflate(
                R.layout.fragment_list,
                this,
                false
            ).also { inflatedView ->
                addView(inflatedView)
            }
            addCallback(callback)
        }

        wearableRecyclerView = view.findViewById(R.id.wearable_recycler_view)
        view.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_SCROLL && RotaryEncoderHelper.isFromRotaryEncoder(
                        event
                    )
                ) {
                    val delta =
                        -RotaryEncoderHelper.getRotaryAxisValue(event) * RotaryEncoderHelper.getScaledScrollFactor(
                            requireContext()
                        )
                    wearableRecyclerView.scrollBy(0, delta.roundToInt())
                    return false
                }
                return false
            }
        })
        return view
    }
}
