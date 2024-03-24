package com.example.mealbuddyapp.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealbuddyapp.R
import com.example.mealbuddyapp.adapter.NtfItemAdapter
import com.example.mealbuddyapp.data.NtfData
import com.example.mealbuddyapp.data.UserData
import com.example.mealbuddyapp.databinding.FragmentAdminProfileBinding
import com.example.mealbuddyapp.ui.StartScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class AdminProfileFragment : Fragment() {
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("admins")
    private lateinit var binding : FragmentAdminProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var ntfAdapter: NtfItemAdapter
    lateinit var database: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAdminProfileBinding.inflate(inflater, container, false)
        return binding.root     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            getUserData(userId)
        }

        auth = FirebaseAuth.getInstance()
        binding.adminLogoutBtn.setOnClickListener{
            auth.signOut()
            val intent = Intent(activity, StartScreen::class.java)
            startActivity(intent)

        }



        ntfAdapter = NtfItemAdapter(emptyList())

        binding.recycelrView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ntfAdapter
        }

        getNtfData()

    }

    fun getUserData(adminId: String) {
        databaseReference.child(adminId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val adminData = dataSnapshot.getValue(UserData::class.java)
                    adminData?.let {
                        updateUI(it.userName, it.userEmail)
                    }
                } else {

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun updateUI(adminName: String?, adminEmail: String?) {
        view?.findViewById<TextView>(R.id.adminNameText)?.text = adminName
        view?.findViewById<TextView>(R.id.adminEmailText)?.text = adminEmail
    }

    fun getNtfData(){
        database = FirebaseDatabase.getInstance().reference.child("ntf").child("ntf")
        database.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val ntfList = mutableListOf<NtfData>()

                    for(ntfSnapshot in snapshot.children){
                        val ntfData = ntfSnapshot.getValue(NtfData::class.java)
                        ntfData?.let {
                            ntfList.add(it)
                        }
                    }
                    ntfAdapter = NtfItemAdapter(ntfList)
                    binding.recycelrView.adapter = ntfAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

}