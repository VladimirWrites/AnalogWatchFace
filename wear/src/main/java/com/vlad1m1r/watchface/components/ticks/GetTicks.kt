package com.vlad1m1r.watchface.components.ticks

import android.content.Context
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.TicksLayoutType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetTicks @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage,
) {

    operator fun invoke(): TicksLayout {
        return when (dataStorage.getTicksLayoutType()) {
            TicksLayoutType.ORIGINAL -> {
                TicksLayoutOriginal(context, dataStorage, colorStorage)
            }
            TicksLayoutType.TICKS_LAYOUT_1 -> {
                TicksLayout1(context, dataStorage, colorStorage)
            }
            TicksLayoutType.TICKS_LAYOUT_2 -> {
                TicksLayout2(context, dataStorage, colorStorage)
            }
            TicksLayoutType.TICKS_LAYOUT_3 -> {
                TicksLayout3(context, dataStorage, colorStorage)
            }
        }
    }
}