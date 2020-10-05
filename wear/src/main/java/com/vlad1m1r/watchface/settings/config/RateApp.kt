package com.vlad1m1r.watchface.settings.config

import android.content.Context
import android.content.Intent
import android.net.Uri

class RateApp(private val context: Context) {
    fun openAppInPlayStore() {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context.packageName}")))
        } catch (exception: android.content.ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")))
        }
    }
}