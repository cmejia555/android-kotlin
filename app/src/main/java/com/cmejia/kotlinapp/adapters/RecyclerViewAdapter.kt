package com.cmejia.kotlinapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car


class RecyclerViewAdapter(private var carList : MutableList<Car>,
                          val adapterOnClick : (item: Int) -> Unit) :
                    RecyclerView.Adapter<RecyclerViewAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_list_item, parent, false) as View

        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.brandTextView.text = (carList[position].brand)
        holder.rootLayout.setOnClickListener {
            adapterOnClick(position)
        }
    }

    override fun getItemCount() = carList.size


    class CarViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val brandTextView : TextView = view.findViewById(R.id.name_item_tv)
        val rootLayout : CardView = view.findViewById(R.id.card_layout)
    }
}