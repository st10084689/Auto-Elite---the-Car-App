package com.harmless.autoelitekotlin.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.view.adapters.VehicleListReyclerAdapter

class VehicleList : AppCompatActivity() {

    private var cars: List<Car>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)
        val intent: Intent = intent
        cars = intent.getSerializableExtra("selectedCars") as List<Car>

        init()
    }

    fun init(){
        val vehicleRecycler = findViewById<RecyclerView>(R.id.CarRecyclerView)
        vehicleRecycler.setHasFixedSize(true)
        vehicleRecycler.layoutManager  = LinearLayoutManager(this)
        val adapter = VehicleListReyclerAdapter(cars!!)
        vehicleRecycler.adapter = adapter

    }
}