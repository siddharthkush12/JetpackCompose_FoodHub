package com.example.foodapp.data.models

import com.google.gson.annotations.SerializedName


data class Category(

    @SerializedName("_id")
    val id: String,

    val name: String,
    val imageUrl: String,

    val createdAt: String,
    val updatedAt: String,

    @SerializedName("__v")
    val version: Int
)