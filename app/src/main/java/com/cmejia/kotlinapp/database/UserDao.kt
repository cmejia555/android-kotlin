package com.cmejia.kotlinapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.cmejia.kotlinapp.entities.User

@Dao
interface UserDao {

    @Query("SELECT * from users_table ORDER BY id")
    fun getAll() : LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user : User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM users_table WHERE id = :id")
    fun get(id : Int) : User?
}