package com.vlad1m1r.watchface.settings.background

import android.graphics.drawable.Icon
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class SettingsBackgroundComplicationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(title: String?, description: String?, icon: Icon?, onClickFun: () -> Unit ) {
        itemView.setOnClickListener {
            onClickFun()
        }
        itemView.findViewById<TextView>(R.id.settings_text).apply {
            text = title
        }

        itemView.findViewById<ImageView>(R.id.settings_icon).apply {
            setImageIcon(icon)
            visibility = if (description == null) { View.GONE } else { View.VISIBLE }
        }

        itemView.findViewById<TextView>(R.id.settings_description).apply {
            text = description
            visibility = if (description == null) { View.GONE } else { View.VISIBLE }
        }
    }
}