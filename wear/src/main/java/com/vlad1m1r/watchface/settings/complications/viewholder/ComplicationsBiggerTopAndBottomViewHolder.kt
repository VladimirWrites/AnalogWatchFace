package com.vlad1m1r.watchface.settings.complications.viewholder

import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage

class ComplicationsBiggerTopAndBottomViewHolder(itemView: View, private val dataStorage: DataStorage): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.findViewById<Switch>(R.id.settings_switch).apply {
            isChecked = dataStorage.hasBiggerTopAndBottomComplications()
            setText(R.string.bigger_top_and_bottom_complications)
            setOnCheckedChangeListener { _, isChecked ->
                dataStorage.setHasBiggerTopAndBottomComplications(isChecked)
            }
            setPadding(
                itemView.paddingLeft,
                itemView.paddingTop,
                itemView.paddingRight,
                context.resources.getDimensionPixelSize(R.dimen.bottom_margin)
            )
        }
    }
}