package com.harmless.autoelitekotlin.view.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivitySellCarBinding
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.model.CarModels
import com.harmless.autoelitekotlin.model.Province
import com.harmless.autoelitekotlin.view.adapters.SpinnerAdapter
import com.harmless.autoelitekotlin.viewModel.SellViewModel


class SellCarActivity : AppCompatActivity() {
    companion object {
        val TAG = "SellCarActivity"
    }

    var provinceList: List<Province>? = null
    var carList: ArrayList<CarBrand>? = null
    var modelList : List<CarModels>? = null

    //all the string from the selected string values
     var selectedBrands: String = ""
     var selectedModel: String= ""
     var selectedVariants: String= ""
     var selectedColor: String = ""
    var selectedTransmission: String = ""
    var selectedBodyType: String = ""
    var selectedWheelDrive: String =""
    var selectedYear : String = ""




    lateinit var nextButton: Button//for the bottom button

    private lateinit var binding: ActivitySellCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        //initialising the button
        nextButton = binding.nextButton


        //setting up the spinners texts
        val brandSpinner = findViewById<Spinner>(R.id.brand_spinner)
        val modelSpinner = findViewById<Spinner>(R.id.model_spinner)
        val variantSpinner = findViewById<Spinner>(R.id.variant_spinner)


        val viewModel = SellViewModel()


        viewModel.setCarBrand(object : SellViewModel.CarBrandsCallBack{
            override fun onCarBrandLoaded(carBrands: ArrayList<CarBrand>) {

                Log.d(TAG, "onCarBrandLoaded: entered")
                carList = carBrands

                val brandAdapter = SpinnerAdapter(applicationContext, viewModel.toStringCarBrands(carList!!))
                brandSpinner.adapter = brandAdapter
                
                for (items in carList!!){
                    Log.d(TAG, "onCarBrandLoaded: ${items.name}")
                }
            }
        })

        var selectedText = ""




        brandSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedText = parent.getItemAtPosition(position).toString()
                selectedBrands = selectedText
            val modelListString =  viewModel.getChosenModelsNames(selectedText,carList!!)
            val modelAdapter = SpinnerAdapter(applicationContext, modelListString )
            modelSpinner.adapter = modelAdapter

                modelList = viewModel.getChosenModelsList(selectedText,carList!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }

        }

        modelSpinner?.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedText = parent.getItemAtPosition(position).toString()
                selectedModel = selectedText
                val variantList =  viewModel.getChosenVariants(selectedText,modelList!!)
                val variantAdapter = SpinnerAdapter(applicationContext, variantList )
                variantSpinner.adapter = variantAdapter
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        variantSpinner?.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedText = parent.getItemAtPosition(position).toString()
                selectedVariants = selectedText

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val constants = com.harmless.autoelitekotlin.model.utils.Constants()

        val colorAdapter = SpinnerAdapter(applicationContext, constants.color)//setting up the adapter for the color spinner
        binding.colorSpinner.adapter = colorAdapter

        binding.colorSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedText = parent.getItemAtPosition(position).toString()
                selectedColor = selectedText

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val yearAdapter = SpinnerAdapter(applicationContext, constants.year)//setting up the adapter for the color spinner
        binding.yearSpinner.adapter = yearAdapter

        binding.yearSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedText = parent.getItemAtPosition(position).toString()
                selectedYear = selectedText

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }




        val transmissionColor = SpinnerAdapter(applicationContext, constants.transmission)
        binding.transmissionSpinner.adapter = transmissionColor

        binding.transmissionSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedText = parent.getItemAtPosition(position).toString()
                selectedTransmission= selectedText

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val bodyTypeAdapter = SpinnerAdapter(applicationContext, constants.bodyType)
        binding.bodyTypeSpinner.adapter = bodyTypeAdapter

        binding.bodyTypeSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedText = parent.getItemAtPosition(position).toString()
                selectedBodyType= selectedText

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val wheelTypeAdapter = SpinnerAdapter(applicationContext, constants.driveTrain)
        binding.wheelSpinner.adapter = wheelTypeAdapter
        binding.wheelSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedText = parent.getItemAtPosition(position).toString()
                selectedWheelDrive= selectedText

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        nextButton.setOnClickListener {

           var valid =  isValid(selectedBrands, selectedModel, selectedYear, selectedVariants,  selectedColor, selectedTransmission, selectedBodyType, selectedWheelDrive)
            if(valid){
               val toAdditionalInformation = Intent(applicationContext, ImageSellCar::class.java)
                toAdditionalInformation.putExtra("brand", selectedBrands)
                toAdditionalInformation.putExtra("model", selectedModel)
                toAdditionalInformation.putExtra("year", selectedYear)
                toAdditionalInformation.putExtra("variant", selectedVariants)
                toAdditionalInformation.putExtra("color", selectedColor)
                toAdditionalInformation.putExtra("transmission", selectedTransmission)
                toAdditionalInformation.putExtra("bodyType", selectedBodyType)
                toAdditionalInformation.putExtra("wheelDrive", selectedWheelDrive)
                startActivity(toAdditionalInformation)
            }
            else{
                Toast.makeText(applicationContext, "Fill in all the empty fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValid(brand:String, model:String, year:String, variant: String, color:String, transmission:String, bodyType:String, wheelDrive:String):Boolean{
        var isValid: Boolean
        if(brand.isEmpty()){
          binding.brandText.setTextColor(Color.parseColor("#fc1703"))
          isValid= false
      }
        else{
            binding.brandText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
          isValid= true
      }
        if(model.isEmpty()){
            binding.modelText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            binding.modelText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(year.isEmpty()){
            binding.yearText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            binding.yearText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(variant.isEmpty()){
            binding.variantText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            binding.variantText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(color.isEmpty()){
            binding.colorText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            binding.colorText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(transmission.isEmpty()){
            binding.transmissionText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            binding.transmissionText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(bodyType.isEmpty()){
            binding.bodyTypeText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            binding.bodyTypeText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(wheelDrive.isEmpty()){
            binding.wheelText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            binding.wheelText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }

        return isValid
    }


}

