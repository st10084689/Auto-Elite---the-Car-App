package com.harmless.autoelitekotlin.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.CarModel
import com.harmless.autoelitekotlin.viewModel.MakeAndModelViewModel
class SelectedModelNestedRecyclerAdapter(
    private val carBrand: String,
    carModels: List<CarModel>,
    private val viewModel: MakeAndModelViewModel
) : RecyclerView.Adapter<SelectedModelNestedRecyclerAdapter.ItemHolder>() {

    // make sure "All" model exists and is first; do not modify source list
    private val modelsForAdapter: List<CarModel> = buildList {
        if (carModels.none { it.name == "All" }) add(CarModel(name = "All", variants = mutableListOf("All")))
        addAll(carModels)
    }

    // sort: All first, selected models next, then alphabetically
    private val sortedModels: List<CarModel> = modelsForAdapter.sortedWith(
        compareBy<CarModel> { it.name != "All" } // All first
            .thenByDescending { viewModel.isModelSelected(carBrand, it.name) } // selected next
            .thenBy { it.name }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.model_items_car, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val model = sortedModels[position]
        val isAllModel = model.name == "All"

        holder.modelCheck.setOnCheckedChangeListener(null)
        holder.modelCheck.text = model.name

        // Default check: "All" checked when no selection for brand, or brand-level selected
        val isChecked = if (isAllModel) {
            viewModel.isBrandSelected(carBrand) || !viewModel.hasAnySelectionForBrand(carBrand)
        } else {
            viewModel.isModelSelected(carBrand, model.name)
        }
        holder.modelCheck.isChecked = isChecked

        // nested variants adapter (pass shared viewModel)
        val variantAdapter = SelectedVariantNestedRecyclerAdapter(carBrand, model.name, model.variants, viewModel)
        holder.variantRecycler.apply {
            layoutManager = LinearLayoutManager(holder.itemView.context)
            adapter = variantAdapter
            visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        holder.modelCheck.setOnCheckedChangeListener { _, isNowChecked ->
            if (isAllModel) {
                // toggle brand (All acts as brand toggle)
                val realModelNames = modelsForAdapter.filter { it.name != "All" }.map { it.name }
                viewModel.toggleBrand(carBrand, realModelNames)
            } else {
                // toggle just this model
                viewModel.toggleModel(carBrand, model.name, model.variants)
                // if toggled on, ensure "All" is unselected
                if (isNowChecked) viewModel.toggleModel(carBrand, "All", emptyList())
            }


            // After any change: if nothing selected for brand -> ensure brand-level "All"
            val anySpecificSelected = modelsForAdapter.any { it.name != "All" && viewModel.hasSelectionsForModel(carBrand, it.name) }
            if (!anySpecificSelected) {
                // ensure the brand has the default "All" selection
                val realModelNames = modelsForAdapter.filter { it.name != "All" }.map { it.name }
                viewModel.toggleBrand(carBrand, realModelNames)
            }

            holder.variantRecycler.visibility = if (isNowChecked) View.VISIBLE else View.GONE
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = sortedModels.size

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modelCheck: CheckBox = itemView.findViewById(R.id.checkbox)
        val variantRecycler: RecyclerView = itemView.findViewById(R.id.variants_recycler)
    }
}
