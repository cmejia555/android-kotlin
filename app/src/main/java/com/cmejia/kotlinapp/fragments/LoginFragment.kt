package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.User
import com.cmejia.kotlinapp.models.UserViewModel
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {

    private val viewModel : UserViewModel by activityViewModels()

    private lateinit var v : View
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = view.findViewById(R.id.login_email_et)
        passwordEditText = view.findViewById(R.id.login_password_et)
        loginButton = view.findViewById(R.id.login_btn)
        signUpTextView = view.findViewById(R.id.sign_up_tv)
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                val user = authenticate(email, password)
                if (user != null) {
                    val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
                    it.findNavController().navigate(action)
                } else {
                    Snackbar.make(it, "The email or password is incorrect", Snackbar.LENGTH_SHORT).show()
                }
            }
            else {
                Snackbar.make(it, "Please complete all fields", Snackbar.LENGTH_SHORT).show()
            }
        }

        signUpTextView.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun authenticate(email : String, password : String) : User? {
        val users = viewModel.getUsers().value!!
        for(user in users) {
            if (user.email == email && user.password == password) return user
        }
        return null
    }

}
