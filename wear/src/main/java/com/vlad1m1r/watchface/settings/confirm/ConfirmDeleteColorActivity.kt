package com.vlad1m1r.watchface.settings.confirm

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.vlad1m1r.watchface.R

const val KEY_CHOICE = "choice"
const val KEY_COLOR_TO_DELETE = "color_to_delete"

private const val KEY_TEXT = "text"
private const val KEY_COLOR = "color"

class ConfirmDeleteColorActivity : AppCompatActivity() {

    private lateinit var colorImageView: ImageView
    private lateinit var text: TextView
    private lateinit var confirmButton: ImageView
    private lateinit var declineButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val colorRes = intent.getIntExtra(KEY_COLOR, 0)
        val textRes = intent.getIntExtra(KEY_TEXT, 0)

        text = findViewById(R.id.text)
        colorImageView = findViewById(R.id.color)
        confirmButton = findViewById(R.id.button_confirm)
        declineButton = findViewById(R.id.button_decline)

        text = findViewById(R.id.text)
        text.setText(textRes)

        val drawable = (AppCompatResources.getDrawable(this, R.drawable.round_color_small) as GradientDrawable).apply {
            this.color = ColorStateList.valueOf(colorRes)
        }
        colorImageView.setImageDrawable(drawable)

        confirmButton.setOnClickListener {
            val data = Intent()
            data.putExtra(KEY_CHOICE, true)
            data.putExtra(KEY_COLOR_TO_DELETE, colorRes)
            setResult(RESULT_OK, data)
            finish()
        }
        declineButton.setOnClickListener {
            val data = Intent()
            data.putExtra(KEY_CHOICE, false)
            setResult(RESULT_OK, data)
            finish()
        }
    }

    companion object {
        fun newInstance(
            context: Context,
            @StringRes text: Int,
            @ColorInt color: Int
        ): Intent {
            return Intent(context, ConfirmDeleteColorActivity::class.java).apply {
                putExtra(KEY_TEXT, text)
                putExtra(KEY_COLOR, color)
            }
        }
    }
}