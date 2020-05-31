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
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car
import com.cmejia.kotlinapp.models.CarsViewModel
import com.cmejia.kotlinapp.models.DetailsViewModels
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


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
            inflateMenu(R.menu.menu_details_toolbar)
            setOnMenuItemClickListener {
                val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                when(it.itemId) {
                    R.id.action_edit -> {
                        if (preferences.getBoolean("edit", false)) {
                            view.findNavController().navigate(R.id.editDialogFragment)
                        } else {
                            Snackbar.make(view,
                                "Debe habilitar los permisos para realizar esta accion",
                                Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    R.id.action_delete -> {
                        if (preferences.getBoolean("delete", false)) {
                            view.findNavController().navigate(R.id.deleteDialogFragment)
                        } else {
                            Snackbar.make(view,
                                "Debe habilitar los permisos para realizar esta accion",
                                Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                true
            }
        }
        detailsViewModels.setAction(DetailsViewModels.DialogState.UNPRESSED)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailsViewModels.itemSelected.observe(viewLifecycleOwner, Observer { item ->
            car = item
            updateUI(item)
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
                inflateMenu(R.menu.menu_details_toolbar)
            }
        }
    }

    private fun updateUI(car : Car) {
        loadImage(car.imageUrl)
        carBrand.text = getString(R.string.car_brand, car.brand)
        carModel.text = getString(R.string.car_model, car.model)
        carYear.text = getString(R.string.car_year, car.year)
        carDescription.text = getString(R.string.car_description, car.description)

    }

    private fun loadImage(imageUrl : String) {
        //firebaseStorage = Firebase.storage("gs://cars-555.appspot.com")
        if (imageUrl.isNotEmpty()) {
            val reference : StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
            Glide.with(carImage)
                .load(reference)
                .circleCrop()
                .placeholder(R.drawable.download_image)
                .into(carImage)

        } else carImage.setImageResource(R.drawable.image_not_available)
    }

}
