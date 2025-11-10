package com.harmless.autoelitekotlin.view.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.view.activities.CarListingActivity


class VehicleListReyclerAdapter(private var cars: List<Car>):
RecyclerView.Adapter<VehicleListReyclerAdapter.ItemViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val car = cars[position]
        holder.cardButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CarListingActivity::class.java)
            intent.putExtra("car", car)
            context.startActivity(intent)
        }

        holder.brandName.setText(cars[position].brand)
        holder.modelName.setText(cars[position].model)
        holder.year.setText(cars[position].year!!.toString())
        holder.location.setText(cars[position].location)
        holder.type.setText(cars[position].type)
        holder.mileage.setText(cars[position].mileage!!.toString())
        holder.price.setText(cars[position].price!!.toString())
        holder.size.setText(cars[position].images!!.size.toString())

        val imageUrl = car.images?.firstOrNull()
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.mipmap.ic_launcher)
        }


    }
    fun updateData(newList: List<Car>) {
        cars = newList
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardButton = itemView.findViewById<RelativeLayout>(R.id.VehicleCardView)
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
    companion object{
        private const val TAG = "VehicleListReyclerAdapt"
    }
}