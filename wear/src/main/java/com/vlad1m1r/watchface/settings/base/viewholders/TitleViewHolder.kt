package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class TitleViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(
            R.layout.item_settings_title,
            parent,
            false
        )
) {

    fun bind(@StringRes titleRes: Int) {
        itemView.findViewById<TextView>(R.id.settings_title).apply {
            setText(titleRes)
        }
    }
}