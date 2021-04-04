package com.vlad1m1r.watchface.provider

import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationManager
import android.support.wearable.complications.ComplicationProviderService
import android.support.wearable.complications.ComplicationText
import java.util.*

class Time12Provider: ComplicationProviderService() {
    override fun onComplicationUpdate(
        complicationId: Int,
        dataType: Int,
        complicationManager: ComplicationManager
    ) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR).toString()
        val minute = calendar.get(Calendar.MINUTE).toString()
        val amPm = if(calendar.get(Calendar.AM_PM) == 0) "AM" else "PM"

        val time = "$hour:$minute $amPm"

        when (dataType) {
            ComplicationData.TYPE_SHORT_TEXT ->
                ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
                    .setShortText(ComplicationText.plainText(time))
                    .build().also { complicationData ->
                        complicationManager.updateComplicationData(complicationId, complicationData)
                    }
            ComplicationData.TYPE_LONG_TEXT ->
                ComplicationData.Builder(ComplicationData.TYPE_LONG_TEXT)
                    .setLongText(ComplicationText.plainText(time))
                    .build().also { complicationData ->
                        complicationManager.updateComplicationData(complicationId, complicationData)
                    }
            else -> {
                complicationManager.noUpdateRequired(complicationId)
            }
        }
    }
}