package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.adapters.CarListAdapter
import com.cmejia.kotlinapp.entities.Car
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private lateinit var addFloatingButton : FloatingActionButton
    private lateinit var carsRecyclerView : RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var listAdapter : CarListAdapter

    private var carList : MutableList<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFloatingButton = view.findViewById(R.id.add_floating_btn)
        layoutManager = LinearLayoutManager(context)
        carList.add(Car("Chevrolet", 2010, 1, "Full"))
        listAdapter = CarListAdapter(carList) { item : Int ->
            Snackbar.make(view, "Pressed position =$item", Snackbar.LENGTH_SHORT).show()
        }


        carsRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview).apply {
            layoutManager = layoutManager
            adapter = listAdapter
        }

    }

    override fun onStart() {
        super.onStart()
    }

    private fun onItemClick () {
        //Snackbar.make(v,"click", Snackbar.LENGTH_SHORT).show()
    }

}
