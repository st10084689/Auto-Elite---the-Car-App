package com.harmless.autoelitekotlin.model.utils

data class Constants (

    val mileage: List<String>  = mutableListOf("Mileage","200 000kms+","199 999-150 000kms","149 999-100 000kms","99 999-50 000kms","49 999-10 000kms","9999-0kms"),
    val driveTrain: List<String> = mutableListOf("Type","4×4","Front Wheel Drive","Rear Wheel Drive"),
    val transmission: List<String> = mutableListOf("Automatic","Manual","Electric"),
    val newOrUsed: List<String> = mutableListOf("New/Used","New","Used"),
    val bodyType: List<String> = mutableListOf("Hatch Back","Bakkie","Sedan","Cabriolet","Van","Bus"),
    val year: List<String> = mutableListOf("Years","2024","2023","2022","2021","2020","2019","2018","2017","2015","2014","older than 2014"),
    val fuelType: List<String> = mutableListOf("Fuel Type","Petrol","Diesel","Electric"),
    val provinces: List<String> = mutableListOf("Eastern Cape","Gauteng","KwaZulu-Natal","Limpopo","Northern Cape","North West","Western Cape"),
    val color : List<String> = mutableListOf("Red","Yellow","Green","Blue","Silver","Black","White","Orange","Pink","Purple","Brown"),
    val sharedPrefString : String = "AutoElite"
)


