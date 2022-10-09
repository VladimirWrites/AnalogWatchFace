package com.vlad1m1r.watchface.settings.background

import android.content.Intent
import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.components.background.SettingsBackgroundComplicationViewHolder
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.viewholders.*
import com.vlad1m1r.watchface.settings.hands.hand.WatchFaceStateHolder
import com.vlad1m1r.watchface.utils.getActivityContext
import java.lang.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_PREVIEW = 1
private const val TYPE_COLOR_LEFT = 2
private const val TYPE_COLOR_RIGHT = 3
private const val TYPE_BLACK_AMBIENT = 4
private const val TYPE_BACKGROUND_COMPLICATION = 5

class BackgroundAdapter(
    private val stateHolder: WatchFaceStateHolder,
    private val navigator: Navigator,
    @StringRes private val title: Int,
    private val leftBackgroundColorLauncher: ActivityResultLauncher<Intent>,
    private val rightBackgroundColorLauncher: ActivityResultLauncher<Intent>,
    private val openBackgroundComplicationPicker: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var backgroundComplicationTitle: String? = null
    private var backgroundComplicationDescription: String? = null
    private var backgroundComplicationIcon: Icon? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(parent)
            TYPE_PREVIEW -> WatchPreviewViewHolder(parent)
            TYPE_COLOR_LEFT,
            TYPE_COLOR_RIGHT -> ColorPickerViewHolder(parent, navigator)
            TYPE_BLACK_AMBIENT -> SettingsWithSwitchViewHolder(parent)
            TYPE_BACKGROUND_COMPLICATION -> SettingsBackgroundComplicationViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_background_complication,
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
            1 -> TYPE_PREVIEW
            2 -> TYPE_COLOR_LEFT
            3 -> TYPE_COLOR_RIGHT
            4 -> TYPE_BLACK_AMBIENT
            5 -> TYPE_BACKGROUND_COMPLICATION
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    title
                )
            TYPE_PREVIEW -> {
                val activity = viewHolder.itemView.context.getActivityContext() as FragmentActivity
                val width = activity.window.decorView.width.toFloat()
                val height = activity.resources.getDimension(R.dimen.item_watch_preview_height)
                (viewHolder as WatchPreviewViewHolder).bind(
                    Point(width / 2, height / 2),
                    stateHolder.currentState
                )
            }
            TYPE_COLOR_LEFT ->
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_left_background_color,
                    stateHolder.currentState.backgroundState.leftColor,
                    false,
                    leftBackgroundColorLauncher
                )
            TYPE_COLOR_RIGHT ->
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_right_background_color,
                    stateHolder.currentState.backgroundState.rightColor,
                    false,
                    rightBackgroundColorLauncher
                )
            TYPE_BLACK_AMBIENT ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_black_ambient_background,
                    stateHolder.currentState.backgroundState.blackInAmbient
                ) {
                    val newBackgroundState = stateHolder.currentState.backgroundState.copy(blackInAmbient = it)
                    stateHolder.setBackgroundState(newBackgroundState)
                    notifyDataSetChanged()
                }
            TYPE_BACKGROUND_COMPLICATION -> (viewHolder as SettingsBackgroundComplicationViewHolder).bind(
                backgroundComplicationTitle,
                backgroundComplicationDescription,
                backgroundComplicationIcon
            ) {
                openBackgroundComplicationPicker()
            }
        }
    }

    fun updateBackgroundComplicationView(title: String, description: String?, providerIcon: Icon?) {
        backgroundComplicationTitle = title
        backgroundComplicationDescription = description
        backgroundComplicationIcon = providerIcon
        notifyItemChanged(5)
    }
}