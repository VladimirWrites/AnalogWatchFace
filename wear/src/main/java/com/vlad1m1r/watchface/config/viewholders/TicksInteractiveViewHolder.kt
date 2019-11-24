package com.vlad1m1r.watchface.config.viewholders

import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataProvider

class TicksInteractiveViewHolder(itemView: View, private val dataProvider: DataProvider): RecyclerView.ViewHolder(itemView) {
    private val switch = itemView.findViewById<Switch>(R.id.settings_switch).apply {
        isChecked = dataProvider.hasTicksInInteractiveMode()
        setText(R.string.ticks_in_interactive_mode)
        setOnCheckedChangeListener { _, isChecked ->
            dataProvider.setHasTicksInInteractiveMode(isChecked)
        }
    }
}