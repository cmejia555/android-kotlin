package com.cmejia.kotlinapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars_table")
data class Car(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var carId : Long = -1,

    @ColumnInfo(name = "brand")
    var brand : String,

    @ColumnInfo(name = "model")
    var model : String,

    @ColumnInfo(name = "year")
    var year : Int,

    @ColumnInfo(name = "image_id")
    var imageId : Int? = null,

    @ColumnInfo(name = "description")
    var description : String = ""
)