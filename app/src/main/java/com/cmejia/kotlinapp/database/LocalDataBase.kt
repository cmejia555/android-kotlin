package com.cmejia.kotlinapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cmejia.kotlinapp.entities.Car
import com.cmejia.kotlinapp.entities.User

@Database(entities = [User::class, Car::class], version = 1, exportSchema = false)
abstract class LocalDataBase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun carDao() : CarDao

    companion object {
        @Volatile
        private var INSTANCE : LocalDataBase? = null

        fun getInstance(context: Context): LocalDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDataBase::class.java,
                    "mydatabase"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}