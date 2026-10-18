package com.riddwan.ai

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import android.os.StrictMode
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

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

        val question = EditText(this)
        question.hint = "Ketik pertanyaan atau soal..."
        question.setHintTextColor(Color.GRAY)
        question.setTextColor(white)
        question.textSize = 16f
        question.minLines = 4
        question.gravity = Gravity.TOP
        question.setPadding(16, 16, 16, 16)
        question.setBackgroundColor(Color.rgb(35, 28, 52))
        root.addView(
            question,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        val button = Button(this)
        button.text = "ANALISIS SOAL"
        button.setTextColor(white)
        button.setBackgroundColor(Color.rgb(115, 50, 190))
        root.addView(button)

        val resultTitle = TextView(this)
        resultTitle.text = "HASIL JAWABAN"
        resultTitle.textSize = 18f
        resultTitle.typeface = Typeface.DEFAULT_BOLD
        resultTitle.setTextColor(purple)
        resultTitle.setPadding(0, 24, 0, 8)
        root.addView(resultTitle)

        val result = TextView(this)
        result.text = "Jawaban AI akan muncul di sini."
        result.textSize = 15f
        result.setTextColor(white)
        root.addView(result)

        button.setOnClickListener {
            val soal = question.text.toString().trim()

            if (soal.isEmpty()) {
                result.text = "Tulis soal terlebih dahulu!"
                return@setOnClickListener
            }

            button.isEnabled = false
            result.text = "Riddwan AI sedang berpikir..."

            thread {
                val jawaban = try {
                    val url = URL(
                        "https://script.google.com/macros/s/AKfycbwVvhOpd1VFlTvGIQSeDNo8YGuI9iwQ0ciBUdZ8gryVe8pTwmXG_dcXQIwo6B4HeXncSA/exec"
                    )

                    val connection =
                        url.openConnection() as HttpURLConnection

                    connection.requestMethod = "POST"
                    connection.connectTimeout = 20000
                    connection.readTimeout = 60000
                    connection.doOutput = true
                    connection.instanceFollowRedirects = true
                    connection.setRequestProperty(
                        "Content-Type",
                        "application/x-www-form-urlencoded; charset=UTF-8"
                    )

                    val data = "soal=" + java.net.URLEncoder.encode(
                        soal,
                        "UTF-8"
                    ) + "&mapel=" + java.net.URLEncoder.encode(
                        "Umum",
                        "UTF-8"
                    )

                    connection.outputStream.use {
                        it.write(data.toByteArray(Charsets.UTF_8))
                    }

                    val stream = if (connection.responseCode in 200..399) {
                        connection.inputStream
                    } else {
                        connection.errorStream
                    }

                    val response = stream.bufferedReader().use {
                        it.readText()
                    }

                    connection.disconnect()

                    response
                } catch (e: Exception) {
                    "Gagal terhubung: ${e.message}"
                }

                runOnUiThread {
                    result.text = jawaban
                    button.isEnabled = true
                }
            }
        }

        setContentView(root)
    }
}
