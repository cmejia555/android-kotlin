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
import com.cmejia.kotlinapp.entities.User
import com.cmejia.kotlinapp.models.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.wajahatkarim3.roomexplorer.RoomExplorer
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private val viewModel : UserViewModel by activityViewModels()

    private lateinit var v : View
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView
    private val db = Firebase.firestore
    private lateinit var users : List<User>

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
            getCollection("Users")
        })
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                if (firebaseAuthentication(email, password)) {
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

        fab_database.hide()
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

    private fun setCollection(collection : String) {
        val users = viewModel.getAllUsers().value!!
        for (user in users) {
            db.collection(collection)
                .document(user.userId.toString())
                .set(user)
                .addOnSuccessListener {
                    Log.d("EXITOSOS", "SE SUBIOOOOOO EL USER")

                }
                .addOnFailureListener {
                    Log.d("FAILUREEEE", "ERORRR UP ${it.message}")
                }
        }
    }

    private fun getCollection(collection: String) {
        db.collection(collection)
            .get()
            .addOnSuccessListener {
                users = it.toObjects<User>()
            }
            .addOnFailureListener {
                view?.let { Snackbar.make(it, "Error getting data", Snackbar.LENGTH_SHORT).show() }
            }
    }

    private fun firebaseAuthentication(email : String, password : String) : Boolean {
        for(user in users) {
            if (user.email == email && user.password == password) return true
        }
        return false
    }
}
