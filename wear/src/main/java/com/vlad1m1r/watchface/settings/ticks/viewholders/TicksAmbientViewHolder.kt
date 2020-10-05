package com.vlad1m1r.watchface.settings.ticks.viewholders

import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage

class TicksAmbientViewHolder(itemView: View, private val dataStorage: DataStorage): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.findViewById<Switch>(R.id.settings_switch).apply {
            isChecked = dataStorage.hasTicksInAmbientMode()
            setText(R.string.ticks_in_ambient_mode)
            setOnCheckedChangeListener { _, isChecked ->
                dataStorage.setHasTicksInAmbientMode(isChecked)
            }
        }
    }
}