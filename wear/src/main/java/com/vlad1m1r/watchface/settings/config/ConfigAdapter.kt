package com.vlad1m1r.watchface.settings.config

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.config.viewholders.*
import com.vlad1m1r.watchface.settings.about.AboutActivity
import com.vlad1m1r.watchface.settings.background.BackgroundActivity
import com.vlad1m1r.watchface.settings.complications.ComplicationsActivity
import com.vlad1m1r.watchface.settings.hands.HandsActivity
import com.vlad1m1r.watchface.settings.ticks.TicksActivity
import java.lang.IllegalArgumentException

const val TYPE_COMPLICATIONS = 2
const val TYPE_TICKS = 3
const val TYPE_BACKGROUND = 4
const val TYPE_RATE = 5
const val TYPE_HANDS = 6
const val TYPE_ABOUT = 7

class ConfigAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            TYPE_COMPLICATIONS, TYPE_TICKS, TYPE_BACKGROUND, TYPE_HANDS ->
                viewHolder = SettingsViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_text,
                            parent,
                            false
                        )
                )

            TYPE_RATE ->
                viewHolder = RateViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_rate,
                            parent,
                            false
                        ),
                    RateApp(parent.context)
                )
            TYPE_ABOUT ->
                viewHolder = SettingsViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_text,
                            parent,
                            false
                        )
                )
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_COMPLICATIONS
            1 -> TYPE_TICKS
            2 -> TYPE_BACKGROUND
            3 -> TYPE_HANDS
            4 -> TYPE_ABOUT
            5 -> TYPE_RATE
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_COMPLICATIONS -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.complications_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, ComplicationsActivity::class.java)
                    )
                }
            }

            TYPE_TICKS -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.ticks_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, TicksActivity::class.java)
                    )
                }
            }

            TYPE_BACKGROUND -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.background_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, BackgroundActivity::class.java)
                    )
                }
            }

            TYPE_HANDS -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.hand_settings
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    activity.startActivity(
                        Intent(viewHolder.itemView.context, HandsActivity::class.java)
                    )
                }
            }

            TYPE_ABOUT -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.about_app
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