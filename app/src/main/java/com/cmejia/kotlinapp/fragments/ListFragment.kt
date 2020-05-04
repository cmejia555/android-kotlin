package com.cmejia.kotlinapp.fragments

import android.os.Bundle
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
import com.cmejia.kotlinapp.models.CarsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private val carViewModel : CarsViewModel by activityViewModels()

    private lateinit var addFloatingButton : FloatingActionButton
    private lateinit var recyclerView : RecyclerView
    private lateinit var viewAdapter : RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFloatingButton = view.findViewById(R.id.add_floating_btn)
        viewAdapter = RecyclerViewAdapter { position : Int ->
            carViewModel.itemSelected.value = position
            view.findNavController().navigate(
                ListFragmentDirections.actionListFragmentToCollectionFragment()
            )
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
        }

        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        carViewModel.getAllCars().observe(viewLifecycleOwner, Observer { list ->
            viewAdapter.carList = list
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        addFloatingButton.setOnClickListener {
            carViewModel.insertCar(Car(0, "Empty", "Empty", 0, imageId =  R.drawable.image_not_available))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_search -> Snackbar.make(list_layout, "Pressed Search", Snackbar.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
