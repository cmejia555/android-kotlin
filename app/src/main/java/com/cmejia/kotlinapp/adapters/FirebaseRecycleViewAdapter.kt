package com.cmejia.kotlinapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car
import com.cmejia.kotlinapp.holders.CarViewHolder
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class FirebaseRecycleViewAdapter(val adapterOnClick : (item: Car) -> Unit?,
                                 options : FirestoreRecyclerOptions<Car>) :
    FirestoreRecyclerAdapter<Car, CarViewHolder>(options) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as View
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int, model: Car) {
        holder.modelTextView.text = model.model
        holder.setImage(model.imageUrl)
        holder.rootLayout.setOnClickListener {
            adapterOnClick(model)
        }
    }

    override fun onDataChanged() {
        super.onDataChanged()
    }

}