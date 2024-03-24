package com.example.mealbuddyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealbuddyapp.data.FavData
import com.example.mealbuddyapp.databinding.FavItemBinding
import com.example.mealbuddyapp.databinding.FragmentUserFavBinding

class FavAdapter (private val favList: List<FavData>) :
    RecyclerView.Adapter<FavAdapter.FavViewHolder>() {

    inner class FavViewHolder(private val binding: FavItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favData: FavData) {
            binding.apply {
                favFoodName.text = favData.foodName

                favFoodPrice.text = favData.price

                Glide.with(binding.root.context)
                    .load(favData.picUrl)
                    .into(binding.favFoodImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding =
            FavItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val favData = favList[position]
        holder.bind(favData)
    }

    override fun getItemCount(): Int {
        return favList.size
    }
}