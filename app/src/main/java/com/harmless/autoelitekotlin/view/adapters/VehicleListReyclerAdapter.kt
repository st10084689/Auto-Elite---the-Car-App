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


class VehicleListReyclerAdapter(private val cars: List<Car>):
RecyclerView.Adapter<VehicleListReyclerAdapter.ItemViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.brandName.setText(cars[position].brand)
        holder.modelName.setText(cars[position].model)
        holder.year.setText(cars[position].year!!.toString())
        holder.location.setText(cars[position].location)
        holder.type.setText(cars[position].type)
        holder.mileage.setText(cars[position].mileage!!.toString())
        holder.price.setText(cars[position].price!!.toString())
        holder.size.setText(cars[position].images!!.size.toString())

        Glide.with(holder.itemView.context)
            .load(cars[position].images)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.image)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val brandName = itemView.findViewById<TextView>(R.id.VehicleBrandTxt)
        val modelName = itemView.findViewById<TextView>(R.id.VehicleModelTxt)
        val year = itemView.findViewById<TextView>(R.id.VehicleYearTxt)
        val location = itemView.findViewById<TextView>(R.id.LocationTxt)
        val type = itemView.findViewById<TextView>(R.id.TypeTxt)
        val mileage = itemView.findViewById<TextView>(R.id.VehicleMileageTxt)
        val price = itemView.findViewById<TextView>(R.id.priceTxt)
        val size = itemView.findViewById<TextView>(R.id.size)
        val image = itemView.findViewById<ImageView>(R.id.VehicleImage)
    }
}