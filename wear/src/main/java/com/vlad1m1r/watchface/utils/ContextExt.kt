package com.vlad1m1r.watchface.utils

import android.content.Context
import dagger.hilt.android.internal.managers.ViewComponentManager

fun Context.getActivityContext(): Context {
    return if (this is ViewComponentManager.FragmentContextWrapper) {
        this.baseContext
    } else this
}