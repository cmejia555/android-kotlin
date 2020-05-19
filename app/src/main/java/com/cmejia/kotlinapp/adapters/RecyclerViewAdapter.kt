package com.cmejia.kotlinapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class RecyclerViewAdapter(val adapterOnClick : (item: Car) -> Unit) :
                    RecyclerView.Adapter<RecyclerViewAdapter.CarViewHolder>() {

    private val storage: FirebaseStorage = Firebase.storage
    var carList : List<Car> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as View

        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = carList[position]
        holder.modelTextView.text = item.model

        holder.setImage(item.imageUrl)
        holder.rootLayout.setOnClickListener {
            adapterOnClick(item)
        }
    }

    override fun getItemCount() = carList.size

    internal fun setCars(carList: List<Car>) {
        this.carList = carList
        notifyDataSetChanged()
    }

    inner class CarViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val rootLayout : CardView = view.findViewById(R.id.card_layout)
        val modelTextView : TextView = view.findViewById(R.id.item_car_model)
        private val image : ImageView = view.findViewById(R.id.item_car_image)

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
}