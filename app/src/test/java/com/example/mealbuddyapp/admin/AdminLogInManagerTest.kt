package com.example.mealbuddyapp.admin

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class AdminLogInManagerTest{

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var firebaseAuth: FirebaseAuth

    lateinit var adminLogInManager: AdminLogInManager

    @Before
    fun setup() {
        context = mock(Context::class.java)
        firebaseAuth = mock(FirebaseAuth::class.java)
        adminLogInManager = AdminLogInManager(context,firebaseAuth)
    }

    @Test
    fun testLoginAdminSuccess() {
        val email = "test@example.com"
        val password = "testPassword"

        val authResult = mock(AuthResult::class.java)

        @Suppress("UNCHECKED_CAST")
        val taskMock = mock(Task::class.java) as Task<AuthResult>

        `when`(taskMock.isSuccessful).thenReturn(true)
        `when`(taskMock.result).thenReturn(authResult)

        `when`(firebaseAuth.signInWithEmailAndPassword(email,password))
            .thenReturn(taskMock)

        adminLogInManager.loginUser(email, password)


    }

    @Test
    fun testLoginAdminFailure() {
        val email = "test@example.com"
        val password = "t"

        @Suppress("UNCHECKED_CAST")
        val taskMock = mock(Task::class.java) as Task<AuthResult>
        `when`(taskMock.isSuccessful).thenReturn(false)

        `when`(firebaseAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(taskMock)

        adminLogInManager.loginUser(email, password)
    }

}