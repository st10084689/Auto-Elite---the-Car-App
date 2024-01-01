package com.harmless.autoelitekotlin.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivityLoginBinding
import com.harmless.autoelitekotlin.viewModel.TAG

class LoginActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init(){
            binding.loginBtn.setOnClickListener {
            email =  binding.email.text.toString()
            password = binding.password.text.toString()
            loginUser(email, password)
        }

        binding.googlebtn.setOnClickListener{
            googleUser()
        }

        binding.registerText.setOnClickListener {
            val toRegister = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(toRegister)
        }
    }

    private fun loginUser(email: String, password: String){//method to make the user sign in with the email and password
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    val toMainActivity = Intent(applicationContext, MainActivity::class.java)
                    startActivity(toMainActivity)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun googleUser(){//method to make thee sign in with google

            }
}