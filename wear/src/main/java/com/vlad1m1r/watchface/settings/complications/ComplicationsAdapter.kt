package com.vlad1m1r.watchface.settings.complications

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsWithSwitchViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder

private const val TYPE_TITLE = 0
private const val TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG = 1
private const val TYPE_COMPLICATION_COLORS = 2
private const val TYPE_COMPLICATIONS_AMBIENT_MODE = 3
private const val TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS = 4

class ComplicationsAdapter(
    private val dataStorage: DataStorage,
    private val navigator: Navigator,
    @StringRes private val title: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(parent)
            TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG,
            TYPE_COMPLICATION_COLORS -> SettingsViewHolder(parent)
            TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS,
            TYPE_COMPLICATIONS_AMBIENT_MODE -> SettingsWithSwitchViewHolder(parent)
            else -> {
                throw IllegalArgumentException("viewType: $viewType is not supported")
            }
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG
            2 -> TYPE_COMPLICATION_COLORS
            3 -> TYPE_COMPLICATIONS_AMBIENT_MODE
            4 -> TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    title
                )
            TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG ->
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_complication_picker
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToComplicationsPickerActivity(activity)
                }
            TYPE_COMPLICATION_COLORS ->
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_complication_colors
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToComplicationColorsActivity(
                        activity,
                        R.string.wear_complication_colors
                    )
                }
            TYPE_COMPLICATIONS_AMBIENT_MODE ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_complications_in_ambient_mode,
                    dataStorage.hasComplicationsInAmbientMode()
                ) {
                    dataStorage.setHasComplicationsInAmbientMode(it)
                }
            TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS -> {
                (viewHolder as SettingsWithSwitchViewHolder).apply {
                    bind(
                        R.string.wear_bigger_top_and_bottom_complications,
                        dataStorage.hasBiggerTopAndBottomComplications(),
                    ) {
                        dataStorage.setHasBiggerTopAndBottomComplications(it)
                    }
                }
            }
        }
    }
}