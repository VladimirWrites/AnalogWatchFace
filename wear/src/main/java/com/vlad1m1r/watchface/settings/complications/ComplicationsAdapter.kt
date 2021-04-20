package com.vlad1m1r.watchface.settings.complications

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsWithSwitchViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder
import com.vlad1m1r.watchface.settings.complications.colors.ComplicationColorsActivity
import com.vlad1m1r.watchface.settings.complications.colors.KEY_COMPLICATION_COLORS_TITLE
import com.vlad1m1r.watchface.settings.complications.picker.ComplicationPickerActivity
import kotlin.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG = 1
private const val TYPE_COMPLICATION_COLORS = 2
private const val TYPE_COMPLICATIONS_AMBIENT_MODE = 3
private const val TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS = 4
private const val TYPE_BIGGER_TEXT = 5

class ComplicationsAdapter(
    private val dataStorage: DataStorage,
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
            TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG,
            TYPE_COMPLICATION_COLORS-> SettingsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_text,
                        parent,
                        false
                    )
            )
            TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS,
            TYPE_COMPLICATIONS_AMBIENT_MODE,
            TYPE_BIGGER_TEXT ->
                SettingsWithSwitchViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_switch,
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
        return 6
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG
            2 -> TYPE_COMPLICATION_COLORS
            3 -> TYPE_COMPLICATIONS_AMBIENT_MODE
            4 -> TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS
            5 -> TYPE_BIGGER_TEXT
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
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, ComplicationPickerActivity::class.java)
                    )
                }
            TYPE_COMPLICATION_COLORS ->
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_complication_colors
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, ComplicationColorsActivity::class.java)
                            .putExtra(KEY_COMPLICATION_COLORS_TITLE, R.string.wear_complication_colors)
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
            TYPE_BIGGER_TEXT -> {
                (viewHolder as SettingsWithSwitchViewHolder).apply {
                    bind(
                        R.string.wear_bigger_text_in_complications,
                        dataStorage.hasBiggerComplicationText(),
                    ) {
                        dataStorage.setHasBiggerComplicationText(it)
                    }
                }
            }
        }
    }
}