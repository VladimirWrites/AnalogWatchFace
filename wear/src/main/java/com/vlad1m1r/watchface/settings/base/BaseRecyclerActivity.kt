package com.vlad1m1r.watchface.settings.base

import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.wear.widget.WearableRecyclerView
import com.google.android.wearable.input.RotaryEncoderHelper
import kotlin.math.roundToInt

open class BaseRecyclerActivity : AppCompatActivity() {

    protected lateinit var wearableRecyclerView: WearableRecyclerView

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_SCROLL && RotaryEncoderHelper.isFromRotaryEncoder(event)) {
            val delta = -RotaryEncoderHelper.getRotaryAxisValue(event) * RotaryEncoderHelper.getScaledScrollFactor(this)
            wearableRecyclerView.scrollBy(0, delta.roundToInt())
            return true
        }
        return super.onGenericMotionEvent(event)
    }
}