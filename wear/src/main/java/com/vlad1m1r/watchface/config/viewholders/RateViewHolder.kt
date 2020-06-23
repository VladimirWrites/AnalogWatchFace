package com.vlad1m1r.watchface.config.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.config.RateApp

class RateViewHolder(itemView: View, private val rateApp: RateApp): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.findViewById<TextView>(R.id.settings_rate).apply {
            setOnClickListener { rateApp.openAppInPlayStore() }
        }
    }
}