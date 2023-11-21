package com.harmless.autoelitekotlin.viewModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.harmless.autoelitekotlin.model.Car

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

    fun setSelectedCars(
        brand: String?,
        model: String?,
        year: Int?,
        color: String?,
        driveTrain: String?,
        location: String?,
        minMileage: Int?,
        maxMileage: Int?,
        price: Double?,
        transmission: String?,
        callback: CarsCallback
    ) {

        setCars(object : CarsCallback {
            override fun onDataLoaded(cars: List<Car>) {
                // getting the value from the cars firebase
                var carsBrand: String?
                var carsModel: String?
                var carsColor: String?
                var carsType: String?
                var carsLocation: String?
                var carsMileage: Int?
                var carsPrice: Double?
                var carsTransmission: String?

                selectedCars.clear()

                for (car in carList) {
                    carsBrand = car.brand;
                    carsModel = car.model;
                    carsColor = car.color;
                    carsType = car.type;
                    carsLocation = car.location;
                    carsMileage = car.mileage;
                    carsPrice = car.price;
                    carsTransmission = car.transmission;



                    if ((carsBrand == brand || brand == null)
                        && (carsModel == model || model == null)
                        && (carsColor == color || color == null)
                        && (carsType == driveTrain || driveTrain == null)
                        && (carsLocation == location || location == null)
                        && (carsMileage!! >= minMileage!! && carsMileage!! <= maxMileage!!)
                        && (carsPrice == price || price == null)
                        && (carsTransmission == transmission || transmission == null)
                    ) {
                        selectedCars.add(car)
                        Log.d(TAG, "getSelectedCars: +add" + car.brand)
                    }


                }

                    callback.onDataLoaded(selectedCars)

            }

            override fun onCancelled(error: DatabaseError) {
                callback.onCancelled(error)
            }
        })
    }
}
