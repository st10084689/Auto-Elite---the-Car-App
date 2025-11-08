package com.harmless.autoelitekotlin.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.CarModel


class ModelSellAdapter(
    private val models: List<CarModel>,
    private val onModelSelected: (CarModel?) -> Unit
) : RecyclerView.Adapter<ModelSellAdapter.ModelViewHolder>() {

    private var selectedPosition = -1 // Track currently selected checkbox

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandName: TextView = itemView.findViewById(R.id.modelText)
        val checkbox: CheckBox = itemView.findViewById(R.id.ModelCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_card, parent, false)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        val model = models[position]
        holder.brandName.text = model.name

        // Remove any old listener before setting new one
        holder.checkbox.setOnCheckedChangeListener(null)

        // Set correct checked state
        holder.checkbox.isChecked = (position == selectedPosition)

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Uncheck previous
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                // Notify selected brand
                onModelSelected(model)
            } else {
                if (selectedPosition == position) {
                    selectedPosition = -1
                    onModelSelected(null)
                }
            }
        }

        // Optional: clicking the whole item also toggles the checkbox
        holder.itemView.setOnClickListener {
            holder.checkbox.isChecked = !holder.checkbox.isChecked
        }
    }

    override fun getItemCount() = models.size
}