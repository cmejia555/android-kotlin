package com.cmejia.kotlinapp.fragments

import android.content.Intent
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
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
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignIn: SignInButton

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
        googleSignIn = view.findViewById(R.id.google_sign_in)

        val toolbar : Toolbar = requireActivity().findViewById(R.id.toolbar)
        val navController = view.findNavController()
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.loginFragment, R.id.listFragment))
        toolbar.setupWithNavController(navController, appBarConfiguration)
        (activity as AppCompatActivity).setupActionBarWithNavController(navController, appBarConfiguration)

        getCollection("Users")
        auth = Firebase.auth
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getAllUsers().observe(viewLifecycleOwner, Observer {
            Log.d("DEBUG", "ESTOY EN LOGIN ${it.size}")
        })
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        updateUI(currentUser)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                firebaseAuthWithEmailAndPass(email, password)
                /*if (firebaseAuthentication(email, password)) {
                    val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
                    it.findNavController().navigate(action)
                } else {
                    Snackbar.make(it, "The email or password is incorrect", Snackbar.LENGTH_SHORT).show()
                }*/
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

        setupSignInWithGoogle()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    Log.d("GOOGLE SIGN IN", "firebaseAuthWithGoogle: ${account.id}")
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e : ApiException) {
                Log.w("ERROR", "Google sign in failed", e)
            }
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
                addSnapshot(collection)
            }
            .addOnFailureListener {
                view?.let { Snackbar.make(it, "Error getting data", Snackbar.LENGTH_SHORT).show() }
            }
    }
    
    private fun addSnapshot(collection: String) {
        db.collection(collection)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w("ERRORR SNAPSHOT", "Listen failed.", error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    users = snapshot.toObjects<User>()
                }
            }
    }

    private fun firebaseAuthentication(email : String, password : String) : Boolean {
        for(user in users) {
            if (user.email == email && user.password == password) return true
        }
        return false
    }

    private fun firebaseAuthWithEmailAndPass(email : String, password : String) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("AUTHHHH", "signInWithEmail:success")
                val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
                view?.findNavController()?.navigate(action)
            }
            .addOnFailureListener {
                Log.d("AUTHHHH", "signInWithEmail:failed ${it.message}")
                view?.let { it -> Snackbar.make(it, "The email or password is incorrect", Snackbar.LENGTH_SHORT).show() }
            }
    }

    private fun updateUI(user : FirebaseUser?) {
        if (user != null) {
            emailEditText.setText(user.email)
            //passwordEditText.setText(user)
        } else {
            emailEditText.setText("")
            passwordEditText.setText("")
        }
    }

    private fun setupSignInWithGoogle() {
        val googleConfiguration = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignIn.setOnClickListener {
            val googleClient = GoogleSignIn.getClient(requireActivity(), googleConfiguration)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    companion object {
        private const val GOOGLE_SIGN_IN = 91
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                Log.d("GOOGLE SIGN ING", "signInWithCredential:success")
                val user = auth.currentUser
                updateUI(user)
                val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
                view?.findNavController()?.navigate(action)
            }
            .addOnFailureListener {
                Log.d("GOOGLE SIGN ING", "signInWithEmail:failure ${it.message}")
                view?.let { it -> Snackbar.make(it, "The email or password is incorrect", Snackbar.LENGTH_SHORT).show() }
            }
    }
}
