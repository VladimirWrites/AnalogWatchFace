package com.vlad1m1r.watchface.settings.about

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ScrollView
import android.widget.TextView
import androidx.wear.activity.ConfirmationActivity
import com.google.android.wearable.input.RotaryEncoderHelper
import com.google.android.wearable.intent.RemoteIntent
import com.vlad1m1r.watchface.BuildConfig
import com.vlad1m1r.watchface.R
import kotlin.math.roundToInt

class AboutActivity : Activity() {

    private lateinit var scrollView: ScrollView
    private lateinit var versionName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        scrollView = findViewById(R.id.scroll_view)
        versionName = findViewById(R.id.version_name)
        val versionText = "v${BuildConfig.VERSION_NAME}"
        versionName.text = versionText

        val gitHub = findViewById<TextView>(R.id.github_view)
        val email = findViewById<TextView>(R.id.email_view)
        val playStore = findViewById<TextView>(R.id.play_store_view)

        gitHub.setOnClickListener { openUri(getString(R.string.github_url)) }
        email.setOnClickListener { openUri("mailto:${getString(R.string.email)}") }
        playStore.setOnClickListener { openUri(getString(R.string.play_store_url)) }
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_SCROLL && RotaryEncoderHelper.isFromRotaryEncoder(event)) {
            val delta = -RotaryEncoderHelper.getRotaryAxisValue(event) * RotaryEncoderHelper.getScaledScrollFactor(this)
            scrollView.scrollBy(0, delta.roundToInt())
            return true
        }
        return super.onGenericMotionEvent(event)
    }

    private fun openUri(uri: String) {
        val intentConfirmationActivity = Intent(this, ConfirmationActivity::class.java)
        intentConfirmationActivity.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.OPEN_ON_PHONE_ANIMATION)
        startActivity(intentConfirmationActivity)

        val intent = Intent(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(Uri.parse(uri))
        RemoteIntent.startRemoteActivity(this, intent, null)
    }
}