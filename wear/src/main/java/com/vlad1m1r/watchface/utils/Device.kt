package com.vlad1m1r.watchface.utils

import android.os.Build

object Device {
    val isSamsungGalaxy get(): Boolean = Build.VERSION.SDK_INT >= 30 && Build.BRAND.equals("samsung", ignoreCase = true)
}