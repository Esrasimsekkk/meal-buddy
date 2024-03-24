package com.example.mealbuddyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.mealbuddyapp.R
import com.example.mealbuddyapp.data.FavData
import com.example.mealbuddyapp.data.NtfData
import com.example.mealbuddyapp.databinding.FragmentMenuDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MenuDetails : Fragment() {
    private lateinit var binding: FragmentMenuDetailsBinding
    private var isFavorite = false
    private lateinit var auth: FirebaseAuth
    private lateinit var favoritesRef: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuId: String? = null
    private lateinit var currentUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMenuDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val foodName = arguments?.getString("foodName")
        val restaurantName = arguments?.getString("restaurantName")
        val description = arguments?.getString("description")
        val picUrl = arguments?.getString("picUrl")
        val price = arguments?.getString("price")


        binding.mealNamedtl.text = foodName
        binding.restaurantNamedtl.text = restaurantName
        binding.decriptionDtl.text = description
        binding.price.text = price

        picUrl?.let {
            Glide.with(requireContext())
                .load(it)
                .into(binding.foodImg)
        }


        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        favoritesRef = FirebaseDatabase.getInstance().reference
            .child("favorites")
            .child(auth.currentUser?.uid.orEmpty())

        binding.favbutton.setOnClickListener {
            toggleFavoriteState()
        }

        menuId = arguments?.getString("menuId")

        checkFavoriteState()


        binding.orderBtn.setOnClickListener {
            val foodName = arguments?.getString("foodName")
            val price = arguments?.getString("price")
            val picUrl = arguments?.getString("picUrl")

            val ntfRef = FirebaseDatabase.getInstance().reference.child("ntf")

            val ntfData = NtfData(
                foodName = foodName,
                price = price,
                picUrl = picUrl
            )

            val ntfID = ntfRef.child("ntf").push().key
            ntfID?.let {
                ntfRef.child("ntf").child(it).setValue(ntfData)
            }
            Toast.makeText(requireContext(), "Menu is ordered successfully", Toast.LENGTH_SHORT).show()

            val action = MenuDetailsDirections.actionMenuDetailsToUserHomeFragment()
            Navigation.findNavController(binding.root).navigate(action)



        }
    }

    private fun toggleFavoriteState() {
        isFavorite = !isFavorite
        updateFavoriteButton()
        if (isFavorite) {
            addToFavorites()
        } else {
            removeFromFavorites()
        }
    }


    private fun addToFavorites() {
        val foodName = arguments?.getString("foodName")
        val restaurantName = arguments?.getString("restaurantName")
        val price = arguments?.getString("price")
        val picUrl = arguments?.getString("picUrl")

        val favData = FavData(
            foodName = foodName,
            restaurantName = restaurantName,
            price = price,
            picUrl = picUrl
        )

        if (menuId.isNullOrEmpty()) {
            menuId = favoritesRef.push().key
        }
        menuId?.let {
            favoritesRef.child(it).setValue(favData)
        }

    }

    private fun removeFromFavorites() {
        menuId?.let {
            favoritesRef.child(it).removeValue()
        }
    }

    private fun checkFavoriteState() {
        menuId?.let {
            favoritesRef.child(it).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    isFavorite = snapshot.exists()
                    updateFavoriteButton()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    private fun updateFavoriteButton() {
        if (isFavorite) {
            binding.favbutton.setImageResource(R.drawable.baseline_favorite_24)
        }
    }
}