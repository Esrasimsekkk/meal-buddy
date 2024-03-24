package com.example.mealbuddyapp

import android.os.SystemClock
import android.os.SystemClock.*
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mealbuddyapp.admin.AdminSignUpActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AdminSignUpActivityPerformanceTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(AdminSignUpActivity::class.java)
    }

    @Test
    fun testButtonClickPerformance() {
        measureClickPerformance(R.id.aButton)
    }

    private fun measureClickPerformance(buttonId: Int) {
        val startTimestamp = uptimeMillis()

        repeat(1000) {
            onView(withId(buttonId)).perform(click())
        }

        val endTimestamp = uptimeMillis()
        val elapsedTime = endTimestamp - startTimestamp

        println("Button click performance for button with ID $buttonId: $elapsedTime milliseconds")
    }
}