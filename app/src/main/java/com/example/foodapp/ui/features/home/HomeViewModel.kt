package com.example.foodapp.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.FoodApi
import com.example.foodapp.data.models.Category
import com.example.foodapp.data.models.Restaurant
import com.example.foodapp.data.remote.ApiResponse
import com.example.foodapp.data.remote.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val foodApi: FoodApi) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<HomeScreenNavigationEvents?>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    private val _categoryState =
        MutableStateFlow<CategoryState>(CategoryState.Loading)
    val categoryState = _categoryState.asStateFlow()

    private val _restaurantState =
        MutableStateFlow<RestaurantState>(RestaurantState.Loading)
    val restaurantState = _restaurantState.asStateFlow()


    init {
        loadCategories()
        loadRestaurants()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            when (val res = safeApiCall { foodApi.getCategories() }) {
                is ApiResponse.Success ->
                    _categoryState.value =
                        if (res.data.data.isEmpty())
                            CategoryState.Empty
                        else
                            CategoryState.Success(res.data.data)

                is ApiResponse.Error ->
                    _categoryState.value =
                        CategoryState.Error(res.message)

                is ApiResponse.Exception ->
                    _categoryState.value =
                        CategoryState.Error(res.message)
            }
        }
    }


    private fun loadRestaurants() {
        viewModelScope.launch {
            when (val res = safeApiCall { foodApi.getRestaurants() }) {
                is ApiResponse.Success ->
                    _restaurantState.value =
                        if (res.data.data.isEmpty())
                            RestaurantState.Empty
                        else
                            RestaurantState.Success(res.data.data)

                is ApiResponse.Error ->
                    _restaurantState.value =
                        RestaurantState.Error(res.message)

                is ApiResponse.Exception ->
                    _restaurantState.value =
                        RestaurantState.Error(res.message)
            }
        }
    }


    sealed class HomeScreenNavigationEvents {
        object NavigationToDetail : HomeScreenNavigationEvents()
    }


    sealed class CategoryState {
        object Loading : CategoryState()
        object Empty : CategoryState()
        data class Success(val data: List<Category>) : CategoryState()
        data class Error(val message: String) : CategoryState()
    }

    sealed class RestaurantState {
        object Loading : RestaurantState()
        object Empty : RestaurantState()
        data class Success(val data: List<Restaurant>) : RestaurantState()
        data class Error(val message: String) : RestaurantState()
    }


}