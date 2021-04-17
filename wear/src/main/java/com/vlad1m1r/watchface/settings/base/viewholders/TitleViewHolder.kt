package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class TitleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(@StringRes titleRes: Int) {
        itemView.findViewById<TextView>(R.id.settings_title).apply {
            setText(titleRes)
        }
    }
}