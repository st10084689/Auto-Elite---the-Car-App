package com.harmless.autoelitekotlin.model

import android.os.Parcel
import android.os.Parcelable

data class Car (

   var images: MutableList<String>? = null,

    var brand: String? = null,

   var year: Int? = 0,

   var model: String? = null,

   var color: String? = null,

   var type: String? = null,

   var location: String? = null,

   var mileage: Int?,

   var price: Double?,

    var transmission: String?
) : Parcelable {
    // No-argument constructor
    constructor() : this(
        mutableListOf(),
        null,
        0,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    // Parcelable implementation
    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(images)
        parcel.writeString(brand)
        parcel.writeValue(year)
        parcel.writeString(model)
        parcel.writeString(color)
        parcel.writeString(type)
        parcel.writeString(location)
        parcel.writeValue(mileage)
        parcel.writeValue(price)
        parcel.writeString(transmission)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Car> {
        override fun createFromParcel(parcel: Parcel): Car {
            return Car(parcel)
        }

        override fun newArray(size: Int): Array<Car?> {
            return arrayOfNulls(size)
        }
    }
}