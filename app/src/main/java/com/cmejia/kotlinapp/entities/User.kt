package com.cmejia.kotlinapp.entities

class User (username : String, email: String, password : String) {

    val username = username
    val email : String = email
    val password: String = password

    override fun toString(): String {
        return "Usuario creado: user = $username, email = $email"
    }
}