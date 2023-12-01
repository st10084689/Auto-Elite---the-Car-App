package com.harmless.autoelitekotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseError
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.view.recyclerViews.YearSelectionRecyclerAdapter
import com.harmless.autoelitekotlin.viewModel.YearSelectionViewModel


class YearSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_year_selection)
        init()
    }

    private fun init() {
        val yearRecycler = findViewById<RecyclerView>(R.id.YearsRecyclerView)

        val yearSelection = YearSelectionViewModel()

        yearSelection.loadYears(object : YearSelectionViewModel.YearsCallback {
            override fun onYearsLoaded(years: List<Int>) {
                yearRecycler.setHasFixedSize(true)
                yearRecycler.layoutManager  = LinearLayoutManager(applicationContext)
                val adapterYear = YearSelectionRecyclerAdapter(years)
                yearRecycler.adapter = adapterYear
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}