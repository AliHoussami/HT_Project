package com.example.ht.data.model
import com.google.gson.annotations.SerializedName

data class User(
    val id: String? = null,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)
