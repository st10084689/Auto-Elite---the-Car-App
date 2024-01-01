package com.harmless.autoelitekotlin.model

data class Province(
    val Cities: List<String>,
    val ProvinceName: String
){
    constructor() : this(mutableListOf(), "")
}


