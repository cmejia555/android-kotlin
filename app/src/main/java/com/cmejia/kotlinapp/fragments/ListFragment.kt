package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.adapters.RecyclerViewAdapter
import com.cmejia.kotlinapp.models.CarsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


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
        adapter = RecyclerViewAdapter(carViewModel.getCars()) { item : Int ->
            Snackbar.make(view, "Pressed position =$item", Snackbar.LENGTH_SHORT).show()
        }

        recyclerView = view.findViewById(R.id.recyclerview_list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

    }

    override fun onStart() {
        super.onStart()
    }

    private fun onItemClick () {
        //Snackbar.make(v,"click", Snackbar.LENGTH_SHORT).show()
    }

}
