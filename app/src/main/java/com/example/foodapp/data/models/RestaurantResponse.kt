package com.example.foodapp.data.models


data class RestaurantResponse(
    val success: Boolean,
    val count: Int,
    val data: List<Restaurant>
)