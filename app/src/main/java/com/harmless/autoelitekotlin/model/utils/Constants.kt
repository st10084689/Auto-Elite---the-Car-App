package com.harmless.autoelitekotlin.model.utils

data class Constants (

    val mileage: List<String>  = mutableListOf("Mileage","200 000kms+","199 999-150 000kms","149 999-100 000kms","99 999-50 000kms","49 999-10 000kms","9999-0kms"),
    val driveTrain: List<String> = mutableListOf("Type","4×4","Front Wheel Drive","Rear Wheel Drive"),
    val newOrUsed: List<String> = mutableListOf("New/Used","New","Used"),
    val fuelType: List<String> = mutableListOf("Fuel Type","Petrol","Diesel","Electric"),
    val provinces: List<String> = mutableListOf("Eastern Cape","Gauteng","KwaZulu-Natal","Limpopo","Northern Cape","North West","Western Cape"),
    val color : List<String> = mutableListOf("Red","Yellow","Green","Blue","Silver","Black","White","Orange","Pink","Purple","Brown")
)


