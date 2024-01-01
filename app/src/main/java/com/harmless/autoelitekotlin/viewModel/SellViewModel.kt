package com.harmless.autoelitekotlin.viewModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.model.CarModels
import com.harmless.autoelitekotlin.model.Province
import com.harmless.autoelitekotlin.view.activities.SellCarActivity

class SellViewModel {

    companion object{
        var TAG = "SellViewModel"
    }

    private lateinit var database: DatabaseReference


//     private var provincesList = mutableListOf<Province>()
    private var carBrands = ArrayList<CarBrand>()


    interface ProvincesCallback {
        fun onProvincesLoaded(provinces: List<Province>)
    }
    interface CarBrandsCallBack{
        fun onCarBrandLoaded(carBrands: ArrayList<CarBrand>)
    }

    fun getProvinces(callback: ProvincesCallback) {
        database = Firebase.database.reference

        database.child("Provinces").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val provincesList = mutableListOf<Province>()
                for (provinceSnapshot in snapshot.children) {
                    val province = provinceSnapshot.getValue(Province::class.java)
                    province?.let {
                        provincesList.add(it)
                    }
                }
                Log.d(TAG, "onDataChange: done")

                
                callback.onProvincesLoaded(provincesList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: failed")
            }
        })
    }


    fun setCarBrand(callback: CarBrandsCallBack) {
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

            }
        }
        carsReference.addValueEventListener(carListener)
    }


    fun toStringProvinceNames(provinces: List<Province>): List<String> {//method that adds the list<provinces> names to a list to a string list so i can use it in the a spinner adapter
        Log.d(SellCarActivity.TAG, "provinceNames: Entered")
        var provinceName: MutableList<String> = mutableListOf()
        for (province in provinces) {
            provinceName.add(province.ProvinceName)
        }
        return provinceName
    }

    fun toStringCarBrands(carBrands: ArrayList<CarBrand>): List<String> {//method that adds the list<CarBrands> names to a list to a string list so i can use it in the a spinner adapter
        Log.d(TAG, "Car Names: Entered")
        var carBrand: ArrayList<String> = ArrayList()
        for (items in carBrands) {
            carBrand.add(items.name)
        }
        return carBrand
    }

    fun getChosenCities(provinceName: String, provinceList: List<Province>): List<String> {//from the province name chosen it gets the cities of the province
        var cities = mutableListOf<String>()

        for (item in provinceList!!) {
            if(provinceName == item.ProvinceName){
                for (city in item.Cities ){
                    cities.add(city)
                }
            }
        }
        for (items in cities) {
            Log.d(SellCarActivity.TAG, "getCities: $items")
        }

        return cities
    }

    fun getChosenModelsNames(carBrand: String, carBrandList: List<CarBrand>): List<String> {//from the province name chosen it gets the cities of the province
        var models = mutableListOf<CarModels>()

        for (brand in carBrandList!!){
          if(carBrand == brand.name){
              models = brand.models as MutableList<CarModels>
          }
        }
        var modelString = mutableListOf<String>()
        for (model in models){
            modelString.add(model.model)
        }
        return modelString
    }
    fun getChosenModelsList(carBrand: String, carBrandList: List<CarBrand>): List<CarModels> {//from the province name chosen it gets the cities of the province
        var models = mutableListOf<CarModels>()

        for (brand in carBrandList!!){
            if(carBrand == brand.name){
                models = brand.models as MutableList<CarModels>
            }
        }

        return models
    }

    fun getChosenVariants(Model: String, modelList: List<CarModels>): List<String> {//from the province name chosen it gets the cities of the province
        var variantsStringList = mutableListOf<String>()

        for (item in modelList!!) {
            if(Model == item.model){
                for (varients in item.variants ){
                    variantsStringList.add(varients)
                }
            }
        }
        for (items in variantsStringList) {
            Log.d(SellCarActivity.TAG, "getCities: $items")
        }

        return variantsStringList
    }

    fun writeToCar(brand:String, model:String, province:String, area:String,mileage:Int,imageList: List<String>, bodyType:String, price: Double, isNew: Boolean, color: String, transmission: String, type: String, year: Int){
        val writeRef = FirebaseDatabase.getInstance().getReference("cars")
        //creating a car object so that i can push to the firebase storage
        val car = Car(bodyType, isNew, brand, color,imageList ,area, mileage, model, price, province,transmission ,type,year)

        writeRef.push().setValue(car)//pushing to the firebase storage
    }
    }