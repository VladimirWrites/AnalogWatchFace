package com.vlad1m1r.watchface.settings.colorpicker

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class ColorsPickerViewHolder(private val itemView: View, private val onColorSelected: OnColorSelected): RecyclerView.ViewHolder(itemView) {

    private val firstColorView = itemView.findViewById<ImageView>(R.id.first_color)
    private val secondColorView = itemView.findViewById<ImageView>(R.id.second_color)
    private val thirdColorView = itemView.findViewById<ImageView>(R.id.third_color)

    fun setColors(@ColorInt firstColor: Int?, @ColorInt secondColor: Int, @ColorInt thirdColor: Int) {

        if(firstColor == null) {
            firstColorView.visibility = View.INVISIBLE
            firstColorView.setOnClickListener {}
        } else {
            firstColorView.visibility = View.VISIBLE
            firstColorView.setColor(firstColor)
            firstColorView.setOnClickListener { onColorSelected.colorSelected(firstColor) }
        }

        secondColorView.setColor(secondColor)
        secondColorView.setOnClickListener { onColorSelected.colorSelected(secondColor) }

        thirdColorView.setColor(thirdColor)
        thirdColorView.setOnClickListener { onColorSelected.colorSelected(thirdColor) }
    }

    private fun ImageView.setColor(@ColorInt color: Int) {
        if(color == Color.TRANSPARENT) {
            this.setImageResource(R.drawable.remove_color_ripple)
        } else {
            this.setImageResource(R.drawable.round_color_ripple)
            ((this.drawable as RippleDrawable).getDrawable(1) as GradientDrawable).color =
                ColorStateList.valueOf(color)
        }
    }
}