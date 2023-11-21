package com.harmless.autoelitekotlin.viewModel

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.harmless.autoelitekotlin.model.CarBrand

interface CarBrandCallback {
    fun onCarBrandLoaded(carBrands: List<CarBrand>)
    fun onCancelled(error: DatabaseError)
}

class MakeAndModelViewModel {


        private var carBrands = mutableListOf<CarBrand>()

     private var isExpandable = false

        fun setCarBrand(callback: CarBrandCallback) {
            val carsReference = FirebaseDatabase.getInstance().getReference("carRecycler").child("brands")
            val carListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    carBrands.clear()
                    for (carSnapshot in snapshot.children) {
                        val carBrand = carSnapshot.getValue(CarBrand::class.java)
                        if (carBrand != null) {
                            carBrands.add(carBrand)
                        }
                    }
                    callback.onCarBrandLoaded(carBrands)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback.onCancelled(error)
                }
            }
            carsReference.addValueEventListener(carListener)
        }

        fun getCarBrand(): List<CarBrand> {
            return carBrands
        }


    fun IsExpandable(position:Int):Boolean{
        return isExpandable
    }

    fun setExpandable(expandable: Boolean) {
        isExpandable = expandable
    }


}