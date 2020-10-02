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

    @ColumnInfo(name = "image_url")
    var imageUrl : String,

    @ColumnInfo(name = "description")
    var description : String = "",

    @ColumnInfo(name = "latitude")
    var latitude : Double = 0.0,

    @ColumnInfo(name = "longitude")
    var longitude : Double = 0.0
) {
    constructor() : this(brand = "", model = "", year = 0, imageUrl =  "", latitude = 0.0, longitude = 0.0)
}