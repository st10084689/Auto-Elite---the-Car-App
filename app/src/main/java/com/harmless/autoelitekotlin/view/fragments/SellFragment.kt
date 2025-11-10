package com.harmless.autoelitekotlin.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.FragmentSellBinding
import com.harmless.autoelitekotlin.databinding.FragmentSellOneBinding
import com.harmless.autoelitekotlin.view.activities.MainActivity
import com.harmless.autoelitekotlin.view.activities.SellActivities.SellCarActivity


class SellFragment : Fragment() {

    private var _binding: FragmentSellBinding? = null
    private val binding get() = _binding!!

    private lateinit var sellButton: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setUpListeners()
    }

    private fun initViews(){
        sellButton = binding.sellCarButton
    }

    private fun setUpListeners(){
        sellButton.setOnClickListener {
            val intent = Intent(requireContext(), SellCarActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }
}