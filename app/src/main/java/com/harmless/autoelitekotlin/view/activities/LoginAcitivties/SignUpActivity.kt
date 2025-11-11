package com.harmless.autoelitekotlin.view.activities.LoginAcitivties

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivitySignUpAcitivtyBinding
import com.harmless.autoelitekotlin.view.activities.MainActivity
import com.harmless.autoelitekotlin.view.activities.UserDetailsActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpAcitivtyBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpAcitivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        binding.signUpButton.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val emailText = binding.email.text.toString().trim()
        val passwordText = binding.password.text.toString().trim()
        val confirmPasswordText = binding.confirmPassword.text.toString().trim()

        // 🔹 Validation
        when {
            emailText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty() -> {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return
            }
            passwordText != confirmPasswordText -> {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return
            }
            passwordText.length < 6 -> {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // 🔹 Create user in Firebase
        auth.createUserWithEmailAndPassword(emailText, passwordText)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()

                    // Go to MainActivity after sign-up
                    val intent = Intent(this, UserDetailsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Signup failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}