package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.User
import com.cmejia.kotlinapp.models.UserViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private val viewModel : UserViewModel by activityViewModels()

    private lateinit var v : View
    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_sign_up, container, false)

        fullNameEditText = v.findViewById(R.id.full_name_et)
        emailEditText = v.findViewById(R.id.sign_up_email_et)
        passwordEditText = v.findViewById(R.id.sign_up_password_et)
        registerButton = v.findViewById(R.id.register_btn)

        return v
    }

    override fun onStart() {
        super.onStart()

        registerButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString() 
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (fullName.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                if (!isUserRegistered(email)) {
                    val newUser = User(fullName, email, password)
                    viewModel.addUser(newUser)
                    val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                    it.findNavController().navigate(action)
                } else {
                    Snackbar.make(it, "The email already exists", Snackbar.LENGTH_SHORT).show()
                }
            }
            else {
                Snackbar.make(it, "Please complete all fields", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun isUserRegistered(byEmail : String) : Boolean {
        val users = viewModel.getUsers().value!!
        for(user in users) {
            if (user.email == byEmail) return true
        }
        return false
    }

}
