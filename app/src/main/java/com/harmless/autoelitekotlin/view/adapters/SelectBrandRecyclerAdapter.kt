package com.harmless.autoelitekotlin.view.adapters

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
import com.harmless.autoelitekotlin.model.CarModel
import com.harmless.autoelitekotlin.viewModel.MakeAndModelViewModel

class SelectBrandRecyclerAdapter(
    private val brands: List<CarBrand>,
    private val viewModel: MakeAndModelViewModel
) : RecyclerView.Adapter<SelectBrandRecyclerAdapter.BrandViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.make_card, parent, false)
        return BrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brandItem = brands[position]
        val brandName = brandItem.brand
        holder.carBrand.text = brandName
        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked = viewModel.isBrandSelected(brandName)
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            val modelNames = brandItem.models.map { it.name }
            viewModel.toggleBrand(brandName, modelNames)
            holder.nestedRecycler.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        // create models list including "All"
        val modelsForAdapter = mutableListOf<CarModel>().apply {
            if (brandItem.models.none { it.name == "All" }) add(CarModel(name = "All", variants = mutableListOf("All")))
            addAll(brandItem.models)
        }

        val modelAdapter = SelectedModelNestedRecyclerAdapter(brandName, modelsForAdapter, viewModel)
        holder.nestedRecycler.apply {
            layoutManager = LinearLayoutManager(holder.itemView.context)
            adapter = modelAdapter
            visibility = if (holder.checkbox.isChecked) View.VISIBLE else View.GONE
        }

        holder.arrow.setOnClickListener {
            val visible = holder.nestedRecycler.visibility == View.VISIBLE
            holder.nestedRecycler.visibility = if (visible) View.GONE else View.VISIBLE
            holder.arrow.rotation = if (visible) 0f else 90f
        }
    }

    override fun getItemCount(): Int = brands.size

    // ------------------------------------------------------------
    // ViewHolder for Brand-Level Item
    // ------------------------------------------------------------
    inner class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentLayout: RelativeLayout = itemView.findViewById(R.id.ListItem)
        val expandableLayout: RelativeLayout = itemView.findViewById(R.id.NestListView)
        val arrow: ImageView = itemView.findViewById(R.id.ChevronDown)
        val carBrand: TextView = itemView.findViewById(R.id.CarBrandTxt)
        val nestedRecycler: RecyclerView = itemView.findViewById(R.id.CarBrandNestedRecylcer)
        val checkbox: CheckBox = itemView.findViewById(R.id.checkVarient)
    }
}
