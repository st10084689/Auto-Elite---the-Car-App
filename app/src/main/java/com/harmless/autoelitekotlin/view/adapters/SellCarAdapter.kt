package com.harmless.autoelitekotlin.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.Car

class SellCarAdapter(private val carList: List<Car>) :
    RecyclerView.Adapter<SellCarAdapter.SellCarViewHolder>() {

    inner class SellCarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carImage: ImageView = itemView.findViewById(R.id.sell_car_image)
        val carBrand: TextView = itemView.findViewById(R.id.sell_car_brand_textview)
        val carModel: TextView = itemView.findViewById(R.id.sell_car_model_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellCarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sell_car_item_card, parent, false)
        return SellCarViewHolder(view)
    }

    override fun onBindViewHolder(holder: SellCarViewHolder, position: Int) {
        val car = carList[position]

        holder.carBrand.text = car.brand
        holder.carModel.text = car.model

        if (car.images.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(car.images[0])
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.carImage)
        } else {
            holder.carImage.setImageResource(R.drawable.logo)
        }
    }

    override fun getItemCount(): Int = carList.size
}
