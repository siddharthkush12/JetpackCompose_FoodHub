package com.example.foodapp.data.models

data class RestaurantMenu(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val imageUrl: String,
    val name: String,
    val price: Double,
    val restaurantId: String,
    val updatedAt: String
)