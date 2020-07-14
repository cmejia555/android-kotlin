package com.cmejia.kotlinapp.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserAuthViewModel() : ViewModel() {

    var authStatus : MutableLiveData<Authentication>

    enum class Authentication {
        AUTHENTICATED,
        UNAUTHENTICATED
    }

    init {
        authStatus = MutableLiveData(Authentication.AUTHENTICATED)
    }

}