package com.harmless.autoelitekotlin.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.model.CarModels
import com.harmless.autoelitekotlin.model.utils.SelectedValues

private const val TAG = "SelectedModelNestedRecyclerAdapter"
class SelectedModelNestedRecyclerAdapter(private val carBrand : String, private val carModels : List<CarModels>, private val mainPostion:Int) :
    RecyclerView.Adapter<SelectedModelNestedRecyclerAdapter.ItemHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.model_card, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val model = carModels!!.get(position)
        holder.modelText.text = model.model


        holder.modelCheck.setOnClickListener {
            val isChecked = (it as CheckBox).isChecked

            if (isChecked) {
                Log.d(TAG, "onBindViewHolder modelCheck: entered ")


             addValueToKey(carBrand, model)

                for (item in SelectedValues.carBrandsSelected){
                    Log.d(TAG, "onBindViewHolder: ${item.name}")
                    for (i in item.models!!){
                    Log.d(TAG, "onBindViewHolder: $i")
                }}



            } else {

            }
        }
    }

    override fun getItemCount(): Int {
        if(carModels!=null){
        return carModels!!.size
        }
        return 0
    }

    fun addValueToKey(name: String, model: CarModels) {
        val existingBrand = SelectedValues.carBrandsSelected.find { it.name == name }

        if (existingBrand != null) {
            existingBrand.models!!.add(model)
        } else {
            val newBrand = CarBrand(mutableListOf(model), name)
            SelectedValues.carBrandsSelected.add(newBrand)
        }
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val modelCheck: CheckBox = itemView.findViewById(R.id.ModelCheckBox)
        val modelText: TextView = itemView.findViewById(R.id.modelText)
    }
}