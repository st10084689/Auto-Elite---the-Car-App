package com.harmless.autoelitekotlin.view.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.viewModel.MakeAndModelViewModel

class SelectedVariantNestedRecyclerAdapter(
    private val carBrand: String,
    private val carModel: String,
    variants: List<String>,
    private val viewModel: MakeAndModelViewModel
) : RecyclerView.Adapter<SelectedVariantNestedRecyclerAdapter.ItemHolder>() {

    // Build displayed list: "All" first, then the real variants (no duplicates)
    private var displayedVariants: MutableList<String> = mutableListOf<String>().apply {
        if (!variants.contains("All")) add("All")
        addAll(variants.filter { it != "All" })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.variants_items_card, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val variant = displayedVariants[position]

        holder.variantCheck.setOnCheckedChangeListener(null)
        holder.variantCheck.text = variant

        // Default checked rules:
        // - "All" checked if no specific variant selected OR "All" explicitly selected
        val isAllVariant = variant == "All"
        val isChecked = if (isAllVariant) {
            !viewModel.hasSelectionsForModel(carBrand, carModel) || viewModel.isVariantSelected(carBrand, carModel, "All")
        } else {
            viewModel.isVariantSelected(carBrand, carModel, variant)
        }

        holder.variantCheck.isChecked = isChecked

        holder.variantCheck.setOnCheckedChangeListener { _, isNowChecked ->
            // Toggle via ViewModel (logic centralized)
            viewModel.toggleVariant(carBrand, carModel, variant, displayedVariants)

            // If selecting a specific variant, ensure "All" is not selected
            if (!isAllVariant && isNowChecked) {
                viewModel.toggleVariant(carBrand, carModel, "All", displayedVariants) // will remove "All"
            }

            // If no specific variant is selected after change, ensure "All" becomes active
            val anySpecific =
                displayedVariants.any { it != "All" && viewModel.isVariantSelected(carBrand, carModel, it) }
            if (!anySpecific) {
                // ensure "All" is present and selected
                viewModel.toggleVariant(carBrand, carModel, "All", displayedVariants)
            }

            // reorder displayed list: All first, selected variants next
            displayedVariants = sortVariants(displayedVariants).toMutableList()
            notifyDataSetChanged()
        }
    }

    private fun sortVariants(list: List<String>): List<String> {
        val selected = list.filter { it != "All" && viewModel.isVariantSelected(carBrand, carModel, it) }
        val unselected = list.filter { it != "All" && !viewModel.isVariantSelected(carBrand, carModel, it) }
        return listOf("All") + selected + unselected
    }

    override fun getItemCount() = displayedVariants.size

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val variantCheck: CheckBox = itemView.findViewById(R.id.checkbox)
    }
}
