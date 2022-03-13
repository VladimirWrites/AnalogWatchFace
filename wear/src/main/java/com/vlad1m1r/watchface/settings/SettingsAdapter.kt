package com.vlad1m1r.watchface.settings

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.base.viewholders.*
import java.lang.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_COMPLICATIONS = 1
private const val TYPE_TICKS = 2
private const val TYPE_BACKGROUND = 3
private const val TYPE_RATE = 4
private const val TYPE_HANDS = 5
private const val TYPE_ABOUT = 6
private const val TYPE_ANTI_ALIAS = 7

class SettingsAdapter(
    private val dataStorage: DataStorage,
    private val navigator: Navigator
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
            TYPE_COMPLICATIONS,
            TYPE_TICKS,
            TYPE_BACKGROUND,
            TYPE_ABOUT,
            TYPE_HANDS -> SettingsViewHolder(parent)

            TYPE_RATE -> RateViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_rate,
                        parent,
                        false
                    ),
                RateApp(parent.context)
            )
            TYPE_ANTI_ALIAS -> SettingsWithSwitchViewHolder(
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
        return 8
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_COMPLICATIONS
            2 -> TYPE_TICKS
            3 -> TYPE_BACKGROUND
            4 -> TYPE_HANDS
            5 -> TYPE_ANTI_ALIAS
            6 -> TYPE_ABOUT
            7 -> TYPE_RATE
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    R.string.wear_settings
                )
            TYPE_COMPLICATIONS -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_complications_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToComplicationsActivity(activity, R.string.wear_complications_settings)
                }
            }

            TYPE_TICKS -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_ticks_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToTicksActivity(activity, R.string.wear_ticks_settings)
                }
            }

            TYPE_BACKGROUND -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_background_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToBackgroundActivity(activity, R.string.wear_background_settings)
                }
            }

            TYPE_HANDS -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_hand_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToHandsActivity(activity, R.string.wear_hand_settings)
                }
            }
            TYPE_ANTI_ALIAS -> {
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_anti_aliasing_in_ambient_mode,
                    dataStorage.useAntiAliasingInAmbientMode()
                ) {
                    dataStorage.setUseAntiAliasingInAmbientMode(it)
                }
            }
            TYPE_ABOUT -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_about_app
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToAboutActivity(activity)
                }
            }
        }
    }
}