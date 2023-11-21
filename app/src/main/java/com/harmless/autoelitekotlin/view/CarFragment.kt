package com.harmless.autoelitekotlin.view

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
import com.google.firebase.database.DatabaseError
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.Constants
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.viewModel.CarViewModel
import com.harmless.autoelitekotlin.viewModel.MakeAndModelViewModel

class CarFragment : Fragment(), CarViewModel.CarsCallback {

    private var selectedBrand: String? = null
    private var selectedModel: String? = null
    private var selectedYear: String? = null
    private var selectedColor: String? = null
    private var selectedDriveTrain: String? = null
    private var selectedLocation: String? = null
    private var selectedMinimumMileage: Int? = null
    private var selectedMaximumMileage: Int? = null
    private var selectedPrice: Double? = null
    private var selectedTransmission: String? = null
    private var viewModel: CarViewModel = CarViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car, container, false)

        init(view)
        return view
    }

    fun init(view: View) {
        val carBrandBtn = view.findViewById<RelativeLayout>(R.id.carBrandBtn)
        val priceBtn = view.findViewById<RelativeLayout>(R.id.priceBrandSpinner)
        val typeSpinner = view.findViewById<Spinner>(R.id.typeBrandSpinner)
        val yearSpinner = view.findViewById<RelativeLayout>(R.id.yearBrandSpinner)
        val mileageSpinner = view.findViewById<Spinner>(R.id.mileageBrandSpinner)
        val carBrandTxt = view.findViewById<TextView>(R.id.CarBrandTxt)
        val searchBtn = view.findViewById<Button>(R.id.SearchBtn)
        val showMoreTxt = view.findViewById<TextView>(R.id.showMoreId)

        val constants = Constants()

        //setting up the car brand
        carBrandBtn.setOnClickListener{
            val toCarBrandList = Intent(view.context, MakeAndModel::class.java)
            startActivity(toCarBrandList)
        }


        //setting the drive train spinner
        val driveTrainadapter: SpinnerAdapter = SpinnerAdapter(view.context, constants.driveTrain)
        typeSpinner.adapter = driveTrainadapter
        typeSpinner.setSelection(0)
        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = driveTrainadapter.getItem(position)
                selectedDriveTrain = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        //setting the mileage spinner
        val mileageAdapter = SpinnerAdapter(view.context, constants.mileage)
        mileageSpinner.adapter = mileageAdapter
        mileageSpinner.setSelection(0)
        mileageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val selectedItem = mileageAdapter.getItem(position)
//
//                selectedMileage = selectedItem

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        searchBtn.setOnClickListener {
            viewModel.setSelectedCars(
                null, null, null, null, null, null, 0, 1000000, null, null, this
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
}
