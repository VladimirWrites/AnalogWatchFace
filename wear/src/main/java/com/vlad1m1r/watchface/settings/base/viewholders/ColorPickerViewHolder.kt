package com.vlad1m1r.watchface.settings.base.viewholders

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.Navigator

class ColorPickerViewHolder(
    itemView: View,
    private val navigator: Navigator
) : RecyclerView.ViewHolder(itemView) {

    fun bind(@StringRes title: Int, @ColorInt currentColor: Int, showNoColor: Boolean, activityResultLauncher: ActivityResultLauncher<Intent>) {
        val colorDrawable =
            if (currentColor == Color.TRANSPARENT) {
                AppCompatResources.getDrawable(itemView.context, R.drawable.remove_color_small)
            } else {
                (AppCompatResources.getDrawable(itemView.context, R.drawable.round_color_small) as GradientDrawable).apply {
                    color = ColorStateList.valueOf(currentColor)
                }
            }

        itemView.findViewById<TextView>(R.id.settings_text).apply {
            setText(title)
            setCompoundDrawablesWithIntrinsicBounds(null, null, colorDrawable, null)
            setOnClickListener {
                navigator.goToColorPickerActivityForResult(
                    activityResultLauncher,
                    itemView.context,
                    showNoColor,
                    currentColor
                )
            }
        }
    }
}