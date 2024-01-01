package com.harmless.autoelitekotlin.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseError
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.utils.SelectedValues
import com.harmless.autoelitekotlin.view.adapters.SpinnerAdapter
import com.harmless.autoelitekotlin.viewModel.SellViewModel


class SellFragment : Fragment() {

companion object{
    val TAG = "SellFragment"
}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_sell, container, false)
//        init(view)
        return view
    }

//    private fun init(view:View){
//        val provinceSpinner = view.findViewById<Spinner>(R.id.province_spinner)
//        val viewModel = SellViewModel()
//         viewModel.gettingProvinces(object : SellViewModel.provinceCallback {
//            override fun onDataLoaded(provinces: List<String>) {
//                 provinceList = provinces as MutableList<String>
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d(TAG, "onCancelled: error loading the data")
//            }
//        })
//       val spinnerAdapter = SpinnerAdapter(view.context, provinceList)
//        provinceSpinner.adapter = spinnerAdapter
//       for (items in provinceList){//A nested forLoop to to see if an item is already is already selected
//           for(selectedProvinceItems in SelectedValues.selectedProvince) {
//               if (items == selectedProvinceItems) {
//
//                   provinceSpinner.setSelection(SelectedValues.selectedType!!.indexOf(items))
//               } else {
//                   provinceSpinner.setSelection(0)
//               }
//           }
//       }
//    }


}