package com.example.hotelbooking
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.database.ktx.database

class AddData : AppCompatActivity() {

    private lateinit var selectedImageUri: Uri
    private lateinit var imageView: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var ratingEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adddata)

        imageView = findViewById(R.id.imageView)
        nameEditText = findViewById(R.id.nameEditText)
        locationEditText = findViewById(R.id.locationEditText)
        ratingEditText = findViewById(R.id.ratingEditText)

        val selectButton: Button = findViewById(R.id.selectButton)
        val uploadButton: Button = findViewById(R.id.uploadButton)

        selectButton.setOnClickListener {
            // Launch the image picker intent
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

        uploadButton.setOnClickListener {
            // Upload the selected image to Firebase Storage
            uploadImageToFirebase()

            // Upload text data to Firebase Realtime Database
            uploadTextDataToFirebase()
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let {
                selectedImageUri = it
                imageView.setImageURI(selectedImageUri)
            }
        }
    }

    private fun uploadImageToFirebase() {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imagesRef = storageRef.child("images/${selectedImageUri.lastPathSegment}")

        imagesRef.putFile(selectedImageUri)
            .addOnSuccessListener {
                // Image upload successful
                Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()
                val downloadUrlTask = it.metadata?.reference?.downloadUrl
                downloadUrlTask?.addOnSuccessListener { downloadUri ->
                    // Handle the image download URL (downloadUri)
                }
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }

    private fun uploadTextDataToFirebase() {
        val databaseRef = Firebase.database.reference
        val name = nameEditText.text.toString()
        val location = locationEditText.text.toString()
        val rating = ratingEditText.text.toString()

        val newData = mapOf(
            "name" to name,
            "location" to location,
            "rating" to rating
        )

        databaseRef.child("data").push().setValue(newData)
            .addOnSuccessListener {
                Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this,"Unsuccessfully Saved",Toast.LENGTH_SHORT).show()
            }
    }
}
