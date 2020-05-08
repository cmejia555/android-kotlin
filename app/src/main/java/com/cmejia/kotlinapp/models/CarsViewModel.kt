package com.cmejia.kotlinapp.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.database.CarDao
import com.cmejia.kotlinapp.database.LocalDataBase
import com.cmejia.kotlinapp.entities.Car


class CarsViewModel(application: Application) : AndroidViewModel(application) {

    private var allCars : LiveData<List<Car>>
    private val carDao : CarDao
    private var newCarId : Int = 0

    init {
        carDao = LocalDataBase.getInstance(application, viewModelScope).carDao()
        loadData()
        allCars = carDao.getAll()
    }

    private fun loadData() {
        insertCar(Car(0, "Chevrolet", "Meriva", 2010, imageId = R.drawable.meriva))
        insertCar(Car(1, "Peugeot", "206", 2014, imageId = R.drawable.peugeot_206))
        insertCar(Car(2, "Ford", "Focus", 2017, imageId = R.drawable.focus))
    }

    fun getAllCars(): LiveData<List<Car>> {
        return allCars
    }

    fun getCar(index : Int) : Car? {
        return carDao.get(index)
    }

    fun insertCar(car : Car) {
        car.carId = newCarId
        carDao.insert(car)
        newCarId++
    }

    fun deleteCar(car: Car) {
        carDao.delete(car)
    }

}