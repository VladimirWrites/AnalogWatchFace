package com.vlad1m1r.watchface.settings.colorpicker.customcolor

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class ColorsPreviewViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(
            R.layout.item_settings_color_preview,
            parent,
            false
        )
) {

    private val colorView = itemView.findViewById<ImageView>(R.id.color)

    fun bind(
        @ColorInt color: Int,
    ) {
        colorView.setImageResource(R.drawable.square_color)
        (colorView.drawable as GradientDrawable).color =
            ColorStateList.valueOf(color)
    }
}