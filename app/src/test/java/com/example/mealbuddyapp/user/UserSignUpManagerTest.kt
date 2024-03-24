package com.example.mealbuddyapp.user

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class UserSignUpManagerTest{
    @Mock
    lateinit var firebaseAuth:FirebaseAuth

    @Mock
    lateinit var firebaseDatabase:FirebaseDatabase

    lateinit var userSignUpManager: UserSignUpManager

    @Before
    fun setup() {
        firebaseAuth = Mockito.
        mock(FirebaseAuth::class.java)

        firebaseDatabase = Mockito.
        mock(FirebaseDatabase::class.java)

        userSignUpManager = UserSignUpManager(firebaseAuth,
            firebaseDatabase)
    }

    @Test
    fun testSignUpUserSuccess() {
        val email = "test@example.com"
        val password = "testPassword"
        val username = "Test User"

        val authResult = Mockito.mock(AuthResult::class.java)

        @Suppress("UNCHECKED_CAST")
        val taskMock = Mockito.mock(Task::class.java)
                as Task<AuthResult>

        Mockito.`when`(taskMock.isSuccessful).thenReturn(true)
        Mockito.`when`(taskMock.result).thenReturn(authResult)

        Mockito.`when`(firebaseAuth.
        createUserWithEmailAndPassword(email, password))
            .thenReturn(taskMock)

        val result = userSignUpManager.
        signUpUser(email, password, username)

        assertTrue(result)
    }

    @Test
    fun testSignUpUserFailure() {
        val email = "test@example.com"
        val password = "t"
        val username = "Test User"

        @Suppress("UNCHECKED_CAST")
        val taskMock = Mockito.mock(Task::class.java)
                as Task<AuthResult>
        Mockito.`when`(taskMock.isSuccessful).
        thenReturn(false)

        Mockito.`when`(firebaseAuth.
        signInWithEmailAndPassword(email, password))
            .thenReturn(taskMock)

        userSignUpManager.signUpUser(email, password, username)
    }
}