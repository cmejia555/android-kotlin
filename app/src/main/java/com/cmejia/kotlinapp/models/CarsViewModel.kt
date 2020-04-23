package com.cmejia.kotlinapp.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmejia.kotlinapp.entities.Car

class CarsViewModel : ViewModel() {

    private var carsAsList : MutableList<Car> = ArrayList()
    private var cars : MutableLiveData<List<Car>> = MutableLiveData<List<Car>>()

    init {
        cars.value = loadUsers()
    }

    private fun loadUsers() : MutableList<Car> {
        carsAsList = mutableListOf(
            Car("Chevrolet", "Meriva", 2010),
            Car("Peugeot", "206", 2014),
            Car("Ford", "Focus", 2017)
        )

        return carsAsList
    }

    fun getCars(): MutableList<Car> {
        return cars.value!! as MutableList
    }

    fun getCar(index : Int) : Car? {
        return cars.value?.get(index)
    }

    fun addCar(car : Car) {
        carsAsList.add(car)
        cars.value = carsAsList
    }
}