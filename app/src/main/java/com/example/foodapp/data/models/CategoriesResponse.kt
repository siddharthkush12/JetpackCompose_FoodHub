package com.example.foodapp.data.models



data class CategoriesResponse(
    val success: Boolean,
    val count: Int,
    val data: List<Category>
)