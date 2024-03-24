package com.example.mealbuddyapp.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.mealbuddyapp.R
import com.example.mealbuddyapp.adapter.MenuItemAdapter
import com.example.mealbuddyapp.data.MenuData
import com.example.mealbuddyapp.databinding.FragmentUserHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserHomeFragment : Fragment() {
    private lateinit var binding:FragmentUserHomeBinding
    private lateinit var database: DatabaseReference
    private lateinit var menuItemAdapter: MenuItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)


        database = FirebaseDatabase.getInstance().reference.child("menus")
        menuItemAdapter = MenuItemAdapter(emptyList())


        binding.menuRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = menuItemAdapter
        }

        loadMenuData()




    }

    private fun loadMenuData() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val menuList = mutableListOf<MenuData>()

                    for (menuSnapshot in snapshot.children) {
                        val menuData = menuSnapshot.getValue(MenuData::class.java)
                        menuData?.let {
                            menuList.add(it)
                        }
                    }
                    menuItemAdapter = MenuItemAdapter(menuList)
                    binding.menuRecyclerView.adapter = menuItemAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}