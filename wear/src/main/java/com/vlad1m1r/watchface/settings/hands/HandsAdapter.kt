package com.vlad1m1r.watchface.settings.hands

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder
import com.vlad1m1r.watchface.settings.hands.hand.HandType
import java.lang.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_HOUR_HAND = 1
private const val TYPE_MINUTE_HAND = 2
private const val TYPE_SECOND_HAND = 3
private const val TYPE_CENTRAL_CIRCLE = 4

class HandsAdapter(
    private val navigator: Navigator,
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
            TYPE_HOUR_HAND,
            TYPE_MINUTE_HAND,
            TYPE_SECOND_HAND,
            TYPE_CENTRAL_CIRCLE -> SettingsViewHolder(
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
        return 5
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_HOUR_HAND
            2 -> TYPE_MINUTE_HAND
            3 -> TYPE_SECOND_HAND
            4 -> TYPE_CENTRAL_CIRCLE
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    title
                )
            TYPE_HOUR_HAND -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_hours_hand
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToHandActivity(activity, R.string.wear_hours_hand, HandType.HOURS)
                }
            }
            TYPE_MINUTE_HAND -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_minutes_hand
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToHandActivity(activity, R.string.wear_minutes_hand, HandType.MINUTES)
                }
            }
            TYPE_SECOND_HAND -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_seconds_hand
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToHandActivity(activity, R.string.wear_seconds_hand, HandType.SECONDS)
                }
            }
            TYPE_CENTRAL_CIRCLE -> {
                (viewHolder as SettingsViewHolder).bind(
                    R.string.wear_central_circle
                ) {
                    val activity = viewHolder.itemView.context as Activity
                    navigator.goToCentralCircleActivity(activity, R.string.wear_central_circle)
                }
            }
        }
    }
}