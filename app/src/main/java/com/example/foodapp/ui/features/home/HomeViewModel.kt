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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val foodApi: FoodApi): ViewModel(){

    private val _uiState= MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val uiState:StateFlow<HomeScreenState> = _uiState.asStateFlow()

    private val _navigationEvent= MutableSharedFlow<HomeScreenNavigationEvents?>()
    val navigationEvent= _navigationEvent.asSharedFlow()


    init {
        getCategories()
        getPopularRestaurants()
    }

    fun getCategories() {
        viewModelScope.launch{
            val response= safeApiCall {
                foodApi.getCategories()
            }
            when(response){
                is ApiResponse.Success->{
                    val categories = response.data.data
                    if (categories.isEmpty()) {
                        _uiState.value = HomeScreenState.Empty
                    } else {
                        _uiState.value =
                            HomeScreenState.Success(categories)
                    }
                }
                is ApiResponse.Error -> {
                    _uiState.value=HomeScreenState.Error(response.message)
                }
                is ApiResponse.Exception -> {
                    _uiState.value=HomeScreenState.Error(response.message)
                }
            }
        }
    }

    fun getPopularRestaurants(){

    }




    sealed class HomeScreenNavigationEvents{
        object NavigationToDetail: HomeScreenNavigationEvents()
    }


    sealed class HomeScreenState{
        object Loading: HomeScreenState()
        object Empty: HomeScreenState()
        data class Success(val categories: List<Category>,val restaurants: List<Restaurant>) : HomeScreenState()
        data class Error(val message: String) : HomeScreenState()

    }



}