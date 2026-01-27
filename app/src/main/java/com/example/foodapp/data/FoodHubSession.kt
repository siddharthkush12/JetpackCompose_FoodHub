package com.example.foodapp.data

import android.content.Context
import android.content.SharedPreferences

class FoodHubSession(val context: Context) {

    val sharedPref: SharedPreferences= context.getSharedPreferences("FoodHubSession", Context.MODE_PRIVATE)

    init {

    }

    fun storeToken(token: String) {
        sharedPref.edit().putString("token",token).apply()
    }
    fun getToken():String?{
        sharedPref.getString("token",null)?.let{
            return it
        }
        return null
    }
}