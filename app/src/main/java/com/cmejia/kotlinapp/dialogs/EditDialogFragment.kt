package com.cmejia.kotlinapp.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car
import com.cmejia.kotlinapp.models.CarsViewModel
import com.cmejia.kotlinapp.models.DetailsViewModels
import com.github.nikartm.button.FitButton


class EditDialogFragment : DialogFragment() {

    private lateinit var brandEditText: EditText
    private lateinit var modelEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var cancelButton: FitButton
    private lateinit var saveButton: FitButton

    private val detailsViewModels : DetailsViewModels by activityViewModels()
    private val carsViewModel : CarsViewModel by activityViewModels()
    private lateinit var car : Car

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        brandEditText = view.findViewById(R.id.brand_dialog_input)
        modelEditText = view.findViewById(R.id.model_dialog_input)
        yearEditText = view.findViewById(R.id.year_dialog_input)
        descriptionEditText = view.findViewById(R.id.description_dialog_input)
        cancelButton = view.findViewById(R.id.dialog_cancel_button)
        saveButton = view.findViewById(R.id.dialog_save_btn)

        detailsViewModels.itemSelected.observe(viewLifecycleOwner, Observer {item ->
            showItemData(item)
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onStart() {
        super.onStart()

        cancelButton.setOnClickListener {
            dismiss()
        }

        saveButton.setOnClickListener {
            car.brand = brandEditText.text.toString()
            car.model = modelEditText.text.toString()
            car.year = yearEditText.text.toString().toInt()
            car.description = descriptionEditText.text.toString()

            carsViewModel.updateCar(car)
            detailsViewModels.itemSelected.value = car.carId
            dismiss()
        }

    }

    private fun showItemData(position : Long) {
        car = carsViewModel.getCar(position)!!
        brandEditText.setText(getString(R.string.brand_dialog_input, car.brand))
        modelEditText.setText(getString(R.string.model_dialog_input, car.model))
        yearEditText.setText(getString(R.string.year_dialog_input, car.year))
        descriptionEditText.setText(getString(R.string.description_dialog_input, car.description))
    }
}
