package com.harmless.autoelitekotlin.model

import com.google.firebase.database.Exclude

data class CarBrand(

val models: MutableList<String>,
val name: String
){
    constructor() : this(mutableListOf(), "")
    @Exclude
    var isExpandable: Boolean = false
}
