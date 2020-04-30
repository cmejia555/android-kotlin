package com.cmejia.kotlinapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car


class RecyclerViewAdapter(list : MutableList<Car>, val adapterOnClick : (item: Int) -> Unit) :
                    RecyclerView.Adapter<RecyclerViewAdapter.CarViewHolder>() {

    private var carList : MutableList<Car> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as View

        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = carList[position]
        holder.modelTextView.text = item.model
        holder.image.setImageResource(item.imageId!!)
        holder.rootLayout.setOnClickListener {
            adapterOnClick(position)
        }
    }

    override fun getItemCount() = carList.size


    class CarViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val modelTextView : TextView = view.findViewById(R.id.item_car_model)
        val image : ImageView = view.findViewById(R.id.item_car_image)
        val rootLayout : CardView = view.findViewById(R.id.card_layout)
    }
}