package com.harmless.autoelitekotlin.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.utils.SelectedValues

class ColourSelectionRecyclerAdapter(private val items: List<String>):
    RecyclerView.Adapter<ColourSelectionRecyclerAdapter.ItemViewHolder>(){

    companion object{
        private val TAG = "ColourSelectionRecyclerAdapter"
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_items_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = items[position]

        holder.brandName.text = model.toString()

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->

            if(isChecked){
                SelectedValues.selectedColor.add(items[position])
            }
            else{
                SelectedValues.selectedColor.remove(items[position])
            }
            for (items in SelectedValues.selectedPrice){
                Log.d(TAG, "onBindViewHolder:  $items")
            }

        }




    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandName = itemView.findViewById<TextView>(R.id.nameTxt)
        val checkbox = itemView.findViewById<CheckBox>(R.id.checkbox)

    }
}