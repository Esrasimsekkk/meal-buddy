package com.example.mealbuddyapp.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealbuddyapp.R
import com.example.mealbuddyapp.adapter.FavAdapter
import com.example.mealbuddyapp.data.FavData
import com.example.mealbuddyapp.databinding.FragmentUserFavBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserFavFragment : Fragment() {
    private lateinit var binding: FragmentUserFavBinding
    private lateinit var favAdapter: FavAdapter
    private lateinit var favList: MutableList<FavData>
    private lateinit var auth: FirebaseAuth
    private lateinit var favoritesRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUserFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favList = mutableListOf()
        favAdapter = FavAdapter(favList)
        binding.favRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favRecyclerView.adapter = favAdapter

        auth = FirebaseAuth.getInstance()
        favoritesRef = FirebaseDatabase.getInstance().reference.child("favorites")
            .child(auth.currentUser?.uid.orEmpty())

        loadFavorites()
    }



    private fun loadFavorites() {
        favoritesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                favList.clear()
                for (favSnapshot in snapshot.children) {
                    val favData = favSnapshot.getValue(FavData::class.java)
                    favData?.let { favList.add(it) }
                }
                favAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

}