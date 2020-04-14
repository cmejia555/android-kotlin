package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.models.UserViewModel

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private val viewModel : UserViewModel by activityViewModels()

    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_sign_up, container, false)

        viewModel.addUser("cm", "cm", "cm")
        return v
    }

}
