package com.cmejia.kotlinapp.entities

class User (fullName : String, email: String, password : String) {

    val fullName : String = fullName
    val email : String = email
    val password: String = password

    override fun toString(): String {
        return "User created: name = $fullName, email = $email"
    }
}