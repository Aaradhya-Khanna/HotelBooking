package com.example.hotelbooking

import HotelAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.content.Intent

class Searchhotels : AppCompatActivity() {
    private lateinit var databaseReference1: DatabaseReference
    private lateinit var recycler: RecyclerView
    private var data = ArrayList<hotels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        recycler = findViewById(R.id.view)
        recycler.layoutManager = LinearLayoutManager(this)

        data = arrayListOf()
        getuserData()
    }

    private fun getuserData() {
        databaseReference1 = FirebaseDatabase.getInstance().getReference("data")
        databaseReference1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val hotel = userSnapshot.getValue(hotels::class.java)
                        data.add(hotel!!)
                    }

                    val adapter = HotelAdapter(data)
                    recycler.adapter = adapter

                    // Set item click listener
                    adapter.setOnItemClickListener(object : HotelAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            // Handle item click here
                            val selectedItem = data[position]

                            // Create an Intent to start a new activity and pass data
                            val intent = Intent(this@Searchhotels, Booking::class.java)
                            intent.putExtra("hotelData", selectedItem)
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle cancellation
            }
        })
    }
}
