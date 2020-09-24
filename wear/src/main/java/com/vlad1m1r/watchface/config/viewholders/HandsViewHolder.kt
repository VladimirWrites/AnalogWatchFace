package com.vlad1m1r.watchface.config.viewholders

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.hands.HandsActivity

class HandsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.findViewById<TextView>(R.id.settings_text).apply {
            setText(R.string.hand_settings)
            setOnClickListener {
                val activity = itemView.context as Activity

                activity.startActivity(
                    Intent(itemView.context, HandsActivity::class.java)
                )
            }
        }
    }
}