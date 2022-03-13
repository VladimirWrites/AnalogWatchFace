package com.vlad1m1r.watchface.settings.colorpicker

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.utils.getDistanceBetweenColors

class ColorsPickerViewHolder(
    itemView: View,
    private val onColorAction: OnColorAction,
    private val preselectedColor: Int
) : RecyclerView.ViewHolder(itemView) {

    private val colorView = itemView.findViewById<ImageView>(R.id.color)

    fun bind(
        @ColorInt color: Int
    ) {

        colorView.visibility = View.VISIBLE
        colorView.setColor(color)
        colorView.setOnClickListener { onColorAction.colorSelected(color) }
        if (color != Color.parseColor("#00000000")) {
            colorView.setOnLongClickListener {
                onColorAction.colorDeleted(color)
                true
            }
        }
        if (color == preselectedColor) {
            colorView.selectColor(color)
        } else {
            colorView.deselectColor(color)
        }
    }

    private fun ImageView.getGradientDrawable(): GradientDrawable {
        val drawable = (this.drawable as RippleDrawable).getDrawable(1)
        return if (drawable is GradientDrawable) drawable else {
            ((drawable as LayerDrawable).getDrawable(1) as GradientDrawable)
        }
    }

    private fun ImageView.selectColor(@ColorInt color: Int) {
        val strokeColor = getStrokeColor(color)
        val gradientDrawable = this.getGradientDrawable()
        gradientDrawable.setStroke(
            context.resources.getDimensionPixelSize(R.dimen.item_color_picker_selected_stroke_width),
            strokeColor
        )
    }

    private fun ImageView.deselectColor(@ColorInt color: Int) {
        val strokeColor = getStrokeColor(color)
        val gradientDrawable = this.getGradientDrawable()
        gradientDrawable.setStroke(
            context.resources.getDimensionPixelSize(R.dimen.item_color_picker_deselected_stroke_width),
            strokeColor
        )
    }

    private fun ImageView.setColor(@ColorInt color: Int) {
        if (color == Color.parseColor("#00000000")) {
            this.setImageResource(R.drawable.remove_color_ripple)
        } else {
            this.setImageResource(R.drawable.round_color_ripple)
            ((this.drawable as RippleDrawable).getDrawable(1) as GradientDrawable).color =
                ColorStateList.valueOf(color)
        }
    }

    private fun getStrokeColor(@ColorInt color: Int): Int {
        val lightShadeOfGray =
            ContextCompat.getColor(itemView.context, R.color.item_color_picker_light_stroke_color)
        val distance = getDistanceBetweenColors(color, lightShadeOfGray)
        return if (distance > 90) {
            lightShadeOfGray
        } else {
            ContextCompat.getColor(itemView.context, R.color.item_color_picker_dark_stroke_color)
        }
    }
}