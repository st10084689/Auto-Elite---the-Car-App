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
import com.google.firebase.database.DatabaseError
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivitySellSelectModelBinding
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.model.CarModel
import com.harmless.autoelitekotlin.view.adapters.ModelSellAdapter
import com.harmless.autoelitekotlin.viewModel.CarBrandCallback
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel

class SellSelectModelActivity : AppCompatActivity() {

    private var selectedModel: List<CarModel>? = null
    private lateinit var brandRecycler: RecyclerView
    private lateinit var continueButton: AppCompatButton
    private lateinit var binding: ActivitySellSelectModelBinding
    private lateinit var viewModel: SellCarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySellSelectModelBinding.inflate(layoutInflater)
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

        selectedModel = viewModel.getCarModels(SellCarViewModel.SellSession.selectedBrand!!)
        setupModelRecycler(selectedModel!!)
        setupContinueButton()
    }

    private fun setupModelRecycler(model: List<CarModel>) {
        brandRecycler.layoutManager = LinearLayoutManager(this)
        brandRecycler.adapter = ModelSellAdapter(model) { model ->
            SellCarViewModel.SellSession.selectedModel = model
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