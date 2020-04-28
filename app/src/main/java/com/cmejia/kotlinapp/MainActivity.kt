package com.cmejia.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val navController = findNavController(R.id.main_navhost)
        //val appBarConfiguration = AppBarConfiguration(navController.graph)
        //val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //toolbar.setupWithNavController(navController, appBarConfiguration)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
}
