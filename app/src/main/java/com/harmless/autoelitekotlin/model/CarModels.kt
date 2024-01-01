package com.harmless.autoelitekotlin.model

data class CarModels (
    val model: String,
    val variants: List<String>
        ){ constructor() : this( "",mutableListOf())}


