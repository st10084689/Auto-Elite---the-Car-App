package com.harmless.autoelitekotlin.view.activities.SellActivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseError
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivitySellSelectBrandBinding
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.view.adapters.BrandSellAdapter
import com.harmless.autoelitekotlin.viewModel.CarBrandCallback
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel

class SellSelectBrandActivity : AppCompatActivity() {

    private var selectedBrand: CarBrand? = null
    private lateinit var brandRecycler: RecyclerView
    private lateinit var continueButton: AppCompatButton
    private lateinit var binding: ActivitySellSelectBrandBinding
    private lateinit var viewModel: SellCarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySellSelectBrandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
    }

    private fun init() {
        brandRecycler = binding.sellSelectionRecyclerview
        continueButton = binding.applyBtn
        viewModel = SellCarViewModel()

        viewModel.getCarBrand(object : CarBrandCallback {
            override fun onCarBrandLoaded(carBrands: List<CarBrand>) {
                setupBrandRecycler(carBrands)
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        setupContinueButton()
    }

    private fun setupBrandRecycler(brands: List<CarBrand>) {
        brandRecycler.layoutManager = LinearLayoutManager(this)
        brandRecycler.adapter = BrandSellAdapter(brands) { brand ->
            selectedBrand = brand
            SellCarViewModel.SellSession.selectedBrand = brand
        }
    }

    private fun setupContinueButton() {
        continueButton.setOnClickListener {
            val brand = selectedBrand
            Log.d(TAG, "setupContinueButton: ${SellCarViewModel.SellSession.selectedBrand }")
            Log.d(TAG, "setupContinueButton: $brand")

            if (brand != null) {
//                Toast.makeText(this, "Selected: ${brand.brand}", Toast.LENGTH_SHORT).show()

                val toSellFragment = Intent(this, SellCarActivity::class.java)
                startActivity(toSellFragment)

            } else {
                Toast.makeText(this, "Please select a brand", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TAG = "SellSelectActivity"
    }
}