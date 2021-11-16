package com.vlad1m1r.watchface.utils

/*
Code taken from https://github.com/benoitletondor/PixelMinimalWatchFace/blob/master/android/watchface/src/main/java/com/benoitletondor/pixelminimalwatchface/helper/CompatHelper.kt
Big thanks to Benoit Letondor for creating the initial fix! ❤️
 */

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationText
import com.vlad1m1r.watchface.R
import org.json.JSONObject
import kotlin.math.roundToInt

fun ComplicationData.sanitize(context: Context, complicationProviderName: String?): ComplicationData {
    try {
        if (Device.isSamsungGalaxy) {
            val shortText = when {
                isSamsungHeartRateBadComplicationData(context, complicationProviderName) -> context.getSamsungDataFromKey("heart_rate") ?: "?"
                isSamsungSpo2BadComplicationData(context, complicationProviderName) -> context.getSamsungDataFromKey("spo2")
                else -> ""
            }

            return if (shortText == "") this
            else {
                val builder = ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
                    .setTapAction(tapAction)
                    .setShortText(ComplicationText.plainText(shortText))
                    .setIcon(icon)
                builder.build()
            }
        }
        return this
    } catch (t: Throwable) {
        return this
    }
}

private fun Context.getSamsungDataFromKey(key: String): String? {
    val uri = "content://com.samsung.android.wear.shealth.healthdataprovider"

    val bundle = contentResolver.call(Uri.parse(uri), key, null, null)
    if (bundle != null) {
        val error = bundle.getString("error")
        if (error != null) {
            return null
        }

        val data = bundle.getString("data") ?: return null
        val json = JSONObject(data)
        val value = json.optDouble("value", -1.0)
        return if (value > 0) {
            value.roundToInt().toString()
        } else {
            null
        }
    }

    return null
}

@SuppressLint("NewApi")
private fun ComplicationData.isSamsungHeartRateBadComplicationData(context: Context, complicationProviderName: String?): Boolean {
    return icon != null &&
            icon.resPackage == "com.samsung.android.wear.shealth" &&
            context.resources.getString(R.string.heart_rate) == complicationProviderName
}

@SuppressLint("NewApi")
private fun ComplicationData.isSamsungSpo2BadComplicationData(context: Context, complicationProviderName: String?): Boolean {
    return icon != null &&
            icon.resPackage == "com.samsung.android.wear.shealth" &&
            context.resources.getString(R.string.home_spo2_title) == complicationProviderName
}
