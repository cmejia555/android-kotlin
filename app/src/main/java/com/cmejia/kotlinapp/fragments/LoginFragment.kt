package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.models.UserViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private val viewModel : UserViewModel by activityViewModels()

    private lateinit var v : View
    private lateinit var fullNameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false)

        fullNameEditText = v.findViewById(R.id.fullName_et)
        passwordEditText = v.findViewById(R.id.password_et)
        loginButton = v.findViewById(R.id.login_btn)
        signUpTextView = v.findViewById(R.id.sign_up_tv)

        return v
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener {
            val username = fullNameEditText.text.toString()
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

        signUpTextView.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun isUserValid(username : String, password : String) : Boolean {
        val items = viewModel.getUsers().value!!
        for(item in items) {
            if (item.fullName == username && item.password == password) return true
        }
        return false
    }

}
