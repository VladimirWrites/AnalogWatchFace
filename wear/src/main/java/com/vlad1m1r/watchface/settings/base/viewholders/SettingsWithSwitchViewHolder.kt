package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.View
import android.widget.Switch
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class SettingsWithSwitchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(@StringRes titleRes: Int, checked: Boolean, onCheckChangedFun: (Boolean) -> Unit ) {
        itemView.findViewById<Switch>(R.id.settings_switch).apply {
            setText(titleRes)
            isChecked = checked
            setOnCheckedChangeListener { _, isChecked ->
                onCheckChangedFun(isChecked)
            }
        }
    }
}