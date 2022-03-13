package com.vlad1m1r.watchface.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.vlad1m1r.watchface.settings.about.AboutActivity
import com.vlad1m1r.watchface.settings.background.BackgroundActivity
import com.vlad1m1r.watchface.settings.colorpicker.ColorPickerActivity
import com.vlad1m1r.watchface.settings.colorpicker.customcolor.CustomColorActivity
import com.vlad1m1r.watchface.settings.complications.ComplicationsActivity
import com.vlad1m1r.watchface.settings.complications.colors.ComplicationColorsActivity
import com.vlad1m1r.watchface.settings.complications.picker.ComplicationPickerActivity
import com.vlad1m1r.watchface.settings.confirm.ConfirmDeleteColorActivity
import com.vlad1m1r.watchface.settings.hands.HandsActivity
import com.vlad1m1r.watchface.settings.hands.centralcircle.CentralCircleActivity
import com.vlad1m1r.watchface.settings.hands.hand.HandActivity
import com.vlad1m1r.watchface.settings.hands.hand.HandType
import com.vlad1m1r.watchface.settings.ticks.TicksActivity
import com.vlad1m1r.watchface.settings.tickslayoutpicker.TicksLayoutPickerActivity
import javax.inject.Inject

class Navigator @Inject constructor() {

    fun goToAboutActivity(activity: Activity) {
        activity.startActivity(
            AboutActivity.newInstance(activity)
        )
    }

    fun goToBackgroundActivity(activity: Activity, @StringRes title: Int) {
        activity.startActivity(
            BackgroundActivity.newInstance(activity, title)
        )
    }

    fun goToColorPickerActivityForResult(
        activityResultLauncher: ActivityResultLauncher<Intent>,
        context: Context,
        showNoColor: Boolean,
        @ColorInt preselectedColor: Int
    ) {
        val intent = ColorPickerActivity.newInstance(context, showNoColor, preselectedColor)
        activityResultLauncher.launch(
            intent
        )
    }

    fun goToComplicationsActivity(activity: Activity, @StringRes title: Int) {
        activity.startActivity(
            ComplicationsActivity.newInstance(activity, title)
        )
    }

    fun goToComplicationsPickerActivity(activity: Activity) {
        activity.startActivity(
            ComplicationPickerActivity.newInstance(activity)
        )
    }

    fun goToComplicationColorsActivity(activity: Activity, @StringRes title: Int) {
        activity.startActivity(
            ComplicationColorsActivity.newInstance(activity, title)
        )
    }

    fun goToCentralCircleActivity(activity: Activity, @StringRes title: Int) {
        activity.startActivity(
            CentralCircleActivity.newInstance(activity, title)
        )
    }

    fun goToHandActivity(
        activity: Activity,
        @StringRes title: Int,
        handType: HandType
    ) {
        activity.startActivity(
            HandActivity.newInstance(activity, title, handType)
        )
    }

    fun goToHandsActivity(activity: Activity, @StringRes title: Int) {
        activity.startActivity(
            HandsActivity.newInstance(activity, title)
        )
    }

    fun goToTicksActivity(activity: Activity, @StringRes title: Int) {
        activity.startActivity(
            TicksActivity.newInstance(activity, title)
        )
    }

    fun goToTicksLayoutPickerActivityForResult(
        activityResultLauncher: ActivityResultLauncher<Intent>,
        context: Context
    ) {
        activityResultLauncher.launch(
            TicksLayoutPickerActivity.newInstance(context)
        )
    }

    fun goToAddCustomColor(
        activityResultLauncher: ActivityResultLauncher<Intent>,
        context: Context
    ) {
        activityResultLauncher.launch(
            CustomColorActivity.newInstance(context)
        )
    }

    fun goToConfirmDeleteColor(
        activityResultLauncher: ActivityResultLauncher<Intent>,
        context: Context,
        @StringRes text: Int,
        @ColorInt color: Int
    ) {
        activityResultLauncher.launch(
            ConfirmDeleteColorActivity.newInstance(context, text, color)
        )
    }
}