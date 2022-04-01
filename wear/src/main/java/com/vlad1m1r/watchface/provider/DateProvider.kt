package com.vlad1m1r.watchface.provider

import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationText
import java.util.*

//class DateProvider: ComplicationProviderService() {
//    override fun onComplicationUpdate(
//        complicationId: Int,
//        dataType: Int,
//        complicationManager: ComplicationManager
//    ) {
//
//        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
//
//        when (dataType) {
//            ComplicationData.TYPE_SHORT_TEXT ->
//                ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
//                    .setShortText(ComplicationText.plainText(currentDay))
//                    .build().also { complicationData ->
//                        complicationManager.updateComplicationData(complicationId, complicationData)
//                    }
//            else -> {
//                complicationManager.noUpdateRequired(complicationId)
//            }
//        }
//    }
//}