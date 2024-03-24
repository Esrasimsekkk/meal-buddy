package com.example.mealbuddyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mealbuddyapp.R

@Suppress("DEPRECATION")
class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh_screen)
        Handler().postDelayed({
            val intent = Intent(this, StartScreen::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}