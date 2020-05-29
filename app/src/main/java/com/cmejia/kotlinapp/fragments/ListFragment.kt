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
import com.cmejia.kotlinapp.models.DetailsViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private val carViewModel : CarsViewModel by activityViewModels()
    private val detailsViewModels : DetailsViewModels by activityViewModels()

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
            inflateMenu(R.menu.menu_recycler_view_toolbar)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menu_search -> Snackbar.make(list_layout, "Pressed Search", Snackbar.LENGTH_SHORT).show()
                }
                true
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        carViewModel.getAllCars().observe(viewLifecycleOwner, Observer { list ->
            viewAdapter.carList = list
        })
    }

    override fun onStart() {
        super.onStart()
        addFloatingButton.setOnClickListener {
            carViewModel.insertCar(Car( brand = "Empty", model = "Empty", year = 0, imageUrl =  ""))
        }
    }

}
