package com.harmless.autoelitekotlin.model

data class User(

    var displayName: String,
    var image: String
) {
    constructor() : this("", "")
}