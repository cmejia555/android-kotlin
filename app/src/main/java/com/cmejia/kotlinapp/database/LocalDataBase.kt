package com.cmejia.kotlinapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car
import com.cmejia.kotlinapp.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class, Car::class], version = 1, exportSchema = false)
abstract class LocalDataBase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun carDao() : CarDao

    companion object {
        @Volatile
        private var INSTANCE : LocalDataBase? = null

        fun getInstance(context: Context, scope: CoroutineScope): LocalDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDataBase::class.java,
                    "mydatabase"
                )
                    .allowMainThreadQueries()
                    //.addCallback(DataBaseCallback(scope))
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DataBaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    Log.d("ERRORRR", "EERRRRRRRRORRRRr")
                    loadDatabase(database.userDao())
                    loadDatabase(database.carDao())
                }
            }
        }

        suspend fun loadDatabase(userDao: UserDao) {
            userDao.insert(User(0,"cesar mejia", "cmejia@gmail.com", "1234"))
            userDao.insert(User(1,"octavio", "octavio@yahoo.com", "1234"))
            userDao.insert(User(2,"jose luis mejia", "joseluis@outlook.com", "1234"))
        }

        suspend fun loadDatabase(carDao: CarDao) {
            carDao.insert(Car(0, "Chevrolet", "Meriva", 2010, imageId = R.drawable.meriva))
            carDao.insert(Car(1, "Peugeot", "206", 2014, imageId = R.drawable.peugeot_206))
            carDao.insert(Car(2, "Ford", "Focus", 2017, imageId = R.drawable.focus))
        }
    }
}