package com.vlad1m1r.watchface.settings.config.viewholders

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.colorpicker.ColorPickerActivity

class ColorPickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val activity = itemView.context as Activity

    fun setData(
        @StringRes title: Int,
        requestCode: Int,
        @ColorInt currentColor: Int,
        showNoColor: Boolean
    ) {
        val colorDrawable =
            if (currentColor == Color.TRANSPARENT) {
                itemView.context.getDrawable(R.drawable.remove_color_small)
            } else {
                (itemView.context.getDrawable(R.drawable.round_color_small) as GradientDrawable).apply {
                    color = ColorStateList.valueOf(currentColor)
                }
            }

        itemView.findViewById<TextView>(R.id.settings_text).apply {
            setText(title)
            setCompoundDrawablesWithIntrinsicBounds(null, null, colorDrawable, null)
            setOnClickListener {
                val intent = ColorPickerActivity.newInstance(activity, showNoColor, currentColor)
                activity.startActivityForResult(
                    intent,
                    requestCode
                )
            }
        }
    }
}