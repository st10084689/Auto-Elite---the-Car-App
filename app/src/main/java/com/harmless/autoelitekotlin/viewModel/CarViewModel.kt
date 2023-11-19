package com.harmless.autoelitekotlin.viewModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.harmless.autoelitekotlin.model.Car
const val TAG = "CarViewModel"

class CarViewModel {

    val cars = mutableListOf<Car>()
    val selectedCars = mutableListOf<Car>()

    interface CarsCallback {
        fun onCarsLoaded(cars: List<Car>)
        fun onCancelled(error: DatabaseError)
    }



    private fun getCars(callback: CarsCallback):List<Car> {
        val carsReference = FirebaseDatabase.getInstance().getReference("cars")
        val carListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (carSnapshot in snapshot.children) {
                    val car = carSnapshot.getValue<Car>()
                    if (car != null) {
                        cars.add(car)
                    }
                }
                callback.onCarsLoaded(cars)
            }

            override fun onCancelled(error: DatabaseError) {
                callback.onCancelled(error)
            }
        }

        carsReference.addValueEventListener(carListener)
        return cars
    }

     fun getSelectedCars(brand:String?,model:String?, year: Int?, color: String?, driveTrain: String?, location: String?, mileage: Int?, price: Double?, transmission: String?):List<Car>{
        //getting the value from the cars firebase
        var carsBrand: String?
        var carsModel: String?
        var carsColor: String?
        var carsType: String?
        var carsLocation: String?
        var carsMileage : Int?
        var carsPrice : Double?
        var carsTransmission: String?
        for(car in cars){
            carsBrand = car.brand
            carsModel = car.model
            carsColor = car.color
            carsType = car.type
            carsLocation = car.location
            carsMileage = car.mileage
            carsPrice = car.price
            carsTransmission = car.transmission

            if(carsBrand==brand||brand==null
                &&carsModel == model||model==null
                &&carsColor == color||color==null
                &&carsType==driveTrain||driveTrain==null
                &&carsLocation == location||location==null
                &&carsMileage == mileage||mileage==null
                &&carsPrice == price || price == null
                && carsTransmission == transmission || transmission == null){
                 selectedCars.add(car)
            }
        }
      return selectedCars
    }

    }