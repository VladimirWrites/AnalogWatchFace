package com.vlad1m1r.watchface.settings.ticks

import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsWithSwitchViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder
import com.vlad1m1r.watchface.settings.hands.hand.WatchFaceStateHolder
import com.vlad1m1r.watchface.settings.ticks.viewholders.TicksLayoutPickerViewHolder
import java.lang.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_TICKS_AMBIENT_MODE = 1
private const val TYPE_TICKS_INTERACTIVE_MODE = 2
private const val TYPE_HOUR_TICKS_COLOR = 3
private const val TYPE_MINUTE_TICKS_COLOR = 4
private const val TYPE_TICKS_LAYOUT_PICKER = 5
private const val TYPE_ADJUST_TO_SQUARE_SCREEN = 6

class TicksAdapter(
    private val res: Resources,
    private val stateHolder: WatchFaceStateHolder,
    private val navigator: Navigator,
    @StringRes private val title: Int,
    private val watchFacePickerLauncher: ActivityResultLauncher<Intent>,
    private val hourTickColorLauncher: ActivityResultLauncher<Intent>,
    private val minuteTickColorLauncher: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var ticksLayoutPickerViewHolder: TicksLayoutPickerViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(parent)
            TYPE_TICKS_LAYOUT_PICKER -> TicksLayoutPickerViewHolder(parent, stateHolder, navigator).apply {
                ticksLayoutPickerViewHolder = this
            }
            TYPE_TICKS_INTERACTIVE_MODE,
            TYPE_TICKS_AMBIENT_MODE,
            TYPE_ADJUST_TO_SQUARE_SCREEN -> SettingsWithSwitchViewHolder(parent)
            TYPE_HOUR_TICKS_COLOR,
            TYPE_MINUTE_TICKS_COLOR -> ColorPickerViewHolder(parent, navigator)
            else -> {
                throw IllegalArgumentException("viewType: $viewType is not supported")
            }
        }
    }

    override fun getItemCount(): Int {
        return if (res.configuration.isScreenRound) 6 else 7
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_TICKS_LAYOUT_PICKER
            2 -> TYPE_TICKS_AMBIENT_MODE
            3 -> TYPE_TICKS_INTERACTIVE_MODE
            4 -> TYPE_HOUR_TICKS_COLOR
            5 -> TYPE_MINUTE_TICKS_COLOR
            6 -> TYPE_ADJUST_TO_SQUARE_SCREEN
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    title
                )
            TYPE_TICKS_LAYOUT_PICKER ->
                (viewHolder as TicksLayoutPickerViewHolder).bind(
                    watchFacePickerLauncher
                )
            TYPE_TICKS_AMBIENT_MODE ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_ticks_in_ambient_mode,
                    stateHolder.currentState.ticksState.hasInAmbientMode
                ) {
                    val newTicksState = stateHolder.currentState.ticksState.copy(hasInAmbientMode = it)
                    stateHolder.setTicksState(newTicksState)
                }
            TYPE_TICKS_INTERACTIVE_MODE ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_ticks_in_interactive_mode,
                    stateHolder.currentState.ticksState.hasInInteractiveMode
                ) {
                    val newTicksState = stateHolder.currentState.ticksState.copy(hasInInteractiveMode = it)
                    stateHolder.setTicksState(newTicksState)
                }
            TYPE_HOUR_TICKS_COLOR ->
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_hour_ticks_color,
                    stateHolder.currentState.ticksState.hourTicksColor,
                    true,
                    hourTickColorLauncher
                )
            TYPE_MINUTE_TICKS_COLOR ->
                (viewHolder as ColorPickerViewHolder).bind(
                    R.string.wear_minute_ticks_color,
                    stateHolder.currentState.ticksState.minuteTicksColor,
                    true,
                    minuteTickColorLauncher
                )
            TYPE_ADJUST_TO_SQUARE_SCREEN ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_ticks_adjust_to_square_screen,
                    stateHolder.currentState.ticksState.shouldAdjustToSquareScreen
                ) {
                    val newTicksState = stateHolder.currentState.ticksState.copy(shouldAdjustToSquareScreen = it)
                    stateHolder.setTicksState(newTicksState)
                }
        }
    }

    fun updateWatchFacePicker() {
        if (::ticksLayoutPickerViewHolder.isInitialized) {
            ticksLayoutPickerViewHolder.refreshImage()
        }
    }
}