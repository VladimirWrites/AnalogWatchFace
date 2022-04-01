package com.vlad1m1r.watchface.provider

import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationText
import java.util.*

//class Time24Provider: ComplicationProviderService() {
//    override fun onComplicationUpdate(
//        complicationId: Int,
//        dataType: Int,
//        complicationManager: ComplicationManager
//    ) {
//        val calendar = Calendar.getInstance()
//        val hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
//        val minuteTemp = calendar.get(Calendar.SECOND)
//        val minute = if(minuteTemp < 10) "0$minuteTemp" else minuteTemp.toString()
//
//        val time = "$hour:$minute"
//
//        when (dataType) {
//            ComplicationData.TYPE_SHORT_TEXT ->
//                ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
//                    .setShortText(ComplicationText.plainText(time))
//                    .build().also { complicationData ->
//                        complicationManager.updateComplicationData(complicationId, complicationData)
//                    }
//            else -> {
//                complicationManager.noUpdateRequired(complicationId)
//            }
//        }
//    }
//
//
//}