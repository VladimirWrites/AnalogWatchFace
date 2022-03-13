package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.RateApp

class RateViewHolder(parent: ViewGroup, private val rateApp: RateApp) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(
            R.layout.item_settings_rate,
            parent,
            false
        )
) {
    init {
        itemView.findViewById<TextView>(R.id.settings_rate).apply {
            setOnClickListener { rateApp.openAppInPlayStore() }
        }
    }
}