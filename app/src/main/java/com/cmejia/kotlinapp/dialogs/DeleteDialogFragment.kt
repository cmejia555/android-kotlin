package com.cmejia.kotlinapp.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.models.DetailsViewModels
import com.github.nikartm.button.FitButton


class DeleteDialogFragment : DialogFragment() {

    lateinit var acceptButton: FitButton
    lateinit var cancelButton: FitButton

    private val detailsViewModel : DetailsViewModels by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delete_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        acceptButton = view.findViewById(R.id.dialog_accept_btn)
        cancelButton = view.findViewById(R.id.dialog_cancel_btn)
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

        acceptButton.setOnClickListener {
            detailsViewModel.setAction(DetailsViewModels.DialogState.DELETE_ITEM)
            dismiss()
        }
    }

}
