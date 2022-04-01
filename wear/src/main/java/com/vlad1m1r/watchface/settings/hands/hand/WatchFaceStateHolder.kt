package com.vlad1m1r.watchface.settings.hands.hand

import androidx.activity.ComponentActivity
import androidx.wear.watchface.editor.EditorSession
import androidx.wear.watchface.style.UserStyle
import androidx.wear.watchface.style.UserStyleSchema
import androidx.wear.watchface.style.UserStyleSetting
import com.vlad1m1r.watchface.WATCH_BACKGROUND_MODIFIED
import com.vlad1m1r.watchface.WATCH_COMPLICATION_MODIFIED
import com.vlad1m1r.watchface.WATCH_HANDS_MODIFIED
import com.vlad1m1r.watchface.WATCH_TICKS_MODIFIED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus

class WatchFaceStateHolder(
    private val scope: CoroutineScope,
    private val activity: ComponentActivity
) {

    lateinit var editorSession: EditorSession

    private lateinit var backgroundStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var handsStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var complicationStyleKey: UserStyleSetting.BooleanUserStyleSetting
    private lateinit var ticksStyleKey: UserStyleSetting.BooleanUserStyleSetting

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

    fun setBackgroundState(state: Boolean) {
        setUserStyleOption(
            backgroundStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state)
        )
    }

    fun setHandsState(state: Boolean) {
        setUserStyleOption(
            handsStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state)
        )
    }

    fun setComplicationsState(state: Boolean) {
        setUserStyleOption(
            complicationStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state)
        )
    }

    fun setTicksState(state: Boolean) {
        setUserStyleOption(
            ticksStyleKey,
            UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(state)
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
            when (setting.id.toString()) {
                WATCH_BACKGROUND_MODIFIED -> {
                    backgroundStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                }

                WATCH_HANDS_MODIFIED -> {
                    handsStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                }

                WATCH_COMPLICATION_MODIFIED -> {
                    complicationStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                }

                WATCH_TICKS_MODIFIED -> {
                    ticksStyleKey = setting as UserStyleSetting.BooleanUserStyleSetting
                }
            }
        }
    }

    private fun createWatchFaceCurrentState(
        userStyle: UserStyle
    ): WatchFaceCurrentState {


        val backgroundStyle =
            userStyle[backgroundStyleKey] as UserStyleSetting.BooleanUserStyleSetting.BooleanOption
        val handsStyle =
            userStyle[handsStyleKey] as UserStyleSetting.BooleanUserStyleSetting.BooleanOption
        val complicationStyle =
            userStyle[complicationStyleKey] as UserStyleSetting.BooleanUserStyleSetting.BooleanOption
        val ticksStyle =
            userStyle[ticksStyleKey] as UserStyleSetting.BooleanUserStyleSetting.BooleanOption


        return WatchFaceCurrentState(
            backgroundStyle = backgroundStyle.value,
            handsStyle = handsStyle.value,
            complicationStyle = complicationStyle.value,
            ticksStyle = ticksStyle.value
        )
    }

    data class WatchFaceCurrentState(
        val backgroundStyle: Boolean,
        val handsStyle: Boolean,
        val complicationStyle: Boolean,
        val ticksStyle: Boolean
    )

    sealed class EditWatchFaceUiState {
        data class Success(val watchFaceCurrentState: WatchFaceCurrentState) :
            EditWatchFaceUiState()

        data class Loading(val message: String) : EditWatchFaceUiState()
        data class Error(val exception: Throwable) : EditWatchFaceUiState()
    }

}
