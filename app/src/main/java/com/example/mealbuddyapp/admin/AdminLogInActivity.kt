package com.example.mealbuddyapp.admin


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mealbuddyapp.databinding.ActivityAdminLogInBinding
import com.google.firebase.auth.FirebaseAuth

class AdminLogInActivity : AppCompatActivity()  {
    lateinit var binding : ActivityAdminLogInBinding
    lateinit var adminLogInManager: AdminLogInManager
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.aSignupLink.setOnClickListener {
            val intent = Intent(this, AdminSignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        adminLogInManager = AdminLogInManager(this, firebaseAuth)

        binding.aButton.setOnClickListener {
            val signUpEmail = binding.aEmailEditText.text.toString()
            val signUpPassword = binding.aPasswordEditText.text.toString()

            adminLogInManager.loginUser(signUpEmail, signUpPassword)
        }

    }


}