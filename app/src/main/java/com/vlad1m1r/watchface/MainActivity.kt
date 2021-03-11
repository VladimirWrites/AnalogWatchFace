package com.vlad1m1r.watchface

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.wearable.intent.RemoteIntent

class MainActivity : Activity() {
    private lateinit var root: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        root = findViewById(R.id.root_view)

        val subtitle = findViewById<TextView>(R.id.text_subtitle)
        val message = findViewById<TextView>(R.id.text_message)
        val gitHub = findViewById<TextView>(R.id.button_open_github)
        val email = findViewById<TextView>(R.id.button_send_email)
        val playStore = findViewById<TextView>(R.id.button_open_playstore)
        val installWatchFace = findViewById<Button>(R.id.button_install_watch_face)
        val uninstallApp = findViewById<Button>(R.id.button_uninstall_app)

        uninstallApp.setOnClickListener { uninstallApp() }

        if (isWearOsAppAvailable()) {
            subtitle.visibility = View.VISIBLE
            gitHub.visibility = View.VISIBLE
            email.visibility = View.VISIBLE
            playStore.visibility = View.VISIBLE
            installWatchFace.visibility = View.VISIBLE

            message.text = getString(R.string.description)
            message.setTextAppearance(R.style.TextAppearance_MaterialComponents_Body1)
            message.setTextColor(ContextCompat.getColor(this, R.color.white))
            message.setTypeface(null, Typeface.NORMAL)

            gitHub.setOnClickListener { openUri(getString(R.string.github_url)) }
            email.setOnClickListener { openUri("mailto:${getString(R.string.email)}") }
            playStore.setOnClickListener { openUri(getString(R.string.play_store_url)) }
            installWatchFace.setOnClickListener { openUriOnWear(getString(R.string.play_store_url)) }
        } else {
            subtitle.visibility = View.GONE

            message.text = getString(R.string.no_watch)
            message.setTextAppearance(R.style.TextAppearance_MaterialComponents_Headline6)
            message.setTextColor(ContextCompat.getColor(this, R.color.accent_color))
            message.setTypeface(null, Typeface.BOLD)

            gitHub.visibility = View.GONE
            email.visibility = View.GONE
            playStore.visibility = View.GONE
            installWatchFace.visibility = View.GONE
        }
    }

    private fun openUri(uri: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(browserIntent)
    }

    private fun isWearOsAppAvailable(): Boolean {
        return try {
            packageManager.getPackageInfo("com.google.android.wearable.app", PackageManager.GET_META_DATA)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun openUriOnWear(uri: String) {

        val intent = Intent(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(Uri.parse(uri))

        RemoteIntent.startRemoteActivity(this, intent, object : ResultReceiver(Handler(Looper.getMainLooper())) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                super.onReceiveResult(resultCode, resultData)
                if (resultCode == RemoteIntent.RESULT_OK) {
                    showMessage(R.string.open_playstore_success_message)
                } else {
                    showMessage(R.string.open_playstore_error_message)
                }
            }
        })
    }

    private fun showMessage(@StringRes message: Int) {
        val snackbar = Snackbar.make(root, getString(message), Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.accent_color))
            .setTextColor(ContextCompat.getColor(this, R.color.white))

        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        textView.maxLines = 5

        snackbar.show()
    }

    private fun uninstallApp() {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }
}