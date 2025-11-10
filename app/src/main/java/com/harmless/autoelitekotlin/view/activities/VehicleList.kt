package com.harmless.autoelitekotlin.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivityVehicleListBinding
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.view.adapters.VehicleListReyclerAdapter

class VehicleList : AppCompatActivity() {

    private var cars: MutableList<Car> = mutableListOf()
    private lateinit var binding: ActivityVehicleListBinding
    private lateinit var adapter: VehicleListReyclerAdapter
    private lateinit var sortBySpinner: Spinner
    private lateinit var titleTxt: TextView

    private lateinit var refineBtn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVehicleListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val intent: Intent = intent
        @Suppress("UNCHECKED_CAST")
        cars = (intent.getSerializableExtra("selectedCars") as? List<Car>)?.toMutableList()
            ?: mutableListOf()


        initRecycler()
        setupSpinner()
        initViews()
    }

    private fun initViews(){
        titleTxt = binding.vehicleListText
        titleTxt.text = "Search Results(${cars.size})"

        refineBtn = binding.refineButton
        refineBtn.setOnClickListener {
            finish()
        }
    }

    private fun initRecycler() {
        val vehicleRecycler = binding.CarRecyclerView
        vehicleRecycler.setHasFixedSize(true)
        vehicleRecycler.layoutManager = LinearLayoutManager(this)
        adapter = VehicleListReyclerAdapter(cars)
        vehicleRecycler.adapter = adapter
    }

    private fun setupSpinner() {
        sortBySpinner = binding.vehicleListSort

        ArrayAdapter.createFromResource(
            this,
            R.array.sort_options,
            R.layout.spinner_item
        ).also { spinnerAdapter ->
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sortBySpinner.adapter = spinnerAdapter
        }

        sortBySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                val selected = parent?.getItemAtPosition(position).toString()
                val sortedList = when (selected) {
                    "Sort By Lowest Price" -> cars.sortedBy { it.price }
                    "Sort By Highest Price" -> cars.sortedByDescending { it.price }
                    "Sort By Lowest Date" -> cars.sortedBy { it.year }
                    "Sort By Highest Date" -> cars.sortedByDescending { it.year }
                    else -> cars // "Sort By Default"
                }

                adapter.updateData(sortedList)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}