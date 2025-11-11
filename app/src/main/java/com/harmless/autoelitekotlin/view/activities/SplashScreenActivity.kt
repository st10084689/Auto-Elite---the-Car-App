package com.harmless.autoelitekotlin.view.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivitySplashScreenBinding
import com.harmless.autoelitekotlin.view.activities.LoginAcitivties.LoginActivity
import kotlin.jvm.java


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Wait 2 seconds, then check internet and login
        android.os.Handler(mainLooper).postDelayed({
            checkInternetAndProceed()
        }, 2000)
    }

    private fun checkInternetAndProceed() {
        if (isConnectedToInternet()) {
            checkLoginStatus()
        } else {
            showNoInternetDialog()
        }
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(this)
            .setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again.")
            .setCancelable(false)
            .setPositiveButton("Retry") { dialog, _ ->
                dialog.dismiss()
                checkInternetAndProceed()
            }
            .show()
    }

    private fun checkLoginStatus() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            checkUserProfile(currentUser.uid)
        } else {
            // No user logged in, go to login screen
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun checkUserProfile(uid: String) {
        val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)
        userRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                // User profile exists, go to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // User profile doesn't exist, go to UserDetailsActivity
                startActivity(Intent(this, UserDetailsActivity::class.java))
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to check user profile: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }
}