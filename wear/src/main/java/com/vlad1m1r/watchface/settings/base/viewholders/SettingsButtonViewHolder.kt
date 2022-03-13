package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.View
import android.widget.Button
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class SettingsButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(@StringRes titleRes: Int, onClickFun: () -> Unit) {
        itemView.findViewById<Button>(R.id.settings_button).apply {
            if (titleRes > 0) {
                setText(titleRes)
            }
            setOnClickListener {
                onClickFun()
            }
        }
    }
}