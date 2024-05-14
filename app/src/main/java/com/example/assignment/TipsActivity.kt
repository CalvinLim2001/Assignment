package com.example.assignment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.data.Tips
import com.example.assignment.databinding.ActivityTipsBinding
import com.example.assignment.databinding.FragmentRegisterBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TipsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipsBinding
    private lateinit var adapter : TipAdapter
    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize RecyclerView
        adapter = TipAdapter()
        binding.tipRecycler.layoutManager = LinearLayoutManager(this)
        binding.tipRecycler.adapter = adapter

        dbRef = FirebaseDatabase.getInstance().getReference("Tips")

        fetchDataFromFirebase()
    }
    private fun fetchDataFromFirebase() {
        // Add listener to fetch data from Firebase
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = snapshot.children.mapNotNull { it.getValue(Tips::class.java) }
                adapter.setData(dataList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TipsActivity, "Error: $error", Toast.LENGTH_LONG).show()
            }
        })
    }
}
