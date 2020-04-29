package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car
import com.cmejia.kotlinapp.models.CarsViewModel
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {

    lateinit var v : View
    lateinit var carImage : ImageView
    lateinit var carBrand : TextView
    lateinit var carModel : TextView
    lateinit var carYear : TextView
    lateinit var carDescription : TextView

    lateinit var car: Car
    private val carsViewModel : CarsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_details, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carImage = view.findViewById(R.id.car_image)
        carBrand = view.findViewById(R.id.car_brand)
        carModel = view.findViewById(R.id.car_model)
        carYear = view.findViewById(R.id.car_year)
        carDescription = view.findViewById(R.id.car_description)

        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        val position = DetailsFragmentArgs.fromBundle(requireArguments()).position
        updateUi(position)
    }

    private fun updateUi(position : Int) {
        car = carsViewModel.getCar(position)!!

        carImage.setImageResource(car.imageId!!)
        carBrand.text = getString(R.string.car_brand, car.brand)
        carModel.text = getString(R.string.car_model, car.model)
        carYear.text = getString(R.string.car_year, car.year)
        carDescription.text = getString(R.string.car_description, car.description)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_edit -> Snackbar.make(v, "Pressed Edit", Snackbar.LENGTH_SHORT).show()
            R.id.action_delete -> {
                Snackbar.make(v, "Pressed Delete", Snackbar.LENGTH_SHORT).show()
                carsViewModel.deleteCar(car)
                v.findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToListFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
