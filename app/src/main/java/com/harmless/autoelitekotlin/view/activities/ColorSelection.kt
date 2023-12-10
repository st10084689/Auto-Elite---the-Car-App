package com.harmless.autoelitekotlin.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.utils.Constants
import com.harmless.autoelitekotlin.view.adapters.ColourSelectionRecyclerAdapter
import com.harmless.autoelitekotlin.view.adapters.ProvinceSelectionRecyclerAdapter

class ColorSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_selection)

        init()
    }

        private fun init() {
            val colorRecycler = findViewById<RecyclerView>(R.id.colorRecyclerView)


            colorRecycler.setHasFixedSize(true)
            colorRecycler.layoutManager  = LinearLayoutManager(applicationContext)

            val constant = Constants()
            val adapterProvince= ColourSelectionRecyclerAdapter(constant.color)
            colorRecycler.adapter = adapterProvince
        }
    }