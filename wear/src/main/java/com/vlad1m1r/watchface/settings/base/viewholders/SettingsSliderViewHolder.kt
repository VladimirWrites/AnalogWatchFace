package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class SettingsSliderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val settingsTitle = itemView.findViewById<TextView>(R.id.settings_title)
    private val settingsSeekBar = itemView.findViewById<SeekBar>(R.id.settings_seekbar)

    fun setData(
        @StringRes titleRes: Int,
        progress: Int,
        rangeStart: Int,
        rangeEnd: Int,
        progressUpdate: (Int) -> Unit
    ) {
        settingsTitle.text = itemView.context.getString(titleRes, progress)
        settingsSeekBar.apply {
            tickMark
            incrementProgressBy(1)
            max = rangeEnd - rangeStart
            setProgress(progress.fromRealProgress(rangeStart), false)
            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    settingsTitle.text = itemView.context.getString(titleRes, progress.toRealProgress(rangeStart))
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    progressUpdate(seekBar.progress.toRealProgress(rangeStart))
                }
            })
        }
    }

    private fun Int.toRealProgress(shift: Int) = this + shift
    private fun Int.fromRealProgress(shift: Int) = this - shift
}