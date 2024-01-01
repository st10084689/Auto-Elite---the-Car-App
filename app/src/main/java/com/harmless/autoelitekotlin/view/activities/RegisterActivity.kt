package com.harmless.autoelitekotlin.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivityLoginBinding
import com.harmless.autoelitekotlin.databinding.ActivityRegisterBinding
import com.harmless.autoelitekotlin.model.utils.Constants

class RegisterActivity : AppCompatActivity() {

    companion object{
         val TAG = "Register"
    }

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var email: String
    private lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        val constants = Constants()
        val sharePref = getSharedPreferences(constants.sharedPrefString, Context.MODE_PRIVATE)


        binding.registerBtn.setOnClickListener {

            email =  binding.email.text.toString()
            password = binding.password.text.toString()

            Log.d(TAG, "init: ${isValid()}")
            if(isValid()){
                registerUser(email, password)
            }
        }
    }

    private fun registerUser(email:String,password:String){
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    val toAdditionalRegister = Intent(applicationContext, AdditionalRegister::class.java)
                    startActivity(toAdditionalRegister)
                } else {

                }
            }
    }

    private fun isValid(): Boolean{
        var isValid: Boolean = true
        if(binding.email.text.isEmpty()){
            isValid = false
        }
        if(binding.password.text.isEmpty()){
            isValid = false
        }
        return isValid
    }
}