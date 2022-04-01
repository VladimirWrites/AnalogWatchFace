package com.vlad1m1r.watchface.settings.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.wear.activity.ConfirmationActivity
import androidx.wear.widget.SwipeDismissFrameLayout
import com.google.android.wearable.input.RotaryEncoderHelper
import com.vlad1m1r.watchface.BuildConfig
import com.vlad1m1r.watchface.R
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private lateinit var scrollView: ScrollView
    private lateinit var versionName: TextView

    private val callback = object : SwipeDismissFrameLayout.Callback() {
        override fun onSwipeStarted(layout: SwipeDismissFrameLayout) {}

        override fun onSwipeCanceled(layout: SwipeDismissFrameLayout) {}

        override fun onDismissed(layout: SwipeDismissFrameLayout) {
            requireActivity().onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = SwipeDismissFrameLayout(activity).apply {
            inflater.inflate(
                R.layout.fragment_about,
                this,
                false
            ).also { inflatedView ->
                addView(inflatedView)
            }
            addCallback(callback)
        }

        scrollView = view.findViewById(R.id.scroll_view)
        versionName = view.findViewById(R.id.version_name)
        val versionText = "v${BuildConfig.VERSION_NAME}"
        versionName.text = versionText

        val gitHub = view.findViewById<TextView>(R.id.github_view)
        val email = view.findViewById<TextView>(R.id.email_view)
        val playStore = view.findViewById<TextView>(R.id.play_store_view)

        gitHub.setOnClickListener { openUri(getString(R.string.data_github_url)) }
        email.setOnClickListener { openUri("mailto:${getString(R.string.data_email)}") }
        playStore.setOnClickListener { openUri(getString(R.string.data_play_store_url)) }

        view.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_SCROLL && RotaryEncoderHelper.isFromRotaryEncoder(
                        event
                    )
                ) {
                    val delta =
                        -RotaryEncoderHelper.getRotaryAxisValue(event) * RotaryEncoderHelper.getScaledScrollFactor(
                            requireContext()
                        )
                    scrollView.scrollBy(0, delta.roundToInt())
                    return false
                }
                return false
            }
        })
        return view
    }

    private fun openUri(uri: String) {
        val intentConfirmationActivity = Intent(requireContext(), ConfirmationActivity::class.java)
        intentConfirmationActivity.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.OPEN_ON_PHONE_ANIMATION)
        startActivity(intentConfirmationActivity)

        val intent = Intent(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(Uri.parse(uri))
       // RemoteIntent.startRemoteActivity(this, intent, null)
    }
}