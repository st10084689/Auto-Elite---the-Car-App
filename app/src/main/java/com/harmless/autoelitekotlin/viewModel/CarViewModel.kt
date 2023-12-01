package com.harmless.autoelitekotlin.viewModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.model.Constants
import com.harmless.autoelitekotlin.model.Utility
import java.lang.StringBuilder

const val TAG = "CarViewModel"

class CarViewModel {

    var carList = mutableListOf<Car>()
    var selectedCars = mutableListOf<Car>()

    interface CarsCallback {
        fun onDataLoaded(cars: List<Car>)
        fun onCancelled(error: DatabaseError)
    }

    fun setCars(callback: CarsCallback) {
        val carsReference = FirebaseDatabase.getInstance().getReference("cars")
        val carListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                carList.clear()
                for (carSnapshot in snapshot.children) {
                    val car = carSnapshot.getValue(Car::class.java)
                    if (car != null) {
                        if (car.mileage == null) {
                            car.mileage = 0
                        }
                        carList.add(car)
                    }
                }

                callback.onDataLoaded(carList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback.onCancelled(error)
            }
        }

        carsReference.addValueEventListener(carListener)
    }

    fun minMileage(selectedMileage:String):Int?{
        val constants = Constants()
        when(selectedMileage){
            constants.mileage[0]->{
                return null
            }
            constants.mileage[1]->{
                return 200000
            }
            constants.mileage[2]->{
                return 150000
            }
            constants.mileage[3]->{
                return 100000
            }
            constants.mileage[4]->{
                return 50000
            }
            constants.mileage[5]->{
                return 10000
            }
            constants.mileage[6]->{
                return 0
            }
            else->{
                return null
            }
        }
    }

    fun maxMileage(selectedMileage:String): Int? {
        val constants = Constants()
        when(selectedMileage){
            constants.mileage[0]->{
                return null
            }
            constants.mileage[1]->{
                return 1000000
            }
            constants.mileage[2]->{
                return 199999
            }
            constants.mileage[3]->{
                return 149999
            }
            constants.mileage[4]->{
                return 99999
            }
            constants.mileage[5]->{
                return 49999
            }
            constants.mileage[6]->{
                return 9999
            }
            else->{
                return null
            }
        }
    }

    fun setSelectedCars(
        carsSelected: MutableList<CarBrand>,
        year: MutableList<Int>,
        color: String?,
        driveTrain: String?,
        location: String?,
        minMileage: Int? = null,
        maxMileage: Int? = null,
        price: MutableList<Double>,
        transmission: String?,
        callback: CarsCallback
    ) {
        setCars(object : CarsCallback {
            override fun onDataLoaded(cars: List<Car>) {
                selectedCars.clear()
                sortPrice()
                sortYear()
                for (car in cars) {
                    val carsBrand = car.brand
                    val carsModel = car.model
                    val carsColor = car.color
                    val carsType = car.type
                    val carsLocation = car.location
                    val carsMileage = car.mileage
                    val carsPrice = car.price
                    val carsTransmission = car.transmission
                    val carsYear = car.year

                    if(carsSelected.isEmpty()){

                        val colorCondition = carsColor == color || color == null
                        val typeCondition = carsType == driveTrain || driveTrain == null
                        val locationCondition = carsLocation == location || location == null
                        val mileageCondition = (minMileage == null || carsMileage in (minMileage..maxMileage!!)) || minMileage == null
                        val priceCondition =  (price.isNotEmpty() && carsPrice!! in (price[0]..price[price.size-1])||price.isEmpty())
                        val transmissionCondition = carsTransmission == transmission || transmission == null
                        val yearCondition = (year.isNotEmpty() && carsYear!! in (year[0]..year[year.size-1])||year.isEmpty())

                        if (colorCondition &&
                            typeCondition &&
                            locationCondition &&
                            mileageCondition &&
                            priceCondition &&
                            transmissionCondition &&
                            yearCondition
                        ) {

                            selectedCars.add(car)
                            Log.d(TAG, "getSelectedCars: +add" + car.brand)
                        }

                    }
                    for (carBrand in carsSelected) {
                        val brandCondition = carsBrand == carBrand.name || carsSelected.isEmpty() || carsSelected == null
                        val modelCondition = carsModel == null || carsModel in carBrand.models || carBrand.models.isEmpty()
                        val colorCondition = carsColor == color || color == null
                        val typeCondition = carsType == driveTrain || driveTrain == null
                        val locationCondition = carsLocation == location || location == null
                        val mileageCondition = (minMileage == null || carsMileage in (minMileage..maxMileage!!)) || minMileage == null
                        val priceCondition = price == null || (price.isNotEmpty() && carsPrice!! in (price[0]..price.size.toDouble()))
                        val transmissionCondition = carsTransmission == transmission || transmission == null
                        val yearCondition = year == null || (year.isNotEmpty() && carsYear!! in (year[0]..year.size))


                        if (brandCondition  &&
                            modelCondition &&
                            colorCondition &&
                            typeCondition &&
                            locationCondition &&
                            mileageCondition &&
                            priceCondition &&
                            transmissionCondition &&
                            yearCondition
                        ) {

                            selectedCars.add(car)

                            Log.d(TAG, "getSelectedCars: +add" + car.brand)
                        }
                    }
                }

                callback.onDataLoaded(selectedCars)
            }

            override fun onCancelled(error: DatabaseError) {
                callback.onCancelled(error)
            }
        })
    }

    private fun sortPrice(){
       val list = Utility.selectedPrice

        val n = list.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (list[j] > list[j + 1]) {
                    // Swap elements if they are in the wrong order
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        Utility.selectedPrice = list
    }
    private fun sortYear(){
        val list = Utility.selectedYear

        val n = list.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (list[j] > list[j + 1]) {
                    // Swap elements if they are in the wrong order
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        Utility.selectedYear = list
    }


}
