package com.harmless.autoelitekotlin.view.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivityAdditionalRegisterBinding
import com.harmless.autoelitekotlin.databinding.ActivityRegisterBinding
import com.harmless.autoelitekotlin.model.User
import java.util.UUID

class AdditionalRegister : AppCompatActivity() {
    private lateinit var binding: ActivityAdditionalRegisterBinding
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditionalRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        binding.continueBtn.setOnClickListener {
            val database = Firebase.database.reference
            if(isValid()) {
                val user = User(binding.displayName.text.toString(), uploadImage(uri))
                val userName = Firebase.auth.currentUser
                if (userName != null) {
                    val uid = userName.uid
                    database.child("users").child(uid).setValue(user)
                    val toMain = Intent(applicationContext, MainActivity::class.java)
                    startActivity(toMain)
                } else {
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageCardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                10
            )
        }
    }

    private fun uploadImage( imageUri: Uri?):String{
        lateinit var imageName: String
        if (imageUri != null) {
            imageName = "${UUID.randomUUID()}.jpg"

            // Reference to the image in Firebase Storage
            val imageRef: StorageReference = storageRef.child("usersImage/$imageName")

            // Upload the image to Firebase Storage
            imageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->

                    Log.d(AdditionalSellCar.TAG, "uploadImage: $taskSnapshot")

                }
                .addOnFailureListener { e ->
                    Log.e(AdditionalSellCar.TAG, "Error uploading image", e)
                }
        }
        return imageName
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            uri = data.data
            Log.d(AdditionalSellCar.TAG, "onActivityResult: ${AdditionalSellCar.FIRST_IMAGE}")

            Glide.with(applicationContext)
                .load(uri)
                .centerCrop()
                .into(binding.imageId)
        }
    }

    private fun isValid(): Boolean{
        var isValid = true
        if(binding.displayName.text.isEmpty()){
            isValid = false
        }

        return isValid
    }
}