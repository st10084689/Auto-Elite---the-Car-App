package com.harmless.autoelitekotlin.view.activities.SellActivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivitySellSelectModelBinding
import com.harmless.autoelitekotlin.databinding.ActivitySellSelectVariantBinding
import com.harmless.autoelitekotlin.model.CarModel
import com.harmless.autoelitekotlin.view.adapters.ModelSellAdapter
import com.harmless.autoelitekotlin.view.adapters.VariantSellAdapter
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedModel

class SellSelectVariantActivity : AppCompatActivity() {

    private var selectedVariant: List<String>? = null
    private lateinit var variantRecycler: RecyclerView
    private lateinit var continueButton: AppCompatButton
    private lateinit var binding: ActivitySellSelectVariantBinding
    private lateinit var viewModel: SellCarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySellSelectVariantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }
    private fun init() {
        variantRecycler = binding.sellSelectionRecyclerview
        continueButton = binding.applyBtn
        viewModel = SellCarViewModel()

        selectedVariant = viewModel.getCarVariants(SellCarViewModel.SellSession.selectedModel!!)
        setupVariantRecycler(selectedVariant!!)
        setupContinueButton()
    }

    private fun setupVariantRecycler(variant: List<String>) {
        variantRecycler.layoutManager = LinearLayoutManager(this)
        variantRecycler.adapter = VariantSellAdapter(variant) { variant ->
            SellCarViewModel.SellSession.selectedVariant = variant
        }
    }

    private fun setupContinueButton() {
        continueButton.setOnClickListener {

            Log.d(TAG, "setupContinueButton: ${SellCarViewModel.SellSession.selectedModel}")


            if (selectedModel != null) {
//                Toast.makeText(this, "Selected: ${SellCarViewModel.SellSession.selectedModel}", Toast.LENGTH_SHORT).show()
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