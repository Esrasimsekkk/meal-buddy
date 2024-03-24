package com.example.mealbuddyapp.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mealbuddyapp.R
import com.example.mealbuddyapp.data.UserData
import com.example.mealbuddyapp.databinding.FragmentAdminAddBinding
import com.example.mealbuddyapp.databinding.FragmentUserProfileBinding
import com.example.mealbuddyapp.ui.StartScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserProfileFragment : Fragment() {
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
    private lateinit var binding : FragmentUserProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            getUserData(userId)
        }

        auth = FirebaseAuth.getInstance()
        binding.userLogoutBtn.setOnClickListener{
            auth.signOut()
            val intent = Intent(activity, StartScreen::class.java)
            startActivity(intent)
        }
    }

    fun getUserData(userId: String) {
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue(UserData::class.java)
                    userData?.let {
                        updateUI(it.userName, it.userEmail)
                    }
                } else {

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun updateUI(adminName: String?, adminEmail: String?) {
        view?.findViewById<TextView>(R.id.userNameText)?.text = adminName
        view?.findViewById<TextView>(R.id.userEmailText)?.text = adminEmail
    }
}