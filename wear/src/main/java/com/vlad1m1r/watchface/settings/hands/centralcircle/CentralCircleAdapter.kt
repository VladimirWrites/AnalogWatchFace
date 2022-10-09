package com.vlad1m1r.watchface.settings.hands.centralcircle

import android.content.Intent
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.viewholders.*
import com.vlad1m1r.watchface.settings.hands.hand.WatchFaceStateHolder
import com.vlad1m1r.watchface.utils.getActivityContext
import java.lang.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_PREVIEW = 1
private const val TYPE_COLOR_CENTRAL_CIRCLE = 2
private const val TYPE_CENTRAL_CIRCLE_WIDTH = 3
private const val TYPE_CENTRAL_CIRCLE_RADIUS = 4
private const val TYPE_CENTRAL_CIRCLE_IN_AMBIENT_MODE = 5

class CentralCircleAdapter(
    private val stateHolder: WatchFaceStateHolder,
    private val navigator: Navigator,
    @StringRes private val title: Int,
    private val centralCircleColorLauncher: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(parent)
            TYPE_PREVIEW -> WatchPreviewViewHolder(parent)
            TYPE_COLOR_CENTRAL_CIRCLE -> ColorPickerViewHolder(parent, navigator)
            TYPE_CENTRAL_CIRCLE_WIDTH,
            TYPE_CENTRAL_CIRCLE_RADIUS -> SettingsSliderViewHolder(parent)
            TYPE_CENTRAL_CIRCLE_IN_AMBIENT_MODE -> SettingsWithSwitchViewHolder(parent)
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
            2 -> TYPE_COLOR_CENTRAL_CIRCLE
            3 -> TYPE_CENTRAL_CIRCLE_WIDTH
            4 -> TYPE_CENTRAL_CIRCLE_RADIUS
            5 -> TYPE_CENTRAL_CIRCLE_IN_AMBIENT_MODE
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
            TYPE_COLOR_CENTRAL_CIRCLE -> (viewHolder as ColorPickerViewHolder).bind(
                R.string.wear_central_circle_color,
                stateHolder.currentState.handsState.circleState.color,
                true,
                centralCircleColorLauncher
            )
            TYPE_CENTRAL_CIRCLE_WIDTH -> (viewHolder as SettingsSliderViewHolder).setData(
                R.string.wear_circle_width,
                stateHolder.currentState.handsState.circleState.width,
                1,
                stateHolder.currentState.handsState.circleState.radius
            ) { circleWidth ->
                val newCircleState =
                    stateHolder.currentState.handsState.circleState.copy(width = circleWidth)
                stateHolder.setHandsState(stateHolder.currentState.handsState.copy(circleState = newCircleState))
                notifyDataSetChanged()
            }

            TYPE_CENTRAL_CIRCLE_RADIUS -> (viewHolder as SettingsSliderViewHolder).setData(
                R.string.wear_circle_radius,
                stateHolder.currentState.handsState.circleState.radius,
                1,
                15
            ) { circleRadius ->
                val newCircleState =
                    stateHolder.currentState.handsState.circleState.copy(radius = circleRadius)
                stateHolder.setHandsState(stateHolder.currentState.handsState.copy(circleState = newCircleState))
                notifyDataSetChanged()
            }

            TYPE_CENTRAL_CIRCLE_IN_AMBIENT_MODE ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_central_circle_in_ambient_mode,
                    stateHolder.currentState.handsState.circleState.hasInAmbientMode
                ) {
                    val newCircleState =
                        stateHolder.currentState.handsState.circleState.copy(hasInAmbientMode = it)
                    stateHolder.setHandsState(stateHolder.currentState.handsState.copy(circleState = newCircleState))
                    notifyDataSetChanged()
                }
        }
    }
}