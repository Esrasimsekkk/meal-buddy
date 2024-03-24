package com.example.mealbuddyapp.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mealbuddyapp.admin.AdminHomePage
import com.example.mealbuddyapp.admin.AdminLogInManager
import com.example.mealbuddyapp.data.UserData
import com.example.mealbuddyapp.databinding.ActivityUserLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserLogInActivity : AppCompatActivity(){
    lateinit var binding: ActivityUserLogInBinding
    lateinit var userLogInManager: UserLogInManager
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.uSignupLink.setOnClickListener {
            val intent = Intent(this, UserSignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        userLogInManager = UserLogInManager(this, firebaseAuth)

        binding.uButton.setOnClickListener{
            val signUpEmail = binding.uEmailEditText.text.toString()
            val signUpPassword = binding.uPasswordEditText.text.toString()

            userLogInManager.loginUser(signUpEmail, signUpPassword)

        }
    }


}