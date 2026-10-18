package com.riddwan.ai

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*

class MainActivity : Activity() {

    private val bg = Color.rgb(12, 10, 25)
    private val purple = Color.rgb(180, 100, 255)
    private val white = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = bg
        window.navigationBarColor = bg

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setPadding(24, 32, 24, 24)
        root.setBackgroundColor(bg)

        val title = TextView(this)
        title.text = "RIDDWAN AI"
        title.textSize = 30f
        title.setTextColor(purple)
        title.typeface = Typeface.DEFAULT_BOLD
        title.gravity = Gravity.CENTER
        root.addView(title)

        val subtitle = TextView(this)
        subtitle.text = "YOUR PERSONAL AI STUDY ASSISTANT"
        subtitle.textSize = 12f
        subtitle.setTextColor(Color.LTGRAY)
        subtitle.gravity = Gravity.CENTER
        subtitle.setPadding(0, 8, 0, 30)
        root.addView(subtitle)

        val label = TextView(this)
        label.text = "Tanyakan sesuatu, bro!"
        label.textSize = 18f
        label.setTextColor(white)
        root.addView(label)

        val question = EditText(this)
        question.hint = "Ketik pertanyaan atau soal..."
        question.setHintTextColor(Color.GRAY)
        question.setTextColor(white)
        question.textSize = 16f
        question.gravity = Gravity.TOP
        question.minLines = 4
        question.setPadding(16, 16, 16, 16)
        question.setBackgroundColor(Color.rgb(35, 28, 52))

        root.addView(
            question,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 12
            }
        )

        val button = Button(this)
        button.text = "ANALISIS SOAL"
        button.setTextColor(white)
        button.setBackgroundColor(Color.rgb(115, 50, 190))

        root.addView(
            button,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 16
            }
        )

        val resultTitle = TextView(this)
        resultTitle.text = "HASIL"
        resultTitle.textSize = 18f
        resultTitle.typeface = Typeface.DEFAULT_BOLD
        resultTitle.setTextColor(purple)
        resultTitle.setPadding(0, 28, 0, 8)
        root.addView(resultTitle)

        val result = TextView(this)
        result.text = "Jawaban akan muncul di sini."
        result.textSize = 15f
        result.setTextColor(white)
        root.addView(result)

        button.setOnClickListener {
            val text = question.text.toString().trim()

            result.text = if (text.isEmpty()) {
                "Tulis pertanyaan terlebih dahulu, bro."
            } else {
                "Pertanyaan lu:\n\n$text\n\nAI belum terhubung. Koneksi AI akan kita siapkan setelah APK berhasil dibangun."
            }
        }

        setContentView(root)
    }
}
