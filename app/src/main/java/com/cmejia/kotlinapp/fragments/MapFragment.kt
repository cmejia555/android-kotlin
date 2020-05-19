package com.cmejia.kotlinapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.models.DetailsViewModels
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.synnapps.carouselview.CarouselView


class MapFragment : Fragment() {

    private lateinit var carouselView: CarouselView
    var images = arrayOf(
        "",
        "gs://cars-555.appspot.com/default.jpg"
    )

    private val detailsViewModels : DetailsViewModels by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carouselView = view.findViewById(R.id.carousel)
        carouselView.pageCount = images.size
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailsViewModels.itemSelected.observe(viewLifecycleOwner, Observer { item ->
            images[0] = item.imageUrl
            carouselView.setImageListener { position, imageView ->
                if (item.imageUrl.isNotEmpty()) {
                    val reference : StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(images[position])
                    Glide.with(carouselView)
                        .load(reference)
                        .fitCenter()
                        .placeholder(R.drawable.download_image)
                        .into(imageView)

                } else imageView.setImageResource(R.drawable.image_not_available)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<Toolbar>(R.id.toolbar).apply {
            menu.clear()
        }
        carouselView.playCarousel()
    }

}
