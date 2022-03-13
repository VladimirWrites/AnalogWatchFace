package com.vlad1m1r.watchface.settings.complications.colors

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
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
    private val navigator: Navigator,
    @StringRes private val title: Int,
    private val complicationsTextColorLauncher: ActivityResultLauncher<Intent>,
    private val complicationsTitleColorLauncher: ActivityResultLauncher<Intent>,
    private val complicationsIconColorLauncher: ActivityResultLauncher<Intent>,
    private val complicationsBorderColorLauncher: ActivityResultLauncher<Intent>,
    private val complicationsRangedValuePrimaryColorLauncher: ActivityResultLauncher<Intent>,
    private val complicationsRangedValueSecondaryColorLauncher: ActivityResultLauncher<Intent>,
    private val complicationsBackgroundColorLauncher: ActivityResultLauncher<Intent>
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
            TYPE_COMPLICATIONS_BACKGROUND_COLOR -> ColorPickerViewHolder(parent, navigator)
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
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_complications_text_color,
                    colorStorage.getComplicationsTextColor(),
                    true,
                    complicationsTextColorLauncher
                )
            TYPE_COMPLICATIONS_TITLE_COLOR ->
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_complications_title_color,
                    colorStorage.getComplicationsTitleColor(),
                    true,
                    complicationsTitleColorLauncher
                )
            TYPE_COMPLICATIONS_ICON_COLOR ->
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_complications_icon_color,
                    colorStorage.getComplicationsIconColor(),
                    true,
                    complicationsIconColorLauncher
                )
            TYPE_COMPLICATIONS_BORDER_COLOR ->
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_complications_border_color,
                    colorStorage.getComplicationsBorderColor(),
                    true,
                    complicationsBorderColorLauncher
                )
            TYPE_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR ->
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_complications_ranged_value_primary_color,
                    colorStorage.getComplicationsRangedValuePrimaryColor(),
                    true,
                    complicationsRangedValuePrimaryColorLauncher
                )
            TYPE_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR ->
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_complications_ranged_value_secondary_color,
                    colorStorage.getComplicationsRangedValueSecondaryColor(),
                    true,
                    complicationsRangedValueSecondaryColorLauncher
                )
            TYPE_COMPLICATIONS_BACKGROUND_COLOR ->
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_complications_background_color,
                    colorStorage.getComplicationsBackgroundColor(),
                    true,
                    complicationsBackgroundColorLauncher
                )
        }
    }
}