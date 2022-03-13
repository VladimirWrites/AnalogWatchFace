package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class SettingsViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(
            R.layout.item_settings_text,
            parent,
            false
        )
) {

    fun bind(@StringRes titleRes: Int, onClickFun: () -> Unit ) {
        itemView.findViewById<TextView>(R.id.settings_text).apply {
            setText(titleRes)
            setOnClickListener {
                onClickFun()
            }
        }
    }
}