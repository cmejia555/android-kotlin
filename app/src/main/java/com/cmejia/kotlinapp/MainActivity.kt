package com.cmejia.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.cmejia.kotlinapp.clases.User


class MainActivity : AppCompatActivity() {

    lateinit var messageTextView : TextView
    lateinit var upSizeButton: Button
    lateinit var downSizeButton: Button
    lateinit var toggleButton: Button
    lateinit var colorButton: Button

    lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageTextView = findViewById(R.id.tv_message)
        upSizeButton = findViewById(R.id.btn_up)
        downSizeButton = findViewById(R.id.btn_down)
        toggleButton = findViewById(R.id.btn_toggle)
        colorButton = findViewById(R.id.btn_change_color)

        user = User("cmejia", "cmejia@google.com", "abcd")

        messageTextView.text = user.username

        var size : Float = messageTextView.textSize

        upSizeButton.setOnClickListener {
            size += 1
            messageTextView.textSize = size
        }

        downSizeButton.setOnClickListener {
            size -= 1
            messageTextView.textSize = size
        }

        toggleButton.setOnClickListener {
            when (messageTextView.visibility){
                View.VISIBLE -> messageTextView.visibility = View.INVISIBLE
                View.INVISIBLE -> messageTextView.visibility = View.VISIBLE
                else -> messageTextView.visibility = View.GONE
            }
        }

        colorButton.setOnClickListener {
            messageTextView.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        }
    }
}
