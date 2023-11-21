package com.harmless.autoelitekotlin.view.recyclerViews

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
import com.harmless.autoelitekotlin.view.MakeAndModel
import com.harmless.autoelitekotlin.viewModel.MakeAndModelViewModel

class SelectBrandRecyclerAdapter(private val cars: List<CarBrand>) :
    RecyclerView.Adapter<SelectBrandRecyclerAdapter.ItemViewHolder>() {



    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.make_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = cars[position]


        // Set the text for CarBrand TextView
        holder.carBrand.text= model.name

        // Handle checkbox click listener to expand/collapse the item
        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked =  MakeAndModelViewModel().IsExpandable(position)

        holder.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Expand the item and set it as the expanded position
                val previouslyExpandedPosition = expandedPosition
                expandedPosition = position
                if (previouslyExpandedPosition != -1) {
                    notifyItemChanged(previouslyExpandedPosition)
                }
                holder.nestedRecycler.visibility = View.VISIBLE
            } else {
                // Collapse the item and reset the expanded position
                expandedPosition = -1
                holder.nestedRecycler.visibility = View.GONE
            }
        }

        var isExpandable =  MakeAndModelViewModel().IsExpandable(position)
        holder.nestedRecycler.visibility =
            if (isExpandable && expandedPosition == position) View.VISIBLE else View.GONE

        val adapter = SelectedModelNestedRecyclerAdapter(model.name,model.model)
        holder.nestedRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.nestedRecycler.setHasFixedSize(true)
        holder.nestedRecycler.adapter = adapter

        holder.contentLayout.setOnClickListener {
            if (expandedPosition != position) {
                // Collapse the previously expanded item
                val previouslyExpandedPosition = expandedPosition
                expandedPosition = position
                if (previouslyExpandedPosition != -1) {
                    notifyItemChanged(previouslyExpandedPosition)
                }
            } else {
                // Clicking on the expanded item again will collapse it
                expandedPosition = -1
            }
            isExpandable = expandedPosition == position
            notifyItemChanged(position)
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