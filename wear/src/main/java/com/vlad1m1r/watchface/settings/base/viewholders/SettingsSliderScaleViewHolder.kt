package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class SettingsSliderScaleViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(
            R.layout.item_settings_slider,
            parent,
            false
        )
) {

    private val settingsTitle = itemView.findViewById<TextView>(R.id.settings_title)
    private val settingsSeekBar = itemView.findViewById<SeekBar>(R.id.settings_seekbar)

    fun setData(
        @StringRes titleRes: Int,
        progress: Float,
        precision: Int,
        progressUpdate: (Float) -> Unit
    ) {
        settingsTitle.text = itemView.context.getString(titleRes, progress)
        settingsSeekBar.apply {
            tickMark
            incrementProgressBy(1)
            max = precision
            setProgress(progress.fromRealProgress(precision), false)
            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    settingsTitle.text = itemView.context.getString(titleRes, progress.toRealProgress(precision))
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    progressUpdate(seekBar.progress.toRealProgress(precision))
                }
            })
        }
    }

    private fun Int.toRealProgress(precision: Int) = (this)/(precision).toFloat()
    private fun Float.fromRealProgress(precision: Int) = (this*(precision)).toInt()
}