package com.example.hotelbooking
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FrontAct : AppCompatActivity()
{
    private lateinit var addbutton: Button
    private lateinit var searchbutton:Button
    private lateinit var viewbutton:Button
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frontpage)
        addbutton= findViewById(R.id.add)
        addbutton.setOnClickListener()
        {
            val intent = Intent(this, AddData::class.java)
            startActivity(intent)
        }
        searchbutton=findViewById(R.id.bookButton)
        searchbutton.setOnClickListener()
        {
            val intent = Intent(this, Searchhotels::class.java)
            startActivity(intent)
        }
        viewbutton=findViewById(R.id.viewButton)
        viewbutton.setOnClickListener()
        {

        }
    }
}

