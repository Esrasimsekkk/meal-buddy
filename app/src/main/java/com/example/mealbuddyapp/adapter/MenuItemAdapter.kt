package com.example.mealbuddyapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealbuddyapp.data.MenuData
import com.example.mealbuddyapp.databinding.MenuItemBinding
import com.example.mealbuddyapp.user.UserHomeFragmentDirections

class MenuItemAdapter(
    private val menuList: List<MenuData>) : RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {

    class ViewHolder(val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuData: MenuData){
            binding.foodName.text = menuData.foodName
            binding.restaurantName.text = menuData.restaurantName
            binding.price.text = menuData.price

            Glide.with(binding.root.context)
                .load(menuData.picUrl)
                .into(binding.foodImages)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MenuItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuList[position])
        holder.binding.btnDtail.setOnClickListener {
            val action = UserHomeFragmentDirections.actionUserHomeFragmentToMenuDetails(
                foodName = menuList[position].foodName,
                resturantName = menuList[position].restaurantName,
                description = menuList[position].description,
                picUrl = menuList[position].picUrl,
                price = menuList[position].price)

            Navigation.findNavController(holder.binding.root).navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}