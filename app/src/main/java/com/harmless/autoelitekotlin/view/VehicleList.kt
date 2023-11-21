package com.harmless.autoelitekotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.view.recyclerViews.VehicleListReyclerAdapter

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