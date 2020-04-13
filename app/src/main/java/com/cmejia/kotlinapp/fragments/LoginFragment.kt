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

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

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
            val action = LoginFragmentDirections.actionLoginFragmentToInfoFragment()
            v.findNavController().navigate(action)
        }
    }

}
