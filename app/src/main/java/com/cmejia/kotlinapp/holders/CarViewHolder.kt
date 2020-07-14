package com.cmejia.kotlinapp.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmejia.kotlinapp.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class CarViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val rootLayout : CardView = view.findViewById(R.id.card_layout)
    val modelTextView : TextView = view.findViewById(R.id.item_car_model)
    private val image : ImageView = view.findViewById(R.id.item_car_image)
    private val storage: FirebaseStorage = Firebase.storage

    fun setImage(value : String) {
        if (value.isNotEmpty()) {
            val reference : StorageReference = storage.getReferenceFromUrl(value)
            Glide.with(rootLayout)
                .load(reference)
                .fitCenter()
                .placeholder(R.drawable.download_image)
                .error(R.drawable.image_not_available)
                .into(image)
        } else {
            image.setImageResource(R.drawable.image_not_available)
        }
    }
}