package com.example.mealbuddyapp.admin

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class AdminSignUpManagerTest{

    @Mock
    lateinit var firebaseAuth: FirebaseAuth

    @Mock
    lateinit var firebaseDatabase: FirebaseDatabase

    lateinit var adminSignUpManager: AdminSignUpManager

    @Before
    fun setup() {
        firebaseAuth = mock(FirebaseAuth::class.java)
        firebaseDatabase = mock(FirebaseDatabase::class.java)
        adminSignUpManager =
            AdminSignUpManager(firebaseAuth, firebaseDatabase)
    }

    @Test
    fun testSignUpAdminSucces() {
        val email = "test@example.com"
        val password = "testPassword"
        val username = "Test User"

        val authResult = mock(AuthResult::class.java)

        @Suppress("UNCHECKED_CAST")
        val taskMock = mock(Task::class.java)
                as Task<AuthResult>

        `when`(taskMock.isSuccessful).thenReturn(true)
        `when`(taskMock.result).thenReturn(authResult)

        `when`(firebaseAuth.createUserWithEmailAndPassword(email, password))
            .thenReturn(taskMock)

        val result = adminSignUpManager.signUpUser(email, password, username)

        assertTrue(result)
    }

    @Test
    fun testSignUpAdminFailure() {
        val email = "test@example.com"
        val password = "t"
        val username = "Test User"

        @Suppress("UNCHECKED_CAST")
        val taskMock = mock(Task::class.java)
                as Task<AuthResult>
        `when`(taskMock.isSuccessful).thenReturn(false)

        `when`(firebaseAuth.signInWithEmailAndPassword(email,
            password))
            .thenReturn(taskMock)

        adminSignUpManager.signUpUser(email, password, username)
    }

}