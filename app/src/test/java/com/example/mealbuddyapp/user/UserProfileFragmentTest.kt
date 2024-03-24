package com.example.mealbuddyapp.user


import com.example.mealbuddyapp.data.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.Spy

@Suppress("DEPRECATION")
class UserProfileFragmentTest{
    @Mock
    private lateinit var mockDatabaseReference: DatabaseReference

    @Mock
    private lateinit var mockDataSnapshot: DataSnapshot

    @Spy
    private lateinit var mockUserFragment: UserProfileFragment

    @Captor
    private lateinit var captor: ArgumentCaptor<ValueEventListener>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mockUserFragment = spy(UserProfileFragment())
        mockUserFragment.databaseReference = mockDatabaseReference
    }

    @After
    fun tearDown() {
        Mockito.reset(mockUserFragment)
    }

    @Test
    fun testGetUserData_UserExists() {
        val userId = "testUserId"
        val expectedUserName = "Test User"
        val expectedUserEmail = "test@example.com"

        `when`(mockDatabaseReference.child(userId))
            .thenReturn(mockDatabaseReference)
        `when`(mockDatabaseReference
            .addListenerForSingleValueEvent(captor.capture())).thenAnswer {
            captor.value.onDataChange(mockDataSnapshot)
        }

        `when`(mockDataSnapshot.exists()).thenReturn(true)
        `when`(mockDataSnapshot.getValue(UserData::class.java))
            .thenReturn(UserData(expectedUserName, expectedUserEmail))

        mockUserFragment.getUserData(userId)

        verify(mockUserFragment).updateUI(expectedUserName, expectedUserEmail)
    }

    @Test
    fun testGetUserData_UserNotExists() {
        val userId = "nonExistentUserId"

        `when`(mockDatabaseReference.child(userId))
            .thenReturn(mockDatabaseReference)

        `when`(mockDatabaseReference
            .addListenerForSingleValueEvent(captor.capture()))
            .thenAnswer {
            captor.value.onDataChange(mockDataSnapshot)
        }

        `when`(mockDataSnapshot.exists()).thenReturn(false)

        mockUserFragment.getUserData(userId)

        verify(mockUserFragment, never()).updateUI(any(), any())
    }

}