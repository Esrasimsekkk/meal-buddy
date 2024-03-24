package com.example.mealbuddyapp.admin

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
class AdminAddFragmentTest{
    private lateinit var adminAddFragment: AdminAddFragment

    // Mocks
    @Mock
    private lateinit var mockUploadTask: UploadTask

    @Mock
    private val mockStorageReference: StorageReference=
        mock(StorageReference::class.java)

    @Mock
    private val mockDatabaseReference: DatabaseReference
    = mock(DatabaseReference::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        adminAddFragment = AdminAddFragment()
        adminAddFragment.storageReference = mockStorageReference
    }

    @Test
    fun testUploadImageToFirebaseStorage() {
        val imageUri: Uri = mock(Uri::class.java)

        `when`(mockStorageReference.child(any(String::class.java)))
            .thenReturn(mockStorageReference)
        `when`(mockStorageReference.putFile(imageUri))
            .thenReturn(mockUploadTask)

        adminAddFragment.uploadImageToFirebaseStorage(imageUri)

        verify(mockUploadTask).addOnSuccessListener(any())
    }

    @Test
    fun testSaveMenu() {
        val imageUrl = "mock_image_url"
        `when`(mockDatabaseReference.child(any(String::class.java)))
            .thenReturn(mockDatabaseReference)

        adminAddFragment.saveMenu(imageUrl)

        verify(mockDatabaseReference).setValue(any())
    }
}