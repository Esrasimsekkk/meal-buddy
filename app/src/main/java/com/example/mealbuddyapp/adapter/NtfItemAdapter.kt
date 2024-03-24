package com.example.mealbuddyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealbuddyapp.data.NtfData
import com.example.mealbuddyapp.databinding.OrderItemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NtfItemAdapter(private val ntfList: List<NtfData>):
    RecyclerView.Adapter<NtfItemAdapter.ViewHolder>(){

    class ViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ntfData: NtfData){
            binding.orderFoodName.text = ntfData.foodName
            binding.orderFoodPrice.text = ntfData.price

            Glide.with(binding.root.context)
                .load(ntfData.picUrl)
                .into(binding.orderFoodImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ntfList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ntfList[position])

    }
}