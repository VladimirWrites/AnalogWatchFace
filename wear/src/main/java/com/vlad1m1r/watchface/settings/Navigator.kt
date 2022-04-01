package com.vlad1m1r.watchface.settings

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.about.AboutFragment
import com.vlad1m1r.watchface.settings.background.BackgroundFragment
import com.vlad1m1r.watchface.settings.colorpicker.ColorPickerActivity
import com.vlad1m1r.watchface.settings.colorpicker.customcolor.CustomColorActivity
import com.vlad1m1r.watchface.settings.complications.ComplicationsFragment
import com.vlad1m1r.watchface.settings.complications.colors.ComplicationColorsFragment
import com.vlad1m1r.watchface.settings.complications.picker.ComplicationPickerFragment
import com.vlad1m1r.watchface.settings.confirm.ConfirmDeleteColorActivity
import com.vlad1m1r.watchface.settings.hands.HandsFragment
import com.vlad1m1r.watchface.settings.hands.centralcircle.CentralCircleFragment
import com.vlad1m1r.watchface.settings.hands.hand.HandFragment
import com.vlad1m1r.watchface.settings.hands.hand.HandType
import com.vlad1m1r.watchface.settings.ticks.TicksFragment
import com.vlad1m1r.watchface.settings.tickslayoutpicker.TicksLayoutPickerActivity
import javax.inject.Inject

class Navigator @Inject constructor() {

    fun goToAboutActivity(activity: FragmentActivity) {
        navigateToFragment(activity, AboutFragment())
    }

    fun goToBackgroundActivity(activity: FragmentActivity, @StringRes title: Int) {
        navigateToFragment(activity, BackgroundFragment(title))
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

    fun goToComplicationsFragments(activity: FragmentActivity, @StringRes title: Int) {
        navigateToFragment(activity, ComplicationsFragment(title))
    }

    fun goToComplicationsPickerFragment(activity: FragmentActivity) {
        navigateToFragment(activity, ComplicationPickerFragment())
    }

    fun goToComplicationColorsFragment(activity: FragmentActivity, @StringRes title: Int) {
        navigateToFragment(activity, ComplicationColorsFragment(title))
    }

    fun goToCentralCircleFragment(activity: FragmentActivity, @StringRes title: Int) {
        navigateToFragment(activity, CentralCircleFragment(title))
    }

    fun goToHandFragment(
        activity: FragmentActivity,
        @StringRes title: Int,
        handType: HandType
    ) {
        navigateToFragment(activity, HandFragment(title, handType))
    }

    fun goToHandsFragments(activity: FragmentActivity, @StringRes title: Int) {
        navigateToFragment(activity, HandsFragment(title))
    }

    fun goToTicksFragment(activity: FragmentActivity, @StringRes title: Int) {
        navigateToFragment(activity, TicksFragment(title))
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

    fun goToSettingsFragment(
        activity: FragmentActivity
    ) {
        navigateToFragment(activity, SettingsFragment(), false)
    }

    private fun navigateToFragment(
        activity: FragmentActivity,
        fragment: Fragment,
        addToBackstack: Boolean = true
    ) {
        activity.supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment).apply {
                if (addToBackstack) {
                    addToBackStack(null)
                }
            }
            .commit()
    }

}