package com.example.mealbuddyapp.admin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mealbuddyapp.data.UserData
import com.example.mealbuddyapp.databinding.ActivityAdminSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminSignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminSignUpBinding
    lateinit var adminSignUpManager: AdminSignUpManager


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance()

        adminSignUpManager = AdminSignUpManager(firebaseAuth, firebaseDatabase)


        binding.aLoginLink.setOnClickListener{
            val intent = Intent(this, AdminLogInActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.aButton.setOnClickListener{
            val signupUsername = binding.aNameEditText.text.toString()
            val signupEmail = binding.aEmailEditText.text.toString()
            val signupPassword = binding.aPasswordEditText.text.toString()

            if (signupUsername.isNotEmpty() && signupEmail.isNotEmpty() && signupPassword.isNotEmpty()) {
                val result = adminSignUpManager.signUpUser(signupEmail, signupPassword, signupUsername)

                if (result) {
                    Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdminLogInActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "Signup Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@AdminSignUpActivity, "All Fields are Mandatory ", Toast.LENGTH_SHORT).show()
            }
        }

    }

}