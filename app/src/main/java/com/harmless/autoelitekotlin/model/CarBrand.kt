package com.harmless.autoelitekotlin.model

import com.google.firebase.database.Exclude

data class CarBrand(
    val brand: String = "",
    val models: MutableList<CarModel> = mutableListOf()
) {
    @get:Exclude
    var isExpandable: Boolean = false
}

data class CarModel(
    val name: String = "",
    val variants: MutableList<String> = mutableListOf()
)