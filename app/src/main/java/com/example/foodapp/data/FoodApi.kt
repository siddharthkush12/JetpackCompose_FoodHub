package com.example.foodapp.data

import com.example.foodapp.data.models.AuthResponse
import com.example.foodapp.data.models.CategoriesResponse
import com.example.foodapp.data.models.RestaurantMenuResponse
import com.example.foodapp.data.models.RestaurantResponse
import com.example.foodapp.data.models.SignInRequest
import com.example.foodapp.data.models.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FoodApi {

    @POST("api/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<AuthResponse>

    @POST("api/auth/signin")
    suspend fun signIn(@Body request: SignInRequest): Response<AuthResponse>

    @GET("api/food/category")
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("api/food/restaurants")
    suspend fun getRestaurants(): Response<RestaurantResponse>

//    @GET("api/food/restaurants")
//    suspend fun getRestaurants(@Query("lat") lat:Double, @Query("long") long:Double): Response<RestaurantResponse>

    @GET("api/food/{restaurantId}")
    suspend fun getRestaurantsMenu(@Path("restaurantId") restaurantId: String): Response<RestaurantMenuResponse>
}