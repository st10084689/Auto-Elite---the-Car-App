package com.harmless.autoelitekotlin.view.adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.CarModel


class YearSellAdapter(private val items: List<Int>,
                      private val onYearSelected: (Int?) -> Unit):
    RecyclerView.Adapter<YearSellAdapter.ItemViewHolder>(){
    private var selectedPosition = -1



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.brand_items_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = items[position]

        holder.brandName.text = model.toString()
        holder.checkbox.isChecked = (position == selectedPosition)
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Update your selected value list
                SelectedValues.selectedYear.clear()
                SelectedValues.selectedYear.add(items[position])

                // Uncheck previous selection safely
                val previousPosition = selectedPosition
                selectedPosition = position

                holder.itemView.post {
                    if (previousPosition != -1) notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                }

                onYearSelected(model)
            } else {
                if (selectedPosition == position) {
                    selectedPosition = -1
                    holder.itemView.post {
                        notifyItemChanged(position)
                    }
                    onYearSelected(null)
                    SelectedValues.selectedYear.clear()
                }
            }



    }




    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandName = itemView.findViewById<TextView>(R.id.nameTxt)
        val checkbox = itemView.findViewById<CheckBox>(R.id.checkbox)

    }
}