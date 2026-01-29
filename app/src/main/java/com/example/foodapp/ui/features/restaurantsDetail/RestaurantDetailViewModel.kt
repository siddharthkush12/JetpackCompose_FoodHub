package com.example.foodapp.ui.features.restaurantsDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.FoodApi
import com.example.foodapp.data.models.RestaurantMenu
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
class RestaurantDetailViewModel @Inject constructor(private val foodApi: FoodApi): ViewModel() {

    private val _navigationEvent = MutableSharedFlow<RestaurantDetailNavigationState?>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _restaurantDetailState = MutableStateFlow<RestaurantDetailState>(RestaurantDetailState.Loading)
    val restaurantDetailState = _restaurantDetailState.asStateFlow()



    fun loadRestaurantDetailMenu(restaurantId: String){
        viewModelScope.launch {
            when(val res= safeApiCall { foodApi.getRestaurantsMenu(restaurantId)}){
                is ApiResponse.Success -> {
                    _restaurantDetailState.value =
                        if (res.data.data.isEmpty())
                            RestaurantDetailState.Empty
                        else
                            RestaurantDetailState.Success(res.data.data)
                }
                is ApiResponse.Error -> {
                    _restaurantDetailState.value = RestaurantDetailState.Error(res.message)
                }
                is ApiResponse.Exception -> {
                    _restaurantDetailState.value = RestaurantDetailState.Error(res.message)
                }
            }
        }
    }




    sealed class RestaurantDetailNavigationState{
        object NavigationToHome: RestaurantDetailNavigationState()
    }


    sealed class RestaurantDetailState{
        object Loading: RestaurantDetailState()
        object Empty: RestaurantDetailState()
        data class Success(val restaurantDetailMenu:List<RestaurantMenu>): RestaurantDetailState()
        data class Error(val message:String): RestaurantDetailState()

    }
}