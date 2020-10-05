package com.vlad1m1r.watchface.settings.ticks.viewholders

import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage

class TicksInteractiveViewHolder(itemView: View, private val dataStorage: DataStorage) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.findViewById<Switch>(R.id.settings_switch).apply {
            isChecked = dataStorage.hasTicksInInteractiveMode()
            setText(R.string.ticks_in_interactive_mode)
            setOnCheckedChangeListener { _, isChecked ->
                dataStorage.setHasTicksInInteractiveMode(isChecked)
            }
        }
    }
}