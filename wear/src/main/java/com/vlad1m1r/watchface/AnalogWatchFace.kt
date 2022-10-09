package com.vlad1m1r.watchface

import android.view.SurfaceHolder
import androidx.wear.watchface.*
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyleSchema
import com.vlad1m1r.watchface.components.Layouts
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.CustomColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.data.style.CreateUserStyleSchema
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnalogWatchFace : WatchFaceService() {

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var sizeStorage: SizeStorage

    @Inject
    lateinit var colorStorage: ColorStorage

    @Inject
    lateinit var customColorStorage: CustomColorStorage

    @Inject
    lateinit var layouts: Layouts

    @Inject
    lateinit var createUserStyleSchema: CreateUserStyleSchema

    // Used by Watch Face APIs to construct user setting options and repository.
    override fun createUserStyleSchema(): UserStyleSchema = createUserStyleSchema.invoke()

    // Creates all complication user settings and adds them to the existing user settings
    // repository.
    override fun createComplicationSlotsManager(
        currentUserStyleRepository: CurrentUserStyleRepository
    ): ComplicationSlotsManager = createComplicationSlotManager(
        context = applicationContext,
        currentUserStyleRepository = currentUserStyleRepository
    )

    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        // Creates class that renders the watch face.

        val renderer = AnalogWatchCanvasRenderer(
            surfaceHolder = surfaceHolder,
            watchState = watchState,
            complicationSlotsManager = complicationSlotsManager,
            currentUserStyleRepository = currentUserStyleRepository,
            canvasType = CanvasType.HARDWARE,
            dataStorage = dataStorage,
            layouts = layouts.apply {
                setBottomInset(watchState.chinHeight)
            }
        )

        // Creates the watch face.
        return WatchFace(
            watchFaceType = WatchFaceType.ANALOG,
            renderer = renderer
        )
    }

    companion object {
        const val TAG = "AnalogWatchFaceService"
    }
}