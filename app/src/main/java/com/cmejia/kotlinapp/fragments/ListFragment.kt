package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.adapters.FirebaseRecycleViewAdapter
import com.cmejia.kotlinapp.adapters.RecyclerViewAdapter
import com.cmejia.kotlinapp.entities.Car
import com.cmejia.kotlinapp.models.CarsViewModel
import com.cmejia.kotlinapp.models.DetailsViewModels
import com.cmejia.kotlinapp.models.UserAuthViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private val carViewModel : CarsViewModel by activityViewModels()
    private val detailsViewModels : DetailsViewModels by activityViewModels()
    private val userAuthViewModel : UserAuthViewModel by activityViewModels()

    private lateinit var addFloatingButton : FloatingActionButton
    private lateinit var recyclerView : RecyclerView
    private lateinit var viewAdapter : RecyclerViewAdapter
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFloatingButton = view.findViewById(R.id.add_floating_btn)
        viewAdapter = RecyclerViewAdapter { car : Car ->
            detailsViewModels.itemSelected.value = car
            view.findNavController().navigate(
                ListFragmentDirections.actionListFragmentToCollectionFragment()
            )
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            //adapter = viewAdapter
            adapter = getRecycleViewAdapter()
        }

        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.toolbar).apply {
            if (menu.hasVisibleItems()) {
                menu.clear()
            }
            inflateMenu(R.menu.menu_list_toolbar)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menu_search -> Snackbar.make(list_layout, "Pressed Search", Snackbar.LENGTH_SHORT).show()
                    R.id.menu_setting -> view.findNavController().navigate(ListFragmentDirections.actionListFragmentToSettingsFragment())
                }
                true
            }
        }

        setDrawerNavigationOptions()


        //getCollection("Cars")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        carViewModel.getAllCars().observe(viewLifecycleOwner, Observer { list ->
            //viewAdapter.carList = list
        })
        userAuthViewModel.authStatus.observe(viewLifecycleOwner, Observer {
            updateProfileUI(it)
        })
    }

    private fun setDrawerNavigationOptions() {
        val navView = requireActivity().findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    userAuthViewModel.authStatus.value = UserAuthViewModel.Authentication.UNAUTHENTICATED
                    Firebase.auth.signOut()
                    requireActivity().drawer_layout.closeDrawer(GravityCompat.START)
                    view?.findNavController()?.popBackStack(R.id.loginFragment, false)
                }
            }
            true
        }

    }

    private fun updateProfileUI(status : UserAuthViewModel.Authentication) {
        if (status == UserAuthViewModel.Authentication.AUTHENTICATED) {
            val user = Firebase.auth.currentUser
            requireActivity().findViewById<TextView>(R.id.user_auth_name)
                .text = getString(R.string.drawer_header_name_text, user?.displayName)
            val image = requireActivity().findViewById<ImageView>(R.id.user_auth_photo)
            Glide.with(image)
                .load(user?.photoUrl)
                .circleCrop()
                .into(image)
        }
    }

    override fun onStart() {
        super.onStart()
        addFloatingButton.setOnClickListener {
            val emptyCar = Car( brand = "Empty", model = "Empty", year = 0, imageUrl =  "")
            carViewModel.insertCar(emptyCar)
            addCar(emptyCar)
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.toolbar).apply {
            if (menu.hasVisibleItems()) {
                menu.clear()
            }
        }
    }

    private fun setCollection(collection : String) {
        val cars = carViewModel.getAllCars().value!!
        for (car in cars) {
            db.collection(collection)
                .document(car.carId.toString())
                .set(car)
                .addOnSuccessListener {
                    Log.d("EXITOSOS", "SE SUBIOOOOOO EL CAR")

                }
                .addOnFailureListener {
                    Log.d("FAILUREEEE", "ERORRR UP ${it.message}")
                }
        }
    }

    private fun getCollection(collection: String) {
        db.collection(collection)
            .orderBy("carId")
            .get()
            .addOnSuccessListener {
                viewAdapter.carList = it.toObjects<Car>()
                addSnapshot(collection)
            }
            .addOnFailureListener {
                view?.let { Snackbar.make(it, "Error getting data", Snackbar.LENGTH_SHORT).show() }
            }
    }

    private fun addCar(car : Car) {
        db.collection("Cars").document(car.carId.toString()).set(car)
    }

    private fun addSnapshot(collection: String) {
        db.collection(collection)
            .orderBy("carId")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w("ERRORR SNAPSHOT", "Listen failed.", error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    Log.d("SNAPSHOT", "LISTEN")
                    viewAdapter.carList = snapshot.toObjects<Car>()
                }
            }
    }

    private val onClickListenerAdapter = { car : Car ->
        detailsViewModels.itemSelected.value = car
        view?.findNavController()?.navigate(
            ListFragmentDirections.actionListFragmentToCollectionFragment()
        )
    }

    private fun getRecycleViewAdapter() : FirebaseRecycleViewAdapter {
        val rootRef = FirebaseFirestore.getInstance()
        val query = rootRef.collection("Cars").orderBy("carId")
        val options = FirestoreRecyclerOptions.Builder<Car>()
            .setQuery(query, Car::class.java)
            .build()
        val firebaseAdapter = FirebaseRecycleViewAdapter(onClickListenerAdapter, options)

        firebaseAdapter.startListening()
        return firebaseAdapter
    }

}
