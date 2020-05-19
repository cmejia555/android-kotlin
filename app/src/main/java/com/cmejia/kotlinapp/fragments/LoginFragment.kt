package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.database.LocalDataBase
import com.cmejia.kotlinapp.models.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.wajahatkarim3.roomexplorer.RoomExplorer
import kotlinx.android.synthetic.main.fragment_login.*


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

        val toolbar : Toolbar = requireActivity().findViewById(R.id.toolbar)
        val navController = view.findNavController()
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.loginFragment, R.id.listFragment))
        toolbar.setupWithNavController(navController, appBarConfiguration)
        (activity as AppCompatActivity).setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getAllUsers().observe(viewLifecycleOwner, Observer {
            Log.d("DEBUG", "ESTOY EN LOGIN ${it.size}")
        })
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                if (authenticate(email, password)) {
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

        fab_database.setOnClickListener {
            RoomExplorer.show(context, LocalDataBase::class.java, "mydatabase")
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<Toolbar>(R.id.toolbar).apply {
            menu.clear()
        }
    }

    private fun authenticate(email : String, password : String) : Boolean {
        val users = viewModel.getAllUsers().value!!
        for(user in users) {
            if (user.email == email && user.password == password) return true
        }
        return false
    }

}
