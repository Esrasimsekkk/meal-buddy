package com.example.mealbuddyapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mealbuddyapp.admin.AdminHomePage
import com.example.mealbuddyapp.admin.AdminLogInActivity
import com.example.mealbuddyapp.databinding.ActivityStartScreenBinding
import com.example.mealbuddyapp.user.UserHomePage
import com.example.mealbuddyapp.user.UserLogInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StartScreen : AppCompatActivity()  {
    lateinit var binding: ActivityStartScreenBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val firebaseDatabase = FirebaseDatabase.getInstance().reference

        val currentUser = auth.currentUser

        if(currentUser != null){
            val userId = currentUser.uid

            firebaseDatabase.child("users").child(userId).get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val intent = Intent(this, UserHomePage::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        val intent = Intent(this, AdminHomePage ::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

        }else{
            binding.admin.setOnClickListener {
                val intent = Intent(this, AdminLogInActivity::class.java)
                startActivity(intent)
            }
            binding.user.setOnClickListener {
                val intent = Intent(this, UserLogInActivity::class.java)
                startActivity(intent)
            }
        }


    }
}