package com.example.foodapp.ui.features.auth.signin


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.FoodApi
import com.example.foodapp.data.FoodHubSession
import com.example.foodapp.data.models.SignInRequest
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
class SignInViewModel @Inject constructor(val foodApi: FoodApi, val session: FoodHubSession) : ViewModel() {

    private val _uiState = MutableStateFlow<SignInEvent>(SignInEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<SignInNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }


    fun onSignInClick() {
        viewModelScope.launch {
            _uiState.value = SignInEvent.Loading
            val response = safeApiCall {
                foodApi.signIn(
                    SignInRequest(
                        email = _email.value,
                        password = _password.value
                    )
                )
            }
            when(response){
                is ApiResponse.Success->{
                    _uiState.value=SignInEvent.Success
                    session.storeToken(token = response.data.data.token)
                    _navigationEvent.emit(SignInNavigationEvent.NavigateToHome)
                }
                is ApiResponse.Error->{
                    _uiState.value=SignInEvent.Error(response.message)
                }
                is ApiResponse.Exception -> {
                    _uiState.value=SignInEvent.Error(response.message)
                }
            }
        }

    }

    fun onSignUpClick() {
        viewModelScope.launch {
            _navigationEvent.emit(SignInNavigationEvent.NavigateToSignUp)
        }
    }


    sealed class SignInNavigationEvent {
        object NavigateToSignUp : SignInNavigationEvent()
        object NavigateToHome : SignInNavigationEvent()
    }

    sealed class SignInEvent {
        object Nothing : SignInEvent()
        object Success : SignInEvent()
        data class Error(val message: String) : SignInEvent()
        object Loading : SignInEvent()
    }
}