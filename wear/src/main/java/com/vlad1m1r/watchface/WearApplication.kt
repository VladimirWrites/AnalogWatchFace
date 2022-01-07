package com.vlad1m1r.watchface

import android.app.Application
import androidx.annotation.Keep
import dagger.hilt.android.HiltAndroidApp

@Keep
@HiltAndroidApp
class WearApplication : Application()