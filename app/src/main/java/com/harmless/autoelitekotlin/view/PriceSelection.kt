package com.harmless.autoelitekotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseError
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.view.recyclerViews.PriceSelectionRecyclerAdapter
import com.harmless.autoelitekotlin.viewModel.PriceSelectionViewModel

class PriceSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_selection)
        init()
    }

    private fun init() {
        val priceRecycler = findViewById<RecyclerView>(R.id.PriceRecyclerView)

        val priceSelection = PriceSelectionViewModel()

        priceSelection.loadPrice(object : PriceSelectionViewModel.PriceCallback {
            override fun onPriceLoaded(price: List<Double>) {
                priceRecycler.setHasFixedSize(true)
                priceRecycler.layoutManager  = LinearLayoutManager(applicationContext)
                val adapterPrice = PriceSelectionRecyclerAdapter(price)
                priceRecycler.adapter = adapterPrice
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
