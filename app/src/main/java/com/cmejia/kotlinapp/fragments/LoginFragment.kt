package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.models.UserViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private var viewModel : UserViewModel = UserViewModel()

    lateinit var v : View
    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button
    lateinit var signUpTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false)

        usernameEditText = v.findViewById(R.id.username_et)
        passwordEditText = v.findViewById(R.id.password_et)
        loginButton = v.findViewById(R.id.login_btn)
        signUpTextView = v.findViewById(R.id.sign_up_tv)

        return v
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                if (isUserValid(username, password)) {
                    val action = LoginFragmentDirections.actionLoginFragmentToInfoFragment(username)
                    v.findNavController().navigate(action)
                } else {
                    Snackbar.make(v, "The username or password is incorrect", Snackbar.LENGTH_SHORT).show()
                }
            }
            else {
                Snackbar.make(v, "Complete all fields", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun isUserValid(username : String, password : String) : Boolean {
        val items = viewModel.getUsers().value!!
        for(item in items) {
            if (item.username == username && item.password == password) return true
        }
        return false
    }

}
