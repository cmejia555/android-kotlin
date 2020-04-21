package com.cmejia.kotlinapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.entities.Car

class CarListAdapter(private var carList : MutableList<Car>, val adapterOnClick : (item: Int) -> Unit) :
    RecyclerView.Adapter<CarListAdapter.CarsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_list_item, parent, false) as View

        return CarsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        //holder.view.findViewById<TextView>(R.id.list_movie_title).text = moviesList[position].title
        holder.setBrand(carList[position].brand)
        //holder.view.findViewById<CardView>(R.id.card_layout).setOnClickListener {
          //  adapterOnClick()
        //}
        holder.layout.setOnClickListener {
            adapterOnClick(position)
        }
    }

    override fun getItemCount(): Int {
        return  carList.size
    }


    class CarsViewHolder (private val view : View) : RecyclerView.ViewHolder(view) {

        val layout : CardView
            get() {
                return view.findViewById(R.id.card_layout)
            }

        fun setBrand(name : String) {
            val txt : TextView = view.findViewById(R.id.name_item_tv)
            txt.text = name
        }

    }
}