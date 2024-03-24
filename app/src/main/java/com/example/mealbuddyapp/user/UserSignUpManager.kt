package com.example.mealbuddyapp.user

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserSignUpManager(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {
    @RequiresApi(Build.VERSION_CODES.P)
    fun signUpUser(email: String, password: String, username: String): Boolean {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    val userId = firebaseUser?.uid

                    if (userId != null) {
                        val databaseReference: DatabaseReference = firebaseDatabase.getReference("users")
                        databaseReference.child(userId).setValue(
                            com.example.mealbuddyapp.data.UserData(
                                userId,
                                username,
                                email,
                                password
                            )
                        )
                    }
                }
            }
            return true
        } catch (e: Exception) {

            return false
        }
    }
}