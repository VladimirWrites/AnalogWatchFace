package com.vlad1m1r.watchface.utils

/*
Code taken from https://github.com/benoitletondor/PixelMinimalWatchFace/blob/master/android/watchface/src/main/java/com/benoitletondor/pixelminimalwatchface/helper/CompatHelper.kt
Big thanks to Benoit Letondor for creating the fix! ❤️
 */

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Icon
import android.net.Uri
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationText
import com.vlad1m1r.watchface.R
import org.json.JSONObject
import kotlin.math.roundToInt

fun ComplicationData.sanitize(context: Context): ComplicationData {
    try {
        if (!Device.isSamsungGalaxy) {
            return this
        }

        if (!isSamsungHeartRateBadComplicationData(context)) {
            return this
        }

        val shortText = context.getSamsungHeartRateData() ?: "?"

        val icon = Icon.createWithResource(context, R.drawable.ic_heart_rate_complication)

        val builder = ComplicationData.Builder(ComplicationData.TYPE_SHORT_TEXT)
            .setTapAction(tapAction)
            .setShortText(ComplicationText.plainText(shortText))
            .setIcon(icon)
            .setBurnInProtectionIcon(icon)
        return builder.build()
    } catch (t: Throwable) {
        return this
    }
}

private fun Context.getSamsungHeartRateData(): String? {
    val uri = "content://com.samsung.android.wear.shealth.healthdataprovider"

    val bundle = contentResolver.call(Uri.parse(uri), "heart_rate", null, null)
    if (bundle != null) {
        val error = bundle.getString("error")
        if (error != null) {
            return null
        }

        val data = bundle.getString("data") ?: return null
        val json = JSONObject(data)
        val hr = json.optDouble("value", -1.0)
        return if (hr > 0) {
            hr.roundToInt().toString()
        } else {
            null
        }
    }

    return null
}

private val samsungHeartRateComplicationShortTextValues = setOf(
    "心跳率",
    "心率",
    "Nhịp tim",
    "Yurak puls",
    "شرح قلب",
    "Пульс",
    "Klp atş hz",
    "Heart rate",
    "Ýürek ritm",
    "อัตราการเต้นหัวใจ",
    "Тапиши дил",
    "హృదయ స్పందన రేటు",
    "இ.து.விகி.",
    "Puls",
    "Rr. zemrës",
    "Srč. utrip",
    "Srdcový tep",
    "හෘද වේගය",
    "Пульс",
    "Freq. card.",
    "Freq. car.",
    "Tyntno",
    "ਦਿਲ ਦੀ ਗਤੀ",
    "ହାର୍ଟ୍ ରେଟ୍",
    "Hartslag",
    "हृदय गति<",
    "ႏွလုံး ခုန္ႏႈန္း",
    "နှလုံး ခုန်နှုန်း",
    "Kdr jntung",
    "हृदय गती",
    "Зүрхний цохилт",
    "ഹൃദയമിടി.",
    "Пулс",
    "Sirds rit.",
    "Šird. rit.",
    "ອັດຕາຫົວໃຈເຕັ້ນ",
    "Жүрөк согушу",
    "심박수",
    "ಹೃದಯ ಬಡಿತದ ದರ",
    "Жүрек соғ.",
    "პულსი",
    "心拍数",
    "דופק לב",
    "Freq. card.",
    "Púls",
    "Dnyt jntng",
    "Սրտխփ. հճխ",
    "Pulzus",
    "Otk. srca",
    "हृदय गति",
    "હૃદય દર",
    "Ritmo car.",
    "Croíráta",
    "Fréq. car.",
    "Cardio",
    "Syke",
    "ضربان قلب",
    "Bihotz frek.",
    "Süd. löög.",
    "RC",
    "FC",
    "Καρ. παλμ.",
    "Srd. tep",
    "Ritme card",
    "སྙིང་འཕར་ཚད།",
    "হৃদয. হার",
    "হৃদস্পন্দনের হার",
    "Сър. ритъм",
    "Част. пул.",
    "Ürək ritmi",
    "হৃদ হাৰ",
    "سرعة ضربات القلب",
    "HeartRate",
)

@SuppressLint("NewApi")
private fun ComplicationData.isSamsungHeartRateBadComplicationData(context: Context): Boolean {
    if (shortText != null && samsungHeartRateComplicationShortTextValues.contains(shortText.getText(context, System.currentTimeMillis()))) {
        return true
    }

    if (icon != null && icon.resPackage == "com.samsung.android.wear.shealth") {
        return true
    }

    return false
}
