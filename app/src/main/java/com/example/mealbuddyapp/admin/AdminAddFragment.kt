package com.example.mealbuddyapp.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mealbuddyapp.data.MenuData
import com.example.mealbuddyapp.databinding.FragmentAdminAddBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


@Suppress("DEPRECATION")
class AdminAddFragment : Fragment() {

    private lateinit var binding: FragmentAdminAddBinding
    lateinit var storageReference: StorageReference

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAdminAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storageReference = FirebaseStorage.getInstance().reference

        binding.addMealBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data


            if (imageUri != null) {
                uploadImageToFirebaseStorage(imageUri)

            }
        }
    }


    fun uploadImageToFirebaseStorage(imageUri: Uri) {

        val imageRef = storageReference.child("menu_images/${System.currentTimeMillis()}.jpg")

        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->

                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    saveMenu(imageUrl)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    fun saveMenu(imageUrl: String) {
        val foodName = binding.mealNameEditText.text.toString()
        val restaurantName = binding.restaurantNameEditText2.text.toString()
        val price = binding.priceEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val isAvailable = binding.checkBox.isChecked

        val menuData = MenuData(foodName, restaurantName, price, description, isAvailable, imageUrl)
        val databaseReference = FirebaseDatabase.getInstance().reference


        val menuId = databaseReference.child("menus").push().key
        menuId?.let {
            databaseReference.child("menus").child(it).setValue(menuData)
        }

        binding.mealNameEditText.setText("")
        binding.restaurantNameEditText2.setText("")
        binding.priceEditText.setText("")
        binding.descriptionEditText.setText("")
        binding.checkBox.isChecked

        Toast.makeText(requireContext(), "Menu is added successfully", Toast.LENGTH_SHORT).show()





    }


}