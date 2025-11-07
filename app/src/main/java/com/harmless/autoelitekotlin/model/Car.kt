package com.harmless.autoelitekotlin.model

import android.os.Parcel
import android.os.Parcelable

data class Car(
    var BodyType: String,
    var IsNew: Boolean,
    var brand: String,
    var color: String,
    var images: List<String>,
    var location: String,
    var mileage: Int,
    var model: String,
    var price: Double,
    var provinces: String,
    var transmission: String,
    var type: String,
    var year: Int,
    var wheelDrive: String,
    var variant: String,
    var description: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    constructor() : this(
        "",
        false,
        "",
        "",
        emptyList(),
        "",
        0,
        "",
        0.0,
        "",
        "",
        "",
        0,
        "",
        "",
        ""
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(BodyType)
        dest.writeByte(if (IsNew) 1 else 0)
        dest.writeString(brand)
        dest.writeString(color)
        dest.writeStringList(images)
        dest.writeString(location)
        dest.writeInt(mileage)
        dest.writeString(model)
        dest.writeDouble(price)
        dest.writeString(provinces)
        dest.writeString(transmission)
        dest.writeString(type)
        dest.writeInt(year)
        dest.writeString(wheelDrive)
        dest.writeString(variant)
        dest.writeString(description)
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
