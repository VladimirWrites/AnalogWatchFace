package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class SettingsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(@StringRes titleRes: Int, onClickFun: () -> Unit ) {
        itemView.findViewById<TextView>(R.id.settings_text).apply {
            setText(titleRes)
            setOnClickListener {
                onClickFun()
            }
        }
    }
}