package com.harmless.autoelitekotlin.view.activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.utils.*
import java.util.UUID

class ImageSellCar : AppCompatActivity() {

    companion object{
        val TAG = "AdditionalSellCar"
        const val FIRST_IMAGE = 0
        const val SECOND_IMAGE = 1
        const val THIRD_IMAGE = 2
        const val FOURTH_IMAGE = 3
    }

    private lateinit var brand:String
    private lateinit var model:String
    private lateinit var variant:String
    private lateinit var year: String
    private lateinit var color: String
    private lateinit var transmission: String
    private lateinit var bodyType: String
    private lateinit var wheelDrive: String


    //referencing the storage
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    //setting up the images
    private lateinit var firstImage: ImageView
    private lateinit var secondImage: ImageView
    private lateinit var thirdImage: ImageView
    private lateinit var fourthImage: ImageView

    //the textviews of the screen
    private lateinit var colorText: TextView
    private lateinit var descriptionText :TextView

    //the button
    private lateinit var arrowButton: Button
    
    private var imageList = mutableListOf<Uri>()
    private var imageListStrings = mutableListOf<String>()

    private lateinit var selectedColor: String
    private lateinit var selectedDescription: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_sell_car)
        init()
    }

    private fun init(){



        val constants = Constants()


        //initialising the image buttons
        val frontBtn = findViewById<Button>(R.id.frontBtn)
        val rearBtn = findViewById<Button>(R.id.rearBtn)
        val leftBtn = findViewById<Button>(R.id.leftBtn)
        val rightBtn = findViewById<Button>(R.id.rightBtn)

        //initialising the imageview for the the images
        firstImage = findViewById(R.id.firstImage)
        secondImage = findViewById(R.id.secondImage)
        thirdImage = findViewById(R.id.thirdImage)
        fourthImage = findViewById(R.id.fourImage)

        //initialising the text Views
        colorText = findViewById(R.id.color_text)
        descriptionText = findViewById(R.id.description_text)

        //initialising the arrow button
        arrowButton = findViewById(R.id.done_btn)

        frontBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), FIRST_IMAGE)

        }
            rearBtn.setOnClickListener{
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SECOND_IMAGE)

            }
        leftBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), THIRD_IMAGE)
        }
            rightBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), FOURTH_IMAGE)
            }

        arrowButton.setOnClickListener {
//            val valid = isValid(selectedColor)
//            if(valid){
//                val viewModel = SellViewModel()
//                for (i in 0..3){
//                    val imageName =  uploadImage(i, imageList[i])
//                    addImage(i,imageName)
//                }
//
//                viewModel.writeToCar(brand, model, province, area, mileage.toInt(), imageListStrings, "clean",10000000.0,true, selectedColor, "automatic", "rear",2022)
                val toMainActivity = Intent(applicationContext, MainActivity::class.java)
                startActivity(toMainActivity)
            }
        }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FIRST_IMAGE && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            Log.d(TAG, "onActivityResult: $FIRST_IMAGE")
            Log.d(TAG, "onActivityResult: ${imageUri.toString()}")
            addImageUri(FIRST_IMAGE, imageUri!!)
            firstImage
        }
        if (requestCode == SECOND_IMAGE && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            Log.d(TAG, "onActivityResult: $SECOND_IMAGE")
            addImageUri(SECOND_IMAGE, imageUri!!)
        }
        if (requestCode == THIRD_IMAGE && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            Log.d(TAG, "onActivityResult: $THIRD_IMAGE")
            addImageUri(THIRD_IMAGE, imageUri!!)
        }
        if (requestCode == FOURTH_IMAGE && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            Log.d(TAG, "onActivityResult: $FOURTH_IMAGE")
            addImageUri(FOURTH_IMAGE, imageUri!!)
        }
    }
    
    private fun addImage(position: Int,imageString:String){
        if(imageListStrings.size>position){
            Log.d(TAG, "addImage: added $position")
            imageListStrings[position]= imageString
        }
        else{
            Log.d(TAG, "addImage: added_____else $position")
            imageListStrings.add(imageString)
        }
    }

    private fun addImageUri(position: Int,imageUri:Uri){

        Log.d(TAG, "addImageUri: added position ${imageList.size}")
        if(imageList.size>position){
            Log.d(TAG, "addImageUri: added position $position")
            imageList[position]= imageUri
        }
        else{
            Log.d(TAG, "addImageUri: added else statement $position")
            imageList.add(imageUri)
        }
    }


    private fun isValid(color:String):Boolean {
        var isValid: Boolean
        if (color.isEmpty()) {
            colorText.setTextColor(Color.parseColor("#fc1703"))
            isValid = false
        } else {
            colorText.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            isValid = true
        }
        if(imageList.isNotEmpty()&&imageList.size>=4){
            Log.d(TAG, "isValid: valid soldier")
            isValid = true

        }
        else
        {
            Toast.makeText(applicationContext, "fill in all the images", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid
    }

    private fun uploadImage( position: Int ,imageUri: Uri?):String{
        lateinit var imageName: String
        if (imageUri != null) {
            // Generates a random UUID to use as the image name in Firebase Storage
             imageName = "${UUID.randomUUID()}.jpg"

            // Reference to the image in Firebase Storage
            val imageRef: StorageReference = storageRef.child("images/$imageName")

            // Upload the image to Firebase Storage
            imageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    // Image uploaded successfully, now you can get the download URL
//                    imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
//                        val imageUrl = downloadUri.toString()
//                        Log.d(TAG, "Image URL for  $imageUrl")
//
                    Log.d(TAG, "uploadImage: $taskSnapshot")


//                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error uploading image", e)
                }
        }
        return imageName
    }

//    viewModel.getProvinces(object : SellViewModel.ProvincesCallback {
//        override fun onProvincesLoaded(provinces: List<Province>) {
//            provinceList = provinces
//
//            val provinceAdapter = SpinnerAdapter(applicationContext, viewModel.toStringProvinceNames(provinceList!!))
//            provinceSpinner.adapter = provinceAdapter
//
//        }
//    })
//
//    provinceSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//            selectedText = parent.getItemAtPosition(position).toString()
//            selectedProvince = selectedText
//            val cityList =  viewModel.getChosenCities(selectedText,provinceList!!)
//            val cityAdapter = SpinnerAdapter(applicationContext,cityList)
//            areaSpinner.adapter = cityAdapter
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>) {
//
//        }
//    }
//
//    areaSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//            selectedText = parent.getItemAtPosition(position).toString()
//            selectedArea = selectedText
//
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>) {
//
//        }
//    }

}