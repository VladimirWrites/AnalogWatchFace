package com.vlad1m1r.watchface.config.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Switch
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataProvider

class TicksAmbientViewHolder(itemView: View, private val dataProvider: DataProvider): RecyclerView.ViewHolder(itemView) {
    private val switch = itemView.findViewById<Switch>(R.id.settings_switch).apply {
        isChecked = dataProvider.hasTicksInAmbientMode()
        setText(R.string.ticks_in_ambient_mode)
        setOnCheckedChangeListener { _, isChecked ->
            dataProvider.setHasTicksInAmbientMode(isChecked)
        }
    }
}