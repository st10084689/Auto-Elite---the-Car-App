package com.harmless.autoelitekotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseError
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.view.recyclerViews.SelectBrandRecyclerAdapter
import com.harmless.autoelitekotlin.viewModel.CarBrandCallback
import com.harmless.autoelitekotlin.viewModel.MakeAndModelViewModel

private const val TAG = "MakeAndModel"
class MakeAndModel : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_and_model)
        init()
    }

    private fun init(){
        val backBtn = findViewById<AppCompatButton>(R.id.backBtn)
        val applyBtn = findViewById<AppCompatButton>(R.id.applyBtn)
        val selectedCarRecycler = findViewById<RecyclerView>(R.id.Car_selection_recyclerview)
        selectedCarRecycler.setHasFixedSize(true)
        selectedCarRecycler.layoutManager = LinearLayoutManager(this)

        val viewModel = MakeAndModelViewModel()

        viewModel.setCarBrand(object : CarBrandCallback {
            override fun onCarBrandLoaded(carBrands: List<CarBrand>) {
                val adapter = SelectBrandRecyclerAdapter(carBrands)

                selectedCarRecycler.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })



        backBtn.setOnClickListener {
            finish()
        }
        applyBtn.setOnClickListener {
            finish();
        }
    }
}

