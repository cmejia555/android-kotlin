package com.cmejia.kotlinapp.entities

data class Car(
    val brand : String,
    val model : String,
    val year : Int,
    val type : String? = "",
    val imageId : Int? = 0
)