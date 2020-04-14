package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.cmejia.kotlinapp.R

/**
 * A simple [Fragment] subclass.
 */
class InfoFragment : Fragment() {

    lateinit var v : View

    lateinit var infoTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_info, container, false)

        infoTextView = v.findViewById(R.id.info_tv)
        return v
    }

    override fun onStart() {
        super.onStart()

        val username = InfoFragmentArgs.fromBundle(arguments!!).user
        infoTextView.text = "Bienvenido: $username"
    }

}
