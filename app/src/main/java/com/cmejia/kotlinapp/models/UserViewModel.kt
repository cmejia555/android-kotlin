package com.cmejia.kotlinapp.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmejia.kotlinapp.entities.User

class UserViewModel : ViewModel() {

    private var usersAsList : MutableList<User> = ArrayList()
    private var users = MutableLiveData<List<User>>()

    init {
        users.value = loadUsers()
    }

    private fun loadUsers() : List<User> {
        usersAsList.add(User("cmejia", "cmejia@gmail.com", "abcd"))
        usersAsList.add(User("cesar", "cesar@yahoo.com", "1234"))
        usersAsList.add(User("cesarmejia", "cesarmejia@outlook.com", "asdf"))

        return usersAsList
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    fun addUser(user : String, email : String, pass : String) {
        usersAsList.add(User(user, email, pass))
        users.value = usersAsList
    }
}