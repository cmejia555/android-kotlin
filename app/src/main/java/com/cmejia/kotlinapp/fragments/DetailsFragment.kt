package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide


import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car
import com.cmejia.kotlinapp.models.CarsViewModel
import com.cmejia.kotlinapp.models.DetailsViewModels
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class DetailsFragment : Fragment() {

    private lateinit var v : View
    private lateinit var carImage : ImageView
    private lateinit var carBrand : TextView
    private lateinit var carModel : TextView
    private lateinit var carYear : TextView
    private lateinit var carDescription : TextView

    lateinit var car: Car
    private val carsViewModel : CarsViewModel by activityViewModels()
    private val detailsViewModels : DetailsViewModels by activityViewModels()
    private lateinit var firebaseStorage : FirebaseStorage

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

        requireActivity().findViewById<Toolbar>(R.id.toolbar).apply {
            if (menu.hasVisibleItems()) {
                menu.clear()
            }
            inflateMenu(R.menu.details_toolbar)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.action_edit -> {
                        view.findNavController().navigate(R.id.editDialogFragment)
                    }
                    R.id.action_delete -> {
                        view.findNavController().navigate(R.id.dialogFragment
                            //DetailsFragmentDirections.actionDetailsFragmentToDialogFragment()
                        )
                    }
                }
                true
            }
        }
        detailsViewModels.setAction(DetailsViewModels.DialogState.UNPRESSED)

        firebaseStorage = Firebase.storage("gs://cars-555.appspot.com")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailsViewModels.itemSelected.observe(viewLifecycleOwner, Observer { itemId ->
            updateUI(itemId)
        })

        detailsViewModels.actionStatus.observe(viewLifecycleOwner, Observer { action ->
            if (action == DetailsViewModels.DialogState.DELETE_ITEM) {
                carsViewModel.deleteCar(car)
                findNavController().popBackStack(R.id.listFragment, false)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<Toolbar>(R.id.toolbar).apply {
            if (!menu.hasVisibleItems()) {
                inflateMenu(R.menu.details_toolbar)
            }
        }
    }

    private fun updateUI(position : Long) {
        car = carsViewModel.getCar(position)!!

        loadImage("gs://cars-555.appspot.com/image_not_available.jpg")
        //carImage.setImageResource(car.imageId!!)
        carBrand.text = getString(R.string.car_brand, car.brand)
        carModel.text = getString(R.string.car_model, car.model)
        carYear.text = getString(R.string.car_year, car.year)
        carDescription.text = getString(R.string.car_description, car.description)

    }

    private fun loadImage(imageUrl : String) {
        val reference : StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
        //val reference : StorageReference = firebaseStorage.getReferenceFromUrl(imageUrl)
        Glide.with(this)
            .load(reference)
            .into(carImage)
    }

}
