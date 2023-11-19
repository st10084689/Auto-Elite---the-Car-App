package com.harmless.autoelitekotlin.viewModel

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.model.CarBrand

class MakeAndModelViewModel {

    private  lateinit var carBrand: MutableList<CarBrand>

    interface BrandsCallback {
        fun onCarsBrandsLoaded(brands: List<CarBrand>)
        fun onCancelled(error: DatabaseError)
    }

    private fun initCarBrand(callback: BrandsCallback)
    {
        val brandReference = FirebaseDatabase.getInstance().getReference("carRecycler").child("brands")
        val brandListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (carSnapshot in snapshot.children) {
                    val car = carSnapshot.getValue<CarBrand>()
                    if (car != null) {
                        carBrand.add(car)
                    }
                }
                callback.onCarsBrandsLoaded(carBrand)
            }

            override fun onCancelled(error: DatabaseError) {
                callback.onCancelled(error)
            }
        }

        brandReference.addValueEventListener(brandListener)
    }

     fun getCarBrand():List<CarBrand>{
        return carBrand
    }

    fun getIsExpandable(position:Int):Boolean{
        if(carBrand.get(position).model !=null||carBrand.get(position).model!!.isNotEmpty()){
            return true
        }
        else{
            return false
        }
    }


}