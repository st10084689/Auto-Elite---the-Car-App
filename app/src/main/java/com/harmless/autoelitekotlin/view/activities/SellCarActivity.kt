package com.harmless.autoelitekotlin.view.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.harmless.autoelitekotlin.R
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
     var selectedProvince: String= ""
     var selectedArea: String= ""
     var selectedMileage: String= ""
     var selectedMobile: String= ""


    //getting the edit texts
    private lateinit var brandText: TextView
    private lateinit var modelText: TextView
    private lateinit var variantText: TextView
    private lateinit var provinceText: TextView
    private lateinit var areaText: TextView
    private lateinit var mobileText : TextView
    private lateinit var mileageText: TextView

    lateinit var nextButton:ImageButton//for the bottom button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_car)
        init()
    }

    private fun init() {
        //initialising the textViews
        brandText = findViewById(R.id.brand_text)
        modelText = findViewById(R.id.model_text)
        variantText = findViewById(R.id.variant_text)
        mileageText = findViewById(R.id.mileage_text)
        provinceText = findViewById(R.id.province_text)
        areaText = findViewById(R.id.area_text)
        mobileText = findViewById(R.id.mobile_text)

        //initialising the next button
        nextButton = findViewById(R.id.next_button)

        //setting up the spinners texts
        val brandSpinner = findViewById<Spinner>(R.id.brand_spinner)
        val modelSpinner = findViewById<Spinner>(R.id.model_spinner)
        val variantSpinner = findViewById<Spinner>(R.id.variant_spinner)
        val provinceSpinner = findViewById<Spinner>(R.id.province_spinner)
        val areaSpinner = findViewById<Spinner>(R.id.area_spinner)

        //setting up the edit texts
        val mobileNumberEditText = findViewById<EditText>(R.id.mobile_edit_text)
        val mileageEditText = findViewById<EditText>(R.id.mileage_edit_text)

        val viewModel = SellViewModel()

        viewModel.getProvinces(object : SellViewModel.ProvincesCallback {
            override fun onProvincesLoaded(provinces: List<Province>) {
                provinceList = provinces
         
                val provinceAdapter = SpinnerAdapter(applicationContext, viewModel.toStringProvinceNames(provinceList!!))
                provinceSpinner.adapter = provinceAdapter

                }
        })
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

        provinceSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedText = parent.getItemAtPosition(position).toString()
                selectedProvince = selectedText
               val cityList =  viewModel.getChosenCities(selectedText,provinceList!!)
                val cityAdapter = SpinnerAdapter(applicationContext,cityList)
                areaSpinner.adapter = cityAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        areaSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedText = parent.getItemAtPosition(position).toString()
                selectedArea = selectedText

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


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

        nextButton.setOnClickListener {
            selectedMileage = mileageEditText.text.toString()
            selectedMobile = mobileNumberEditText.text.toString()

           var valid =  isValid(selectedBrands, selectedModel, selectedVariants, selectedMileage, selectedProvince, selectedArea, selectedMobile)
            if(valid){
               val toAdditionalInformation = Intent(applicationContext, AdditionalSellCar::class.java)
                toAdditionalInformation.putExtra("brand", selectedBrands)
                toAdditionalInformation.putExtra("model", selectedModel)
                toAdditionalInformation.putExtra("variant", selectedVariants)
                toAdditionalInformation.putExtra("province", selectedProvince)
                toAdditionalInformation.putExtra("area", selectedArea)
                toAdditionalInformation.putExtra("mobile", selectedMobile)
                toAdditionalInformation.putExtra("mileage", selectedMileage)
                startActivity(toAdditionalInformation)
            }
            else{
                Toast.makeText(applicationContext, "Fill in all the empty fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValid(brand:String, model:String, variant:String, mileage:String,province:String, area:String, mobile:String):Boolean{
        var isValid: Boolean
        if(brand.isEmpty()){
          brandText.setTextColor(Color.parseColor("#fc1703"))
          isValid= false
      }
        else{
            brandText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
          isValid= true
      }
        if(model.isEmpty()){
            modelText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            modelText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(variant.isEmpty()){
            variantText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            variantText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(mileage.isEmpty()){
            mileageText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            mileageText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(province.isEmpty()){
            provinceText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            provinceText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(area.isEmpty()){
            areaText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            areaText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }
        if(mobile.isEmpty()){
            mobileText.setTextColor(Color.parseColor("#fc1703"))
            isValid= false
        }
        else{
            mobileText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid= true
        }

        return isValid
    }


}

