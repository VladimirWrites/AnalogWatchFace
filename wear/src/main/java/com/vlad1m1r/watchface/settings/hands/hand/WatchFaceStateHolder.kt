package com.vlad1m1r.watchface.settings.hands.hand

import androidx.activity.ComponentActivity
import androidx.wear.watchface.editor.EditorSession
import androidx.wear.watchface.style.UserStyle
import androidx.wear.watchface.style.UserStyleSchema
import androidx.wear.watchface.style.UserStyleSetting
import com.vlad1m1r.watchface.data.state.*
import com.vlad1m1r.watchface.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus

class WatchFaceStateHolder(
    private val scope: CoroutineScope,
    private val activity: ComponentActivity,
) {

    lateinit var currentState: WatchFaceState
    lateinit var editorSession: EditorSession

    private lateinit var backgroundBlackInAmbientStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var backgroundLeftColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var backgroundRightColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting

    private lateinit var ticksHasInAmbientModeStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var ticksHasInInteractiveModeStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var ticksLayoutTypeStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var ticksHourColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var ticksMinuteColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var ticksShouldAdjustToSquareStyleKey: UserStyleSetting.BooleanUserStyleSetting

    private lateinit var handSecondsIsSmoothStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var handSecondsHasStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var handSecondsColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var handSecondsWidthStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var handSecondsLengthScaleStyleKey: UserStyleSetting.DoubleRangeUserStyleSetting

    private lateinit var handMinutesColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var handMinutesWidthStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var handMinutesLengthScaleStyleKey: UserStyleSetting.DoubleRangeUserStyleSetting

    private lateinit var handHoursColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var handHoursWidthStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var handHoursLengthScaleStyleKey: UserStyleSetting.DoubleRangeUserStyleSetting

    private lateinit var handCircleColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var handsCircleWidthStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var handCircleRadiusStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var handCircleHasInAmbientModeStyleKey: UserStyleSetting.BooleanUserStyleSetting

    private lateinit var handsHasInInteractiveStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var handsKeepColorInAmbientModeStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var handsHasStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var watchUseAntialiasingStyleKey: UserStyleSetting.BooleanUserStyleSetting

    private lateinit var complicationsHasInAmbientModeStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var complicationsHasBiggerTopAndBottomStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var complicationsTextColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var complicationsTitleColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var complicationsIconColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var complicationsBorderColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var complicationsRangedValuePrimaryColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var complicationsRangedValueSecondaryColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting
    private lateinit var complicationsBackgroundColorStyleKey: UserStyleSetting.LongRangeUserStyleSetting

    val uiState: StateFlow<EditWatchFaceUiState> =
        flow<EditWatchFaceUiState> {
            editorSession = EditorSession.createOnWatchEditorSession(
                activity = activity
            )

            extractsUserStyles(editorSession.userStyleSchema)

            emitAll(
                combine(
                    editorSession.userStyle,
                    editorSession.complicationsPreviewData
                ) { userStyle, _ ->
                    EditWatchFaceUiState.Success(
                        createWatchFaceCurrentState(userStyle)
                    )
                }
            )
        }
            .stateIn(
                scope + Dispatchers.Main.immediate,
                SharingStarted.Eagerly,
                EditWatchFaceUiState.Loading("Initializing")
            )

    fun setBackgroundState(state: BackgroundState) {
        this.currentState = currentState.copy(backgroundState = state)
        setUserStyleOption(
            backgroundBlackInAmbientStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.blackInAmbient)
        )

        setUserStyleOption(
            backgroundRightColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.rightColor.toLong())
        )

        setUserStyleOption(
            backgroundLeftColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.leftColor.toLong())
        )
    }

    fun setTicksState(state: TicksState) {
        this.currentState = currentState.copy(ticksState = state)
        setUserStyleOption(
            ticksHasInAmbientModeStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.hasInAmbientMode)
        )

        setUserStyleOption(
            ticksHasInInteractiveModeStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.hasInInteractiveMode)
        )

        setUserStyleOption(
            ticksLayoutTypeStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.layoutType.id.toLong())
        )

        setUserStyleOption(
            ticksHourColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.hourTicksColor.toLong())
        )

        setUserStyleOption(
            ticksMinuteColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.minuteTicksColor.toLong())
        )

        setUserStyleOption(
            ticksShouldAdjustToSquareStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.shouldAdjustToSquareScreen)
        )
    }

    fun setHandsState(state: HandsState) {
        this.currentState = currentState.copy(handsState = state)
        setUserStyleOption(
            handSecondsIsSmoothStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.hasSmoothSecondsHand)
        )

        setUserStyleOption(
            handSecondsHasStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.hasSecondHand)
        )

        setUserStyleOption(
            handSecondsColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.secondsHand.color.toLong())
        )

        setUserStyleOption(
            handSecondsWidthStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.secondsHand.width.toLong())
        )

        setUserStyleOption(
            handSecondsLengthScaleStyleKey,
            UserStyleSetting.DoubleRangeUserStyleSetting.DoubleRangeOption(state.secondsHand.lengthScale.toDouble())
        )

        setUserStyleOption(
            handMinutesColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.minutesHand.color.toLong())
        )

        setUserStyleOption(
            handMinutesWidthStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.minutesHand.width.toLong())
        )

        setUserStyleOption(
            handMinutesLengthScaleStyleKey,
            UserStyleSetting.DoubleRangeUserStyleSetting.DoubleRangeOption(state.minutesHand.lengthScale.toDouble())
        )

        setUserStyleOption(
            handHoursColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.hoursHand.color.toLong())
        )

        setUserStyleOption(
            handHoursWidthStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.hoursHand.width.toLong())
        )

        setUserStyleOption(
            handHoursLengthScaleStyleKey,
            UserStyleSetting.DoubleRangeUserStyleSetting.DoubleRangeOption(state.hoursHand.lengthScale.toDouble())
        )

        setUserStyleOption(
            handCircleColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.circleState.color.toLong())
        )

        setUserStyleOption(
            handsCircleWidthStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.circleState.width.toLong())
        )

        setUserStyleOption(
            handCircleRadiusStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.circleState.radius.toLong())
        )

        setUserStyleOption(
            handCircleHasInAmbientModeStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.circleState.hasInAmbientMode)
        )

        setUserStyleOption(
            handsHasInInteractiveStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.hasInInteractive)
        )

        setUserStyleOption(
            handsKeepColorInAmbientModeStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.shouldKeepHandColorInAmbientMode)
        )

        setUserStyleOption(
            handsHasStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.hasHands)
        )
    }

    fun setComplicationsState(state: ComplicationsState) {

        setUserStyleOption(
            complicationsHasInAmbientModeStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.hasInAmbientMode)
        )

        setUserStyleOption(
            complicationsHasBiggerTopAndBottomStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state.hasBiggerTopAndBottomComplications)
        )

        setUserStyleOption(
            complicationsTextColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.textColor.toLong())
        )

        setUserStyleOption(
            complicationsTitleColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.titleColor.toLong())
        )

        setUserStyleOption(
            complicationsIconColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.iconColor.toLong())
        )

        setUserStyleOption(
            complicationsBorderColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.borderColor.toLong())
        )

        setUserStyleOption(
            complicationsRangedValuePrimaryColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.rangedValuePrimaryColor.toLong())
        )

        setUserStyleOption(
            complicationsRangedValueSecondaryColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.rangedValueSecondaryColor.toLong())
        )

        setUserStyleOption(
            complicationsBackgroundColorStyleKey,
            UserStyleSetting.LongRangeUserStyleSetting.LongRangeOption(state.backgroundColor.toLong())
        )
    }

    fun setUseAntialiasing(useAntialiasing: Boolean) {
        val handsState = currentState.handsState.copy(useAntialiasingInAmbientMode = useAntialiasing)
        val ticksState = currentState.ticksState.copy(useAntialiasingInAmbientMode = useAntialiasing)
        this.currentState = currentState.copy(handsState = handsState, ticksState = ticksState)

        setUserStyleOption(
            watchUseAntialiasingStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(useAntialiasing)
        )
    }

    private fun setUserStyleOption(
        userStyleSetting: UserStyleSetting,
        userStyleOption: UserStyleSetting.Option
    ) {

        val mutableUserStyle = editorSession.userStyle.value.toMutableUserStyle()
        mutableUserStyle[userStyleSetting] = userStyleOption
        editorSession.userStyle.value = mutableUserStyle.toUserStyle()
    }

    private fun extractsUserStyles(userStyleSchema: UserStyleSchema) {

        for (setting in userStyleSchema.userStyleSettings) {
            when (setting.id) {
                BACKGROUND_BLACK_IN_AMBIENT -> backgroundBlackInAmbientStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                BACKGROUND_LEFT_COLOR -> backgroundLeftColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                BACKGROUND_RIGHT_COLOR -> backgroundRightColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting

                TICKS_HAS_IN_AMBIENT_MODE -> ticksHasInAmbientModeStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                TICKS_HAS_IN_INTERACTIVE_MODE -> ticksHasInInteractiveModeStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                TICKS_LAYOUT_TYPE -> ticksLayoutTypeStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                TICKS_HOUR_COLOR -> ticksHourColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                TICKS_MINUTE_COLOR -> ticksMinuteColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                TICKS_SHOULD_ADJUST_TO_SQUARE -> ticksShouldAdjustToSquareStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting

                HAND_SECONDS_IS_SMOOTH -> handSecondsIsSmoothStyleKey  = setting as UserStyleSetting.BooleanUserStyleSetting
                HAND_SECONDS_HAS -> handSecondsHasStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                HAND_SECONDS_COLOR -> handSecondsColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                HAND_SECONDS_WIDTH -> handSecondsWidthStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                HAND_SECONDS_LENGTH_SCALE -> handSecondsLengthScaleStyleKey = setting as UserStyleSetting.DoubleRangeUserStyleSetting

                HAND_MINUTES_COLOR -> handMinutesColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                HAND_MINUTES_WIDTH -> handMinutesWidthStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                HAND_MINUTES_LENGTH_SCALE -> handMinutesLengthScaleStyleKey = setting as UserStyleSetting.DoubleRangeUserStyleSetting

                HAND_HOURS_COLOR -> handHoursColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                HAND_HOURS_WIDTH -> handHoursWidthStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                HAND_HOURS_LENGTH_SCALE -> handHoursLengthScaleStyleKey = setting as UserStyleSetting.DoubleRangeUserStyleSetting

                HAND_CIRCLE_COLOR -> handCircleColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                HAND_CIRCLE_WIDTH -> handsCircleWidthStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                HAND_CIRCLE_RADIUS -> handCircleRadiusStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                HAND_CIRCLE_HAS_IN_AMBIENT_MODE -> handCircleHasInAmbientModeStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting

                HANDS_HAS_IN_INTERACTIVE -> handsHasInInteractiveStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                HANDS_KEEP_COLOR_IN_AMBIENT_MODE -> handsKeepColorInAmbientModeStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                HANDS_HAS -> handsHasStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting

                COMPLICATION_HAS_IN_AMBIENT_MODE -> complicationsHasInAmbientModeStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                COMPLICATION_HAS_BIGGER_TOP_AND_BOTTOM -> complicationsHasBiggerTopAndBottomStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                COMPLICATION_TEXT_COLOR -> complicationsTextColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                COMPLICATION_TITLE_COLOR -> complicationsTitleColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                COMPLICATION_ICON_COLOR -> complicationsIconColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                COMPLICATION_BORDER_COLOR -> complicationsBorderColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                COMPLICATION_RANGED_VALUE_PRIMARY_COLOR -> complicationsRangedValuePrimaryColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                COMPLICATION_RANGED_VALUE_SECONDARY_COLOR -> complicationsRangedValueSecondaryColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting
                COMPLICATION_BACKGROUND_COLOR -> complicationsBackgroundColorStyleKey = setting as UserStyleSetting.LongRangeUserStyleSetting

                WATCH_USE_ANTIALIASING -> watchUseAntialiasingStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
            }
        }
    }

    private fun createWatchFaceCurrentState(
        userStyle: UserStyle
    ): WatchFaceState {
        return userStyle.toWatchFaceState()
    }

    sealed class EditWatchFaceUiState {
        data class Success(val watchFaceState: WatchFaceState) :
            EditWatchFaceUiState()

        data class Loading(val message: String) : EditWatchFaceUiState()
        data class Error(val exception: Throwable) : EditWatchFaceUiState()
    }
}
