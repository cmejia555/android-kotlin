package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car
import com.cmejia.kotlinapp.models.CarsViewModel


class DetailsFragment : Fragment() {

    lateinit var v : View
    lateinit var carImage : ImageView
    lateinit var carBrand : TextView
    lateinit var carModel : TextView
    lateinit var carYear : TextView
    lateinit var carDescription : TextView

    private val carsViewModel : CarsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carImage = view.findViewById(R.id.car_image)
        carBrand = view.findViewById(R.id.car_brand)
        carModel = view.findViewById(R.id.car_model)
        carYear = view.findViewById(R.id.car_year)
        carDescription = view.findViewById(R.id.car_description)
    }

    override fun onStart() {
        super.onStart()

        val position = DetailsFragmentArgs.fromBundle(requireArguments()).position
        updateUi(position)
    }

    private fun updateUi(position : Int) {
        val car : Car? = carsViewModel.getCar(position)

        if (car != null) {
            carImage.setImageResource(car.imageId!!)
            carBrand.text = getString(R.string.car_brand, car.brand)
            carModel.text = getString(R.string.car_model, car.model)
            carYear.text = getString(R.string.car_year, car.year)
            carDescription.text = getString(R.string.car_description, car.description)
        }
    }

}
