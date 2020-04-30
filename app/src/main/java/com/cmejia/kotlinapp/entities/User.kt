package com.cmejia.kotlinapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val userId : Int,

    @ColumnInfo(name = "user_name")
    val userName : String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "password")
    val password : String
)