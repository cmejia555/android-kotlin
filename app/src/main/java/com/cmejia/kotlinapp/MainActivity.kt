package com.cmejia.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cmejia.kotlinapp.clases.User


class MainActivity : AppCompatActivity() {

    lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user = User("cmejia", "cmejia@google.com", "abcd")

    }
}
