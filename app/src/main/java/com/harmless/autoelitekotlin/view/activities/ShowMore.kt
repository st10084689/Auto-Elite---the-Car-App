package com.harmless.autoelitekotlin.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.utils.Constants
import com.harmless.autoelitekotlin.view.activities.FilterActivities.ColorSelection
import com.harmless.autoelitekotlin.view.activities.FilterActivities.MakeAndModel
import com.harmless.autoelitekotlin.view.activities.FilterActivities.PriceSelection
import com.harmless.autoelitekotlin.view.activities.FilterActivities.ProvinceSelection
import com.harmless.autoelitekotlin.view.activities.FilterActivities.YearSelection
import com.harmless.autoelitekotlin.view.adapters.SpinnerAdapter
import com.harmless.autoelitekotlin.viewModel.CarViewModel

class ShowMore : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_more)
        init()
    }

    private fun init(){
        //init the spinner values
        val carTypeSpinner = findViewById<Spinner>(R.id.TypeBtn)
        val newOrUsedSpinner = findViewById<Spinner>(R.id.NewOrUsedBtn)
        val mileageSpinner = findViewById<Spinner>(R.id.MileageBtn)
        val fuelTypeSpinner = findViewById<Spinner>(R.id.FuelTypeBtn)

        //init the buttons
        val apply = findViewById<Button>(R.id.applyBtn)
        val back = findViewById<Button>(R.id.backBtn)

        //init the relative values
        val carBrandRel = findViewById<RelativeLayout>(R.id.carBrandBtn)
        val yearRel = findViewById<RelativeLayout>(R.id.YearBtn)
        val priceRel = findViewById<RelativeLayout>(R.id.PriceBtn)
        val provinceRel = findViewById<RelativeLayout>(R.id.LocationBtn)
        val colourRel = findViewById<RelativeLayout>(R.id.ColourBtn)

        val constant = Constants()

        val typeAdapter = SpinnerAdapter(applicationContext, constant.driveTrain)
        val newUsedAdapter = SpinnerAdapter(applicationContext, constant.newOrUsed)
        val mileageAdapter = SpinnerAdapter(applicationContext, constant.mileage)
        val fuelTypeAdapter = SpinnerAdapter(applicationContext, constant.fuelType)

        carTypeSpinner.adapter = typeAdapter
        newOrUsedSpinner.adapter = newUsedAdapter
        mileageSpinner.adapter = mileageAdapter
        fuelTypeSpinner.adapter = fuelTypeAdapter


        fuelTypeSpinner.setSelection(0)
        fuelTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = typeAdapter.getItem(position)
                SelectedValues.selectedFuelType = selectedItem!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        mileageSpinner.setSelection(0)
        mileageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = typeAdapter.getItem(position)
                val viewModel = CarViewModel()
                SelectedValues.selectedMaxMileage = viewModel.maxMileage(selectedItem!!)
                SelectedValues.selectedMinMileage = viewModel.minMileage(selectedItem!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        newOrUsedSpinner.setSelection(0)
        newOrUsedSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = typeAdapter.getItem(position)
                SelectedValues.isNewOrUsed = selectedItem!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        carTypeSpinner.setSelection(0)
        carTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = typeAdapter.getItem(position)
                SelectedValues.selectedType = selectedItem!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        carBrandRel.setOnClickListener{
            val toCarBrand = Intent(applicationContext,  MakeAndModel::class.java)
            startActivity(toCarBrand)
        }
        yearRel.setOnClickListener{
            val toYear = Intent(applicationContext, YearSelection::class.java)
            startActivity(toYear)
        }
        provinceRel.setOnClickListener{
            val toProvince = Intent(applicationContext, ProvinceSelection::class.java)
            startActivity(toProvince)
        }
        priceRel.setOnClickListener{
            val toPrice = Intent(applicationContext, PriceSelection::class.java)
            startActivity(toPrice)
        }
        colourRel.setOnClickListener{
            val toColor = Intent(applicationContext, ColorSelection::class.java)
            startActivity(toColor)
        }

        apply.setOnClickListener{
            finish()
        }
        back.setOnClickListener{
            finish()
        }

    }
}