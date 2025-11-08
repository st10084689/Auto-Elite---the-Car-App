package com.harmless.autoelitekotlin.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.database.DatabaseError
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.utils.Constants
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.view.activities.FilterActivities.MakeAndModel
import com.harmless.autoelitekotlin.view.activities.FilterActivities.PriceSelection
import com.harmless.autoelitekotlin.view.activities.ShowMore
import com.harmless.autoelitekotlin.view.activities.VehicleList
import com.harmless.autoelitekotlin.view.activities.FilterActivities.YearSelection
import com.harmless.autoelitekotlin.view.adapters.SpinnerAdapter
import com.harmless.autoelitekotlin.viewModel.CarViewModel
import java.lang.StringBuilder

class CarFragment : Fragment(), CarViewModel.CarsCallback {

    companion object{
        private val TAG = "CAR FRAGMENT"
    }

//    private var selectedBrand: String? = null
//    private var selectedModel: String? = null
//    private var selectedYear: String? = null
//    private var selectedColor: String? = null
//    private var selectedDriveTrain: String? = null
//    private var selectedLocation: String? = null
//    private var selectedMinimumMileage: Int? = null
//    private var selectedMaximumMileage: Int? = null
//    private var selectedPrice: Double? = null
//    private var selectedTransmission: String? = null
    private var viewModel: CarViewModel = CarViewModel()
    private lateinit var carBrandTxt: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car, container, false)

        init(view)
        return view
    }

   private fun init(view: View) {
        val carBrandBtn = view.findViewById<RelativeLayout>(R.id.carBrandBtn)
        val priceBtn = view.findViewById<RelativeLayout>(R.id.priceBrandSpinner)
        val typeSpinner = view.findViewById<Spinner>(R.id.typeBrandSpinner)
        val yearRelative = view.findViewById<RelativeLayout>(R.id.yearBrandSpinner)
        val mileageSpinner = view.findViewById<Spinner>(R.id.mileageBrandSpinner)
        carBrandTxt = view.findViewById(R.id.CarBrandTxt)
        val searchBtn = view.findViewById<Button>(R.id.SearchBtn)
        val showMoreTxt = view.findViewById<TextView>(R.id.showMoreId)

        val constants = Constants()

        //setting up the car brand
        carBrandBtn.setOnClickListener{
            val toCarBrandList = Intent(view.context, MakeAndModel::class.java)
            startActivity(toCarBrandList)
        }

       yearRelative.setOnClickListener{
           val toYearSelection = Intent(view.context, YearSelection::class.java)
           startActivity(toYearSelection)
       }
       priceBtn.setOnClickListener{
           val toPriceSelection = Intent(view.context, PriceSelection::class.java)
           startActivity(toPriceSelection)
       }
       showMoreTxt.setOnClickListener{
           val toShowMore = Intent(view.context, ShowMore::class.java)
           startActivity(toShowMore)
       }



        val driveTrainAdapter = SpinnerAdapter(view.context, constants.driveTrain)//setting the drive train spinner
        typeSpinner.adapter = driveTrainAdapter
       for (items in constants.driveTrain){ //A forLoop to to see if an item is already is already selected
           if(items == SelectedValues.selectedType){
               typeSpinner.setSelection(SelectedValues.selectedType!!.indexOf(items))
           }
           else{
               typeSpinner.setSelection(0)
           }
       }

        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = driveTrainAdapter.getItem(position)
                SelectedValues.selectedType = selectedItem

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        //setting the mileage spinner
        val mileageAdapter = SpinnerAdapter(view.context, constants.mileage)
        mileageSpinner.adapter = mileageAdapter
       for (items in constants.mileage){//A forLoop to to see if an item is already is already selected
           if(items == SelectedValues.selectedMileage){

               mileageSpinner.setSelection(SelectedValues.selectedType!!.indexOf(items))
           }
           else{
               mileageSpinner.setSelection(0)
           }
       }
        mileageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val selectedItem = mileageAdapter.getItem(position)

                SelectedValues.selectedMileage = selectedItem
                SelectedValues.selectedMaxMileage = viewModel.maxMileage(selectedItem!!)
                SelectedValues.selectedMinMileage = viewModel.minMileage(selectedItem!!)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        searchBtn.setOnClickListener {
                viewModel.setSelectedCars(
                    SelectedValues.carBrandsSelected,
                    SelectedValues.selectedYear,
                    SelectedValues.selectedColor,
                    SelectedValues.selectedType,
                    SelectedValues.selectedProvince,
                    SelectedValues.selectedMinMileage,
                    SelectedValues.selectedMaxMileage,
                    SelectedValues.selectedPrice,
                    null,
                    this
                )
        }
    }


    override fun onDataLoaded(cars: List<Car>) {
        if (cars.isEmpty()) {
            Toast.makeText(requireContext(), "No cars", Toast.LENGTH_SHORT).show()
        } else {
            val toCarList = Intent(requireContext(), VehicleList::class.java)
            toCarList.putExtra("selectedCars", ArrayList(cars))
            startActivity(toCarList)
        }
    }

    override fun onCancelled(error: DatabaseError) {

    }

    override fun onResume() {
        super.onResume()
        if(SelectedValues.carBrandsSelected!=null){
            if(SelectedValues.carBrandsSelected.isNotEmpty()){

            val displayCars = StringBuilder()
            for (item in SelectedValues.carBrandsSelected){
                if (item.models!!.isEmpty()){
                    displayCars.append(item)
                }
                else{
                    displayCars.append("${item.brand}[${item.models!!.size}]")
                }

            }

                carBrandTxt.text = displayCars
                val textSize = 15
                carBrandTxt.textSize = textSize.toFloat()
                carBrandTxt.setTextColor(ContextCompat.getColor(this.requireContext(), R.color.car_screen_color))

        }}
        else{
            carBrandTxt.text = "Car Brands"
            val textSize = 20
            carBrandTxt.textSize = textSize.toFloat()

        }
    }
}
