package com.example.hotelbooking

import java.io.Serializable

data class hotels(val imageUrl: String?="",val location: String?="",val name: String?="",val rating: String?="") :
    Serializable
{}