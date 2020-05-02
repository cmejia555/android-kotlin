package com.cmejia.kotlinapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cmejia.kotlinapp.entities.Car

@Dao
interface CarDao {

    @Query("SELECT * from cars_table ORDER BY id ASC")
    fun getAll() : LiveData<List<Car>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(car : Car)

    @Update
    fun update(car: Car)

    @Delete
    fun delete(car: Car)

    @Query("SELECT * FROM cars_table WHERE id = :id")
    fun get(id : Int) : Car?
}