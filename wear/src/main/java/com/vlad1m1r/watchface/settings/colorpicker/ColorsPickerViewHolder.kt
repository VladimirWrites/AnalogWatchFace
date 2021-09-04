package com.vlad1m1r.watchface.settings.colorpicker

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.watchFaceColors
import com.vlad1m1r.watchface.utils.*

class ColorsPickerViewHolder(
    itemView: View,
    private val onColorSelected: OnColorSelected,
    private val preselectedColor: Int
) : RecyclerView.ViewHolder(itemView) {

    private val firstColorView = itemView.findViewById<ImageView>(R.id.first_color)
    private val secondColorView = itemView.findViewById<ImageView>(R.id.second_color)
    private val thirdColorView = itemView.findViewById<ImageView>(R.id.third_color)

    fun setColors(
        @ColorInt firstColor: Int?,
        @ColorInt secondColor: Int,
        @ColorInt thirdColor: Int
    ) {

        if (firstColor == null) {
            firstColorView.visibility = View.INVISIBLE
            firstColorView.setOnClickListener {}
        } else {
            firstColorView.visibility = View.VISIBLE
            firstColorView.setColor(firstColor)
            firstColorView.setOnClickListener { onColorSelected.colorSelected(firstColor) }
            if (firstColor == preselectedColor) {
                firstColorView.selectColor(firstColor)
            } else {
                firstColorView.deselectColor(firstColor)
            }
        }

        secondColorView.setColor(secondColor)
        secondColorView.setOnClickListener { onColorSelected.colorSelected(secondColor) }
        if (secondColor == preselectedColor) {
            secondColorView.selectColor(secondColor)
        } else {
            secondColorView.deselectColor(secondColor)
        }

        thirdColorView.setColor(thirdColor)
        thirdColorView.setOnClickListener { onColorSelected.colorSelected(thirdColor) }
        if (thirdColor == preselectedColor) {
            thirdColorView.selectColor(thirdColor)
        } else {
            thirdColorView.deselectColor(thirdColor)
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
        if (color == watchFaceColors.find { it.id == 0}!!.color) {
            this.setImageResource(R.drawable.remove_color_ripple)
        } else {
            this.setImageResource(R.drawable.round_color_ripple)
            ((this.drawable as RippleDrawable).getDrawable(1) as GradientDrawable).color =
                ColorStateList.valueOf(color)
        }
    }

    private fun getStrokeColor(@ColorInt color: Int): Int {
        val lightShadeOfGray = ContextCompat.getColor(itemView.context, R.color.item_color_picker_light_stroke_color)
        val distance = getDistanceBetweenColors(color, lightShadeOfGray)
        return if(distance > 90) {
            lightShadeOfGray
        } else {
            ContextCompat.getColor(itemView.context, R.color.item_color_picker_dark_stroke_color)
        }
    }
}