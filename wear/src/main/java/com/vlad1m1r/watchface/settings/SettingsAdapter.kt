package com.vlad1m1r.watchface.settings

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.base.viewholders.*
import com.vlad1m1r.watchface.settings.about.AboutActivity
import com.vlad1m1r.watchface.settings.background.BackgroundActivity
import com.vlad1m1r.watchface.settings.background.KEY_BACKGROUND_TITLE
import com.vlad1m1r.watchface.settings.complications.ComplicationsActivity
import com.vlad1m1r.watchface.settings.complications.KEY_COMPLICATIONS_TITLE
import com.vlad1m1r.watchface.settings.hands.HandsActivity
import com.vlad1m1r.watchface.settings.hands.KEY_HANDS_TITLE
import com.vlad1m1r.watchface.settings.ticks.KEY_TICKS_TITLE
import com.vlad1m1r.watchface.settings.ticks.TicksActivity
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
    private val dataStorage: DataStorage
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
            TYPE_HANDS -> SettingsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_text,
                        parent,
                        false
                    )
            )

            TYPE_RATE -> RateViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_rate,
                        parent,
                        false
                    ),
                RateApp(parent.context)
            )
            TYPE_ABOUT -> SettingsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_text,
                        parent,
                        false
                    )
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
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, ComplicationsActivity::class.java)
                            .putExtra(KEY_COMPLICATIONS_TITLE, R.string.wear_complications_settings)
                    )
                }
            }

            TYPE_TICKS -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_ticks_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, TicksActivity::class.java)
                            .putExtra(KEY_TICKS_TITLE, R.string.wear_ticks_settings)
                    )
                }
            }

            TYPE_BACKGROUND -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_background_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, BackgroundActivity::class.java)
                            .putExtra(KEY_BACKGROUND_TITLE, R.string.wear_background_settings)
                    )
                }
            }

            TYPE_HANDS -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_hand_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, HandsActivity::class.java)
                            .putExtra(KEY_HANDS_TITLE, R.string.wear_hand_settings)
                    )
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
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, AboutActivity::class.java)
                    )
                }
            }
        }
    }
}