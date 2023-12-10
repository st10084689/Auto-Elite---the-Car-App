package com.harmless.autoelitekotlin.viewModel

import android.util.Log
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


        fun setCarBrand(callback: CarBrandCallback) {
            val carsReference = FirebaseDatabase.getInstance().getReference("carRecycler").child("brands")
            val carListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    carBrands.clear()
                    for (carSnapshot in snapshot.children) {
                        val carBrand = carSnapshot.getValue(CarBrand::class.java)
                        if (carBrand != null) {
                            carBrands.add(carBrand)
//                            for (items in carBrands.indices)
//                                for(model in carBrands[items].model!!){
//                                    Log.d(TAG, "onDataChange: "+ carBrands[items].model!!)
//                                }
//
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

    fun isExpandable(carsBrandList: List<String>): Boolean {
        if(carsBrandList != null){
            return !carsBrandList.isEmpty()
        }
        else{
            return false
        }
    }

    interface OnNestedItemClickListner{
        fun onNestItemClicked(position:Int, isChecked: Boolean)
    }


}