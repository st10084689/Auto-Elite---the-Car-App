package com.harmless.autoelitekotlin.model

data class Car (

   var id:Int = 0,

   var Images: MutableList<String>? = null,

    var brand: String? = null,

   var year: Int? = 0,

   var model: String? = null,

   var color: String? = null,

   var type: String? = null,

   var location: String? = null,

   var mileage: Int?,

   var price: Double?,

    var transmission: String?
)