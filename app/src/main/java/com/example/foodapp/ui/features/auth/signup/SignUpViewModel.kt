package com.example.foodapp.ui.features.auth.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.FoodApi
import com.example.foodapp.data.FoodHubSession
import com.example.foodapp.data.models.SignUpRequest
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
class SignUpViewModel @Inject constructor(val foodApi: FoodApi, val session: FoodHubSession) : ViewModel() {

    private val _uiState = MutableStateFlow<SignUpEvent>(SignUpEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<SignUpNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }


    fun onSignUpClick() {
        viewModelScope.launch {
            _uiState.value = SignUpEvent.Loading
            val response = safeApiCall {
                foodApi.signUp(
                    SignUpRequest(
                        name = _name.value,
                        email = _email.value,
                        password = _password.value
                    )
                )
            }
            when(response){
                is ApiResponse.Success->{
                    _uiState.value=SignUpEvent.Success
                    session.storeToken(token = response.data.data.token)
                    _navigationEvent.emit(SignUpNavigationEvent.NavigateToHome)
                }
                is ApiResponse.Error->{
                    _uiState.value=SignUpEvent.Error(response.message)
                }
                is ApiResponse.Exception->{
                    _uiState.value=SignUpEvent.Error(response.message)
                }
            }
        }

    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(SignUpNavigationEvent.NavigateToSignIn)
        }
    }


    sealed class SignUpNavigationEvent {
        object NavigateToSignIn : SignUpNavigationEvent()
        object NavigateToHome : SignUpNavigationEvent()
    }

    sealed class SignUpEvent {
        object Nothing : SignUpEvent()
        object Success : SignUpEvent()
        data class Error(val message: String) : SignUpEvent()
        object Loading : SignUpEvent()
    }
}