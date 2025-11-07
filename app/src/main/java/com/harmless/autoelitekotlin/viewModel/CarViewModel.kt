package com.harmless.autoelitekotlin.viewModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.model.utils.Constants
import com.harmless.autoelitekotlin.model.utils.SelectedValues

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
        yearSelected: MutableList<Int>,
        colorSelected: MutableList<String>,
        driveTrainSelected: String?,
        locationSelected: MutableList<String>,
        minMileageSelected: Int? = null,
        maxMileageSelected: Int? = null,
        priceSelected: MutableList<Double>,
        transmissionSelected: String?,
        callback: CarsCallback
    ) {
        setCars(object : CarsCallback {
            override fun onDataLoaded(cars: List<Car>) {
                selectedCars.clear()
                sortPrice()
                sortYear()

                for (car in cars) {
                    val brandCondition = carsSelected.isEmpty() || carsSelected.any { it.brand == car.brand }
                    val modelCondition = carsSelected.isEmpty() || carsSelected.any { carBrand -> carBrand.models.any { it.name == car.model } }
                    val colorCondition = colorSelected.isEmpty() || colorSelected.contains(car.color)
                    val typeCondition = driveTrainSelected == null || driveTrainSelected == "Type" || car.type == driveTrainSelected
                    val locationCondition = locationSelected.isEmpty() || locationSelected.contains(car.location)
                    val mileageCondition = (minMileageSelected == null || maxMileageSelected == null) ||
                            (car.mileage in (minMileageSelected!!..maxMileageSelected!!))
                    val priceCondition = priceSelected.isEmpty() || (car.price in priceSelected.minOrNull()!!..priceSelected.maxOrNull()!!)
                    val transmissionCondition = transmissionSelected == null || car.transmission == transmissionSelected
                    val yearCondition = yearSelected.isEmpty() || car.year in (yearSelected.minOrNull()!!..yearSelected.maxOrNull()!!)

                    if (brandCondition &&
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
       val list = SelectedValues.selectedPrice

        val n = list.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (list[j] > list[j + 1]) {

                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        SelectedValues.selectedPrice = list
    }
    private fun sortYear(){
        val list = SelectedValues.selectedYear

        val n = list.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (list[j] > list[j + 1]) {

                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        SelectedValues.selectedYear = list
    }


}
