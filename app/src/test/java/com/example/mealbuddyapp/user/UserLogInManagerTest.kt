package com.example.mealbuddyapp.user

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class UserLogInManagerTest{
    @Mock
    lateinit var context: Context

    @Mock
    lateinit var firebaseAuth: FirebaseAuth

    lateinit var userLogInManager: UserLogInManager

    @Before
    fun setup() {

        context = Mockito.mock(Context::class.java)
        firebaseAuth = Mockito.mock(FirebaseAuth::class.java)
        userLogInManager = UserLogInManager(context,firebaseAuth)
    }

    @Test
    fun testLoginUserSuccess() {
        val email = "test@example.com"
        val password = "testPassword"

        val authResult = Mockito.mock(AuthResult::class.java)

        @Suppress("UNCHECKED_CAST")
        val taskMock = Mockito.mock(Task::class.java) as Task<AuthResult>

        Mockito.`when`(taskMock.isSuccessful).thenReturn(true)
        Mockito.`when`(taskMock.result).thenReturn(authResult)

        Mockito.`when`(firebaseAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(taskMock)

        userLogInManager.loginUser(email, password)


    }

    @Test
    fun testLoginUserFailure() {
        val email = "test@example.com"
        val password = "t"

        @Suppress("UNCHECKED_CAST")
        val taskMock = Mockito.mock(Task::class.java)
                as Task<AuthResult>
        Mockito.`when`(taskMock.isSuccessful).
        thenReturn(false)

        Mockito.`when`(firebaseAuth
            .signInWithEmailAndPassword(email, password))
            .thenReturn(taskMock)

        userLogInManager.loginUser(email, password)
    }

}