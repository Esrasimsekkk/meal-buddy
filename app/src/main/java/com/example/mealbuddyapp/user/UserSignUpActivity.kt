package com.example.mealbuddyapp.user

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mealbuddyapp.admin.AdminHomePage
import com.example.mealbuddyapp.admin.AdminLogInActivity
import com.example.mealbuddyapp.data.UserData
import com.example.mealbuddyapp.databinding.ActivityUserSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserSignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserSignUpBinding
    lateinit var userSignUpManager: UserSignUpManager

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance()

        userSignUpManager = UserSignUpManager(firebaseAuth, firebaseDatabase)

        binding.uLoginLink.setOnClickListener {
            val intent = Intent(this, UserLogInActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.uButton.setOnClickListener{
            val signupUsername = binding.uNameEditText.text.toString()
            val signupEmail = binding.uEmailEditText.text.toString()
            val signupPassword = binding.uPasswordEditText.text.toString()

            if (signupUsername.isNotEmpty() && signupEmail.isNotEmpty() && signupPassword.isNotEmpty()) {
                val result = userSignUpManager.signUpUser(signupEmail, signupPassword, signupUsername)

                if (result) {
                    Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, UserLogInActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "Signup Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@UserSignUpActivity, "All Fields are Mandatory ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*private fun uSignup(userId: String, email:String, username: String, password:String){
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.exists()){
                    val userData = UserData(userId,username,email,password)
                    databaseReference.child(userId!!).setValue(userData)
                }else{
                    Toast.makeText(this@UserSignUpActivity, "User Already Exists", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@UserSignUpActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }*/
}