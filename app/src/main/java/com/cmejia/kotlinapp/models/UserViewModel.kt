package com.cmejia.kotlinapp.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cmejia.kotlinapp.database.LocalDataBase
import com.cmejia.kotlinapp.database.UserDao
import com.cmejia.kotlinapp.entities.User


class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val allUsers : LiveData<List<User>>
    private var userDao : UserDao
    private var newUserId : Int = 0

    init {
        userDao = LocalDataBase.getInstance(application, viewModelScope).userDao()
        loadData()
        allUsers = userDao.getAll()
    }

    private fun loadData() {
        insertUser(User(0,"cesar mejia", "cmejia@gmail.com", "1234"))
        insertUser(User(1,"octavio", "octavio@yahoo.com", "1234"))
        insertUser(User(2,"jose luis mejia", "joseluis@outlook.com", "1234"))
    }

    fun getAllUsers() : LiveData<List<User>> {
        return allUsers
    }

    fun insertUser(user : User) {
        user.userId = newUserId
        userDao.insert(user)
        newUserId++
    }

    fun getUser(byEmail : String) : User? {
        return userDao.get(byEmail)
    }

    fun getUser(id : Int) : User? {
        return userDao.get(id)
    }
}