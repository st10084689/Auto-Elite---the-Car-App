package com.harmless.autoelitekotlin.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.harmless.autoelitekotlin.databinding.FragmentSellOneBinding
import com.harmless.autoelitekotlin.view.activities.FilterActivities.YearSelection
import com.harmless.autoelitekotlin.view.activities.SellActivities.SellSelectBrandActivity
import com.harmless.autoelitekotlin.view.activities.SellActivities.SellSelectModelActivity
import com.harmless.autoelitekotlin.view.activities.SellActivities.SellSelectVariantActivity
import com.harmless.autoelitekotlin.view.activities.SellActivities.SellSelectYear
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel

class SellOneFragment : Fragment() {

    private var _binding: FragmentSellOneBinding? = null
    private val binding get() = _binding!!

    private lateinit var brandButton: ConstraintLayout
    private lateinit var modelButton: ConstraintLayout
    private lateinit var variantButton: ConstraintLayout
    private lateinit var yearButton: ConstraintLayout

    private lateinit var brandTxt: TextView
    private lateinit var modelTxt: TextView
    private lateinit var variantTxt: TextView
    private lateinit var yearTxt: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupListeners()
        populateSavedValues()
    }

    private fun initViews() {
        brandTxt = binding.carBrand
        modelTxt = binding.carModel
        variantTxt = binding.carVariant
        yearTxt = binding.carYear

        brandButton = binding.sellCarBrandCardview
        modelButton = binding.sellCarModelCardview
        variantButton = binding.sellCarVariantCardview
        yearButton = binding.sellCarYearCardview
    }

    private fun setupListeners() {
        brandButton.setOnClickListener {
            startActivity(Intent(requireContext(), SellSelectBrandActivity::class.java))
        }

        modelButton.setOnClickListener {
            // Must select brand first
            if (SellCarViewModel.SellSession.selectedBrand == null) {
                Toast.makeText(requireContext(), "Please select a car brand first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(requireContext(), SellSelectModelActivity::class.java))
        }

        variantButton.setOnClickListener {
            // Must select brand and model first
            if (SellCarViewModel.SellSession.selectedBrand == null ||
                SellCarViewModel.SellSession.selectedModel == null) {
                Toast.makeText(requireContext(), "Please select a brand and model first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(requireContext(), SellSelectVariantActivity::class.java))
        }

        yearButton.setOnClickListener {
            // Must select all previous first
            if (SellCarViewModel.SellSession.selectedBrand == null ||
                SellCarViewModel.SellSession.selectedModel == null ||
                SellCarViewModel.SellSession.selectedVariant == null) {
                Toast.makeText(requireContext(), "Please complete all previous selections first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(requireContext(), SellSelectYear::class.java))
        }
    }


    private fun populateSavedValues() {
        // Use safe calls with default placeholders
        val brand = SellCarViewModel.SellSession.selectedBrand?.brand ?: "Select Brand"
        val model = SellCarViewModel.SellSession.selectedModel?.name ?: "Select Model"
        val variant = SellCarViewModel.SellSession.selectedVariant ?: "Select Variant"
        val year = SellCarViewModel.SellSession.selectedYear?.toString() ?: "Select Year"

        brandTxt.text = brand
        modelTxt.text = model
        variantTxt.text = variant
        yearTxt.text = year
    }

    override fun onResume() {
        super.onResume()

        populateSavedValues()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "SellOneFragment"
    }
}
