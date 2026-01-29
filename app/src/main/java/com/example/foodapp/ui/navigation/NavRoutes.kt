package com.example.foodapp.ui.navigation

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer


@Serializable
object WelcomeScreen

@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
object Home

@Serializable
data class RestaurantDetail(
    val restaurantId: String,
    val restaurantName: String,
    val restaurantImageUrl: String,
)
