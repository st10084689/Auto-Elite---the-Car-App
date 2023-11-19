package com.harmless.autoelitekotlin.view.recyclerViews

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.view.MakeAndModel

private const val TAG = "SelectedModelNestedRecy"
class SelectedModelNestedRecyclerAdapter(private val carModels : MutableList<String>?) :
    RecyclerView.Adapter<SelectedModelNestedRecyclerAdapter.ItemHolder>() {

    private val TAG = "NestedCarSelectionAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.model_card, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val model = carModels?.get(position)
        holder.modelText.text = model


        holder.modelCheck.setOnClickListener {
            val isChecked = (it as CheckBox).isChecked

            if (isChecked) {

            } else {

            }
        }
    }

    override fun getItemCount(): Int {
        return carModels!!.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val modelCheck: CheckBox = itemView.findViewById(R.id.ModelCheckBox)
        val modelText: TextView = itemView.findViewById(R.id.modelText)
    }
}