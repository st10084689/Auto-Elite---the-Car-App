package com.harmless.autoelitekotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.view.recyclerViews.SelectBrandRecyclerAdapter
import com.harmless.autoelitekotlin.viewModel.MakeAndModelViewModel

private const val TAG = "MakeAndModel"
class MakeAndModel : AppCompatActivity() {

    private lateinit var backBtn: AppCompatButton
    private lateinit var applyBtn: AppCompatButton
    private lateinit var selectCarRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_and_model)
    init()

    }

    fun init(){
        var make = MakeAndModelViewModel().getCarBrand()

        backBtn = findViewById(R.id.backBtn)
        applyBtn = findViewById(R.id.applyBtn)
        selectCarRecycler = findViewById(R.id.Car_selection_recyclerview)
        selectCarRecycler.setHasFixedSize(true)
        selectCarRecycler.setLayoutManager(LinearLayoutManager(this))


         var carBrandAdapter = SelectBrandRecyclerAdapter(make)
        selectCarRecycler.adapter = carBrandAdapter

        backBtn.setOnClickListener {
            finish()
        }
        applyBtn.setOnClickListener {
            finish()
        }
    }//init method ends
}//class ends

