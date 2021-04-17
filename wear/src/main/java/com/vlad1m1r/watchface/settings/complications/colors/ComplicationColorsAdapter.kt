package com.vlad1m1r.watchface.settings.complications.colors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.settings.*
import com.vlad1m1r.watchface.settings.base.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder
import kotlin.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_COMPLICATIONS_TEXT_COLOR = 1
private const val TYPE_COMPLICATIONS_TITLE_COLOR = 2
private const val TYPE_COMPLICATIONS_ICON_COLOR = 3
private const val TYPE_COMPLICATIONS_BORDER_COLOR = 4
private const val TYPE_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR = 5
private const val TYPE_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR = 6
private const val TYPE_COMPLICATIONS_BACKGROUND_COLOR = 7

class ComplicationColorsAdapter(
    private val colorStorage: ColorStorage,
    @StringRes private val title: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_title,
                            parent,
                            false
                        )
                )
            TYPE_COMPLICATIONS_TEXT_COLOR,
            TYPE_COMPLICATIONS_TITLE_COLOR,
            TYPE_COMPLICATIONS_ICON_COLOR,
            TYPE_COMPLICATIONS_BORDER_COLOR,
            TYPE_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR,
            TYPE_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR,
            TYPE_COMPLICATIONS_BACKGROUND_COLOR ->
                ColorPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_text,
                            parent,
                            false
                        )
                )
            else -> {
                throw IllegalArgumentException("viewType: $viewType is not supported")
            }
        }
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_COMPLICATIONS_TEXT_COLOR
            2 -> TYPE_COMPLICATIONS_TITLE_COLOR
            3 -> TYPE_COMPLICATIONS_ICON_COLOR
            4 -> TYPE_COMPLICATIONS_BORDER_COLOR
            5 -> TYPE_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR
            6 -> TYPE_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR
            7 -> TYPE_COMPLICATIONS_BACKGROUND_COLOR
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    title
                )
            TYPE_COMPLICATIONS_TEXT_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_complications_text_color,
                    COMPLICATIONS_TEXT_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsTextColor(),
                    true
                )
            TYPE_COMPLICATIONS_TITLE_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_complications_title_color,
                    COMPLICATIONS_TITLE_COLOR_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsTitleColor(),
                    true
                )
            TYPE_COMPLICATIONS_ICON_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_complications_icon_color,
                    COMPLICATIONS_ICON_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsIconColor(),
                    true
                )
            TYPE_COMPLICATIONS_BORDER_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_complications_border_color,
                    COMPLICATIONS_BORDER_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsBorderColor(),
                    true
                )
            TYPE_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_complications_ranged_value_primary_color,
                    COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsRangedValuePrimaryColor(),
                    true
                )
            TYPE_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_complications_ranged_value_secondary_color,
                    COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsRangedValueSecondaryColor(),
                    true
                )
            TYPE_COMPLICATIONS_BACKGROUND_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_complications_background_color,
                    COMPLICATIONS_BACKGROUND_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsBackgroundColor(),
                    true
                )
        }
    }
}