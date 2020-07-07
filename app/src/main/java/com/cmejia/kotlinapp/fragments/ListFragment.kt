package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.adapters.RecyclerViewAdapter
import com.cmejia.kotlinapp.entities.Car
import com.cmejia.kotlinapp.entities.User
import com.cmejia.kotlinapp.models.CarsViewModel
import com.cmejia.kotlinapp.models.DetailsViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private val carViewModel : CarsViewModel by activityViewModels()
    private val detailsViewModels : DetailsViewModels by activityViewModels()

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
            adapter = viewAdapter
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        carViewModel.getAllCars().observe(viewLifecycleOwner, Observer { list ->
            //viewAdapter.carList = list
            getCollection("Cars")
        })
    }

    override fun onStart() {
        super.onStart()
        addFloatingButton.setOnClickListener {
            carViewModel.insertCar(Car( brand = "Empty", model = "Empty", year = 0, imageUrl =  ""))
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
            }
            .addOnFailureListener {
                view?.let { Snackbar.make(it, "Error getting data", Snackbar.LENGTH_SHORT).show() }
            }
    }

}
