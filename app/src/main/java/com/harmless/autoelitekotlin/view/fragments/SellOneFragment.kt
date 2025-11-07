package com.harmless.autoelitekotlin.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.FragmentSellOneBinding
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel

class SellOneFragment : Fragment() {

    private var _binding: FragmentSellOneBinding? = null
//    private val viewModel: SellCarViewModel by activityViewModels()
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSellOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

    }

    companion object {
        private const val TAG = "SellOneFragment"
    }
}