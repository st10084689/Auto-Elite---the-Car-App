package com.harmless.autoelitekotlin.viewModel

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PriceSelectionViewModel {

interface PriceCallback {
    fun onPriceLoaded(price: List<Double>)
    fun onCancelled(error: DatabaseError)
}


private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

fun loadPrice(callback: PriceCallback) {
    val priceReference: DatabaseReference = databaseReference.child("price")

    val priceListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val priceList = snapshot.children.mapNotNull { it.getValue(Double::class.java) }
            callback.onPriceLoaded(priceList)
        }

        override fun onCancelled(error: DatabaseError) {
            callback.onCancelled(error)
        }
    }

    priceReference.addValueEventListener(priceListener)
}
}
