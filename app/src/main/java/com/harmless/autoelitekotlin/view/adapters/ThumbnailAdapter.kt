package com.harmless.autoelitekotlin.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harmless.autoelitekotlin.R

class ThumbnailAdapter(
    private val images: List<String>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ThumbnailAdapter.ThumbViewHolder>() {

    private var selectedPosition = 0

    inner class ThumbViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.thumbnail_image)
        val cardView: CardView = itemView.findViewById(R.id.card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_thumbnail, parent, false)
        return ThumbViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThumbViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(images[position])
            .centerCrop()
            .into(holder.image)

        if (position == selectedPosition) {
            holder.cardView.radius = 16f
            holder.image.scaleX = 1.1f
            holder.image.scaleY = 1.1f
            holder.image.alpha = 1.0f
        } else {
            holder.cardView.radius = 0f
            holder.image.scaleX = 1.0f
            holder.image.scaleY = 1.0f
            holder.image.alpha = 0.5f
        }

        holder.itemView.setOnClickListener {
            val oldPos = selectedPosition
            selectedPosition = position
            notifyItemChanged(oldPos)
            notifyItemChanged(position)
            onClick(position)
        }
    }

    override fun getItemCount() = images.size

    fun setSelected(position: Int) {
        val oldPos = selectedPosition
        selectedPosition = position
        notifyItemChanged(oldPos)
        notifyItemChanged(position)
    }
}
