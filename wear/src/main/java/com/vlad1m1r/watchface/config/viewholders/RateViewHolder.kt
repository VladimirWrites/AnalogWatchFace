package com.vlad1m1r.watchface.config.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.config.RateApp

class RateViewHolder(itemView: View, private val rateApp: RateApp): RecyclerView.ViewHolder(itemView) {
    private val text = itemView.findViewById<TextView>(R.id.settings_rate).apply {
        setOnClickListener { rateApp.openAppInPlayStore() }
    }
}