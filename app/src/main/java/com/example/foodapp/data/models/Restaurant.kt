package com.example.foodapp.data.models

data class Restaurant(
    val _id: String,
    val name: String,
    val address: String,
    val imageUrl: String,
    val createdAt: String,
    val updatedAt: String,
    val location: Location,
    val categoryId: CategoryId,
    val ownerId: OwnerId,
    val __v: Int
)


data class OwnerId(
    val _id: String,
    val name: String,
    val email: String
)


data class Location(
    val type: String,
    val coordinates: List<Double> // [longitude, latitude]
)

data class CategoryId(
    val _id: String,
    val name: String,
    val imageUrl: String
)