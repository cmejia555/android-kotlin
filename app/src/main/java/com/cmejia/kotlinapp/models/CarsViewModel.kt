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

    init {
        carDao = LocalDataBase.getInstance(application, viewModelScope).carDao()
        loadData()
        allCars = carDao.getAll()
    }

    private fun loadData() {
        insertCar(Car(0, "Chevrolet", "Meriva", 2010, imageId = R.drawable.meriva, description = "Se encuentra en excelente estado"))
        insertCar(Car(1, "Peugeot", "206", 2014, imageId = R.drawable.peugeot_206, description = "Listo para manejar, con tan solo 80mil km"))
        insertCar(Car(2, "Ford", "Focus", 2017, imageId = R.drawable.focus, description = "Tiene equipo de GNC y es full"))
    }

    fun getAllCars(): LiveData<List<Car>> {
        return allCars
    }

    fun getCar(index : Long) : Car? {
        return carDao.get(index)
    }

    fun insertCar(car : Car) {
        car.apply {
            if (carId == -1L) {
                carId = System.currentTimeMillis()
            }
        }
        carDao.insert(car)
    }

    fun deleteCar(car: Car) {
        carDao.delete(car)
    }

    fun updateCar(car : Car) {
        carDao.update(car)
    }

}