package com.cmejia.kotlinapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars_table")
data class Car(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val carId : Int,

    @ColumnInfo(name = "brand")
    val brand : String,

    @ColumnInfo(name = "model")
    val model : String,

    @ColumnInfo(name = "year")
    val year : Int,

    @ColumnInfo(name = "image_id")
    val imageId : Int? = 0,

    @ColumnInfo(name = "description")
    val description : String = ""
)