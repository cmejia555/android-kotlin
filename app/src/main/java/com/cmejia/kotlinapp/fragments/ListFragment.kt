package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
    private lateinit var adapter : RecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFloatingButton = view.findViewById(R.id.add_floating_btn)
        adapter = RecyclerViewAdapter(carViewModel.getCars()) { position : Int ->
            view.findNavController().navigate(
                ListFragmentDirections.actionListFragmentToDetailsFragment(position)
            )
        }

        recyclerView = view.findViewById(R.id.recyclerview_list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        addFloatingButton.setOnClickListener {
            carViewModel.addCar(Car("Empty", "Empty", 0, imageId =  R.drawable.image_not_available))
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar, menu)
        //(activity as AppCompatActivity).supportActionBar?.title = getString(R.string.my_movies)
        //(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_search -> Snackbar.make(list_layout, "Pressed Search", Snackbar.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
