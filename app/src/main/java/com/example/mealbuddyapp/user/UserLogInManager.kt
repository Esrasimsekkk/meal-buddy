package com.example.mealbuddyapp.user

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class UserLogInManager(private val context: Context, private val firebaseAuth: FirebaseAuth) {


    fun loginUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, UserHomePage::class.java)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "All Fields are Mandatory ", Toast.LENGTH_SHORT).show()
        }
    }
}