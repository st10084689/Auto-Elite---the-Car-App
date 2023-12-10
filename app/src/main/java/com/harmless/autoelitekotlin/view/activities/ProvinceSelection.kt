package com.harmless.autoelitekotlin.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseError
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.utils.Constants
import com.harmless.autoelitekotlin.view.adapters.ProvinceSelectionRecyclerAdapter
import com.harmless.autoelitekotlin.view.adapters.YearSelectionRecyclerAdapter
import com.harmless.autoelitekotlin.viewModel.YearSelectionViewModel

class ProvinceSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_province_selection)
        init()
    }

    private fun init() {
        val provinceRecycler = findViewById<RecyclerView>(R.id.provinceRecyclerView)


        provinceRecycler.setHasFixedSize(true)
        provinceRecycler.layoutManager  = LinearLayoutManager(applicationContext)

                val constant = Constants()
                val adapterProvince= ProvinceSelectionRecyclerAdapter(constant.provinces)
        provinceRecycler.adapter = adapterProvince
    }
}