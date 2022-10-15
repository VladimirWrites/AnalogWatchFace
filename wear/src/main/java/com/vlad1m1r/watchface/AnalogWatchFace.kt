package com.vlad1m1r.watchface

import android.view.SurfaceHolder
import androidx.wear.watchface.*
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyleSchema
import com.vlad1m1r.watchface.components.Layouts
import com.vlad1m1r.watchface.components.complications.Complications
import com.vlad1m1r.watchface.components.complications.createComplicationSlotManager
import com.vlad1m1r.watchface.data.style.CreateUserStyleSchema
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class AnalogWatchFace : WatchFaceService() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AnalogWatchFaceEntryPoint {
        fun createUserStyleSchemaImplementation(): CreateUserStyleSchema
        fun layouts(): Layouts
        fun complications(): Complications
    }

    // Used by Watch Face APIs to construct user setting options and repository.
    override fun createUserStyleSchema(): UserStyleSchema {

        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                applicationContext,
                AnalogWatchFaceEntryPoint::class.java
            )

        return hiltEntryPoint.createUserStyleSchemaImplementation().invoke()
    }

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

        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                applicationContext,
                AnalogWatchFaceEntryPoint::class.java
            )

        val renderer = AnalogWatchCanvasRenderer(
            surfaceHolder = surfaceHolder,
            watchState = watchState,
            complicationSlotsManager = complicationSlotsManager,
            currentUserStyleRepository = currentUserStyleRepository,
            canvasType = CanvasType.HARDWARE,
            complications = hiltEntryPoint.complications(),
            layouts = hiltEntryPoint.layouts().apply {
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