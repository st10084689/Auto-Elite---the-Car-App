package com.harmless.autoelitekotlin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harmless.autoelitekotlin.model.Car

class SellCarViewModel: ViewModel() {

    val sellCarObject = MutableLiveData(
        Car(
            BodyType = "",
            IsNew = false,
            brand = "",
            color = "",
            images = emptyList(),
            location = "",
            mileage = 0,
            model = "",
            price = 0.0,
            provinces = "",
            transmission = "",
            type = "",
            year = 0,
            wheelDrive = "",
            variant = "",
            description = ""
        )
    )

    fun updateFirstPage(model: String, brand: String, type: String, color: String, wheelDrive:String,variant:String) {
        val current = sellCarObject.value ?: return
        sellCarObject.value = current.copy(model = model, brand = brand, type = type, color = color, wheelDrive =wheelDrive, variant=variant )
    }

    fun updateSecondPage(year: Int, price: Double,mileage:Int,isNew:Boolean,province:String,description:String) {
        val current = sellCarObject.value ?: return
        sellCarObject.value = current.copy(year = year, price = price, mileage = mileage, IsNew = isNew, provinces = province, description = description )


    }

    fun updateThirdPage(images : List<String>){
        val current = sellCarObject.value ?: return
        sellCarObject.value = current.copy(images = images)
    }


}