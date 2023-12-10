package com.harmless.autoelitekotlin.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.viewModel.MakeAndModelViewModel
import com.harmless.autoelitekotlin.viewModel.TAG

class SelectBrandRecyclerAdapter(private val cars: List<CarBrand>) :
    RecyclerView.Adapter<SelectBrandRecyclerAdapter.ItemViewHolder>() {

    private var previousExpanded = -1
    private var expanded = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.make_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = cars[position]
        model.isExpandable = true

        holder.nestedRecycler.visibility = View.GONE//setting teh recycler to be normally Gone
        model.isExpandable = MakeAndModelViewModel().isExpandable(model.models)

        holder.carBrand.text = model.name

        val adapter = SelectedModelNestedRecyclerAdapter(model.name, model.models,position)
        holder.nestedRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.nestedRecycler.setHasFixedSize(true)
        holder.nestedRecycler.adapter = adapter

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (model.isExpandable) {
                Log.d(TAG, "onBindViewHolder: opened")
                holder.nestedRecycler.visibility = View.VISIBLE
                model.isExpandable = false

            } else {
                Log.d(TAG, "onBindViewHolder: closed")
                holder.nestedRecycler.visibility = View.GONE
                model.isExpandable = true
            }
        }
        
        holder.contentLayout.setOnClickListener {
            if (model.isExpandable) {
                Log.d(TAG, "onBindViewHolder: opened")
                holder.nestedRecycler.visibility = View.VISIBLE
                model.isExpandable = false
                previousExpanded = expanded
            } else if(!model.isExpandable) {
                Log.d(TAG, "onBindViewHolder: closed")
                holder.nestedRecycler.visibility = View.GONE
                model.isExpandable = true
            }
        }


    }


    override fun getItemCount(): Int = cars.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentLayout: RelativeLayout = itemView.findViewById(R.id.ListItem)
        val expandableLayout: RelativeLayout = itemView.findViewById(R.id.NestListView)
        val arrow: ImageView = itemView.findViewById(R.id.ChevronDown)
        val carBrand: TextView = itemView.findViewById(R.id.CarBrandTxt)
        val nestedRecycler: RecyclerView = itemView.findViewById(R.id.CarBrandNestedRecylcer)
        val checkbox: CheckBox = itemView.findViewById(R.id.checkVarient)
    }
}