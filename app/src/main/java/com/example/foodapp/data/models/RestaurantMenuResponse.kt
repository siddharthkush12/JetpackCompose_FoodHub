package com.example.foodapp.data.models

data class RestaurantMenuResponse(
    val count: Int,
    val `data`: List<RestaurantMenu>,
    val success: Boolean
)