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

                            Log.d(TAG, "onDataLoadedColor: the car color is $cars")
                            Log.d(TAG, "onDataLoadedColor: the car color is $carsColor")


                        val colorCondition = (colorSelected.isEmpty()||colorSelected.isNotEmpty() && carsColor!! in (colorSelected[0]..colorSelected[priceSelected.size-1]))
                        val typeCondition = carsType == driveTrainSelected || driveTrainSelected == null||driveTrainSelected =="Type"
                        val locationCondition = (locationSelected.isNotEmpty() && carsLocation!! in (locationSelected[0]..locationSelected[priceSelected.size-1])||locationSelected.isEmpty())
                        val mileageCondition = (minMileageSelected == null&&minMileageSelected == null || carsMileage in (minMileageSelected..maxMileageSelected!!))
                        val priceCondition =  (priceSelected.isNotEmpty() && carsPrice!! in (priceSelected[0]..priceSelected[priceSelected.size-1])||priceSelected.isEmpty())
                        val transmissionCondition = carsTransmission == transmissionSelected || transmissionSelected == null
                        val yearCondition = (yearSelected.isNotEmpty() && carsYear!! in (yearSelected[0]..yearSelected[yearSelected.size-1])||yearSelected.isEmpty())
                        Log.d(TAG, "onDataLoaded: colorCondition $colorCondition the car is $carsColor the selected car is ${colorSelected}")
                        Log.d(TAG, "onDataLoaded: typeCondition $typeCondition the car is $carsType  the selected car is ${driveTrainSelected}")
                        Log.d(TAG, "onDataLoaded: locationCondition $locationCondition the car is $carsLocation  the selected car is ${locationSelected}")
                        Log.d(TAG, "onDataLoaded: mileageCondition $mileageCondition the value is $carsMileage the selected min ${SelectedValues.selectedMinMileage} the selected max ${SelectedValues.selectedMaxMileage} ")
                        Log.d(TAG, "onDataLoaded: priceCondition $priceCondition ")
                        Log.d(TAG, "onDataLoaded: transmissionCondition $transmissionCondition the car is $carsTransmission  the selected car is ${transmissionSelected}")
                        Log.d(TAG, "onDataLoaded: yearCondition $yearCondition ")
                        Log.d(TAG, "onDataLoaded:-------------------------------------------------------------------")
                        Log.d(TAG, "onDataLoaded:-------------------------------------------------------------------")

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
                    else{
                        for (carBrand in carsSelected) {
                            val brandCondition = carsBrand == carBrand.name || carsSelected.isEmpty() || carsSelected == null
                            var modelCondition = false
                            for (items in carBrand.models){
                                modelCondition = carsModel == null ||carsModel in items.model  || carBrand.models.isEmpty()
                            }
                            val colorCondition =(colorSelected.isNotEmpty() && carsColor!! in (colorSelected[0]..colorSelected[priceSelected.size-1])||colorSelected.isEmpty())
                            val typeCondition = carsType == driveTrainSelected ||driveTrainSelected == null||driveTrainSelected =="Type"
                            val locationCondition = (locationSelected.isNotEmpty() && carsLocation!! in (locationSelected[0]..locationSelected[priceSelected.size-1])||locationSelected.isEmpty())
                            val mileageCondition = (minMileageSelected == null&&minMileageSelected == null || carsMileage in (minMileageSelected..maxMileageSelected!!))
                            val priceCondition =  (priceSelected.isNotEmpty() && carsPrice!! in (priceSelected[0]..priceSelected[priceSelected.size-1])||priceSelected.isEmpty())
                            val transmissionCondition = carsTransmission == transmissionSelected || transmissionSelected == null
                            val yearCondition = (yearSelected.isNotEmpty() && carsYear!! in (yearSelected[0]..yearSelected[yearSelected.size-1])||yearSelected.isEmpty())
                            Log.d(TAG, "-------------------------------------------------------------------")
                            Log.d(TAG, "-------------------------------------------------------------------")

                            Log.d(TAG, "onDataLoaded: BrandCondition $brandCondition the car is $carsBrand the selected car is ${carBrand.name}")
//                            Log.d(TAG, "onDataLoaded: modelCondition $modelCondition the car is $carsModel the selected car is ${carsModel in carBrand.models}")
                            Log.d(TAG, "onDataLoaded: colorCondition $colorCondition the car is $carsColor the selected car is ${colorSelected}")
                            Log.d(TAG, "onDataLoaded: typeCondition $typeCondition the car is $carsType  the selected car is ${driveTrainSelected}")
                            Log.d(TAG, "onDataLoaded: locationCondition $locationCondition the car is $carsLocation  the selected car is ${locationSelected}")
                            Log.d(TAG, "onDataLoaded: mileageCondition $mileageCondition the car is $carsColor  the selected car is ${carBrand.name}")
                            Log.d(TAG, "onDataLoaded: priceCondition $priceCondition ")
                            Log.d(TAG, "onDataLoaded: transmissionCondition $transmissionCondition the car is $carsTransmission  the selected car is ${transmissionSelected}")
                            Log.d(TAG, "onDataLoaded: yearCondition $yearCondition ")
                            Log.d(TAG, "-------------------------------------------------------------------")
                            Log.d(TAG, "-------------------------------------------------------------------")

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
