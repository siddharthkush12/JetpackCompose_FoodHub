package com.example.foodapp.data.models

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val data: AuthData
)

data class AuthData(
    val token: String,
    val user: User
)

data class User(
    @SerializedName("_id")
    val id: String,

    val name: String,
    val email: String
)