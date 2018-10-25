package com.vlad1m1r.watchface.config

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Switch
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.utils.DataProvider

class ComplicationsAmbientViewHolder(itemView: View, private val dataProvider: DataProvider): RecyclerView.ViewHolder(itemView) {
    private val switch = itemView.findViewById<Switch>(R.id.switch_complications_ambient).apply {
        isChecked = dataProvider.hasComplicationsInAmbientMode()
        setOnCheckedChangeListener { _, isChecked ->
            dataProvider.setHasComplicationsInAmbientMode(isChecked)
        }
    }
}