package com.cmejia.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton

class MainActivity : AppCompatActivity() {

    lateinit var messageTextView : TextView
    lateinit var upSizeButton: Button
    lateinit var downSizeButton: Button
    lateinit var colorButton: Button
    lateinit var toggleButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageTextView = findViewById(R.id.tv_message)
        upSizeButton = findViewById(R.id.btn_up)
        downSizeButton = findViewById(R.id.btn_down)
        colorButton = findViewById(R.id.btn_change_color)
        toggleButton = findViewById(R.id.btn_toggle)

        messageTextView.text = "Bienvenido"

        var size : Float = messageTextView.textSize

        upSizeButton.setOnClickListener {
            size += 1
            messageTextView.textSize = size
        }

        downSizeButton.setOnClickListener {
            size -= 1
            messageTextView.textSize = size
        }
    }
}
