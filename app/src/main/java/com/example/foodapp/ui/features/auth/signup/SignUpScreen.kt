package com.example.foodapp.ui.features.auth.signup


import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.foodapp.R
import com.example.foodapp.ui.FoodHubTextField
import com.example.foodapp.ui.GroupSocialButtons
import com.example.foodapp.ui.navigation.Home
import com.example.foodapp.ui.navigation.SignIn
import com.example.foodapp.ui.navigation.WelcomeScreen
import com.example.foodapp.ui.theme.Orange
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = hiltViewModel()) {

    val name = viewModel.name.collectAsStateWithLifecycle()
    val email = viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val loading = remember { mutableStateOf(false) }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var passwordVisible by remember { mutableStateOf(false) }



    when (uiState.value) {
        is SignUpViewModel.SignUpEvent.Error -> {
            loading.value = false
            errorMessage.value = (uiState.value as SignUpViewModel.SignUpEvent.Error).message
        }

        is SignUpViewModel.SignUpEvent.Loading -> {
            loading.value = true
            errorMessage.value = null
        }

        else -> {
            loading.value = false
            errorMessage.value = null
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collectLatest { event ->
            when (event) {
                is SignUpViewModel.SignUpNavigationEvent.NavigateToHome -> {
                    navController.navigate(Home){
                        popUpTo(WelcomeScreen){
                            inclusive=true
                        }
                    }
                }
                is SignUpViewModel.SignUpNavigationEvent.NavigateToSignIn -> {
                    navController.navigate(SignIn)
                }
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.auth_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Box(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.signUp),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(40.dp))

            FoodHubTextField(
                value = name.value,
                onValueChange = { viewModel.onNameChange(it) },
                label = {
                    Text(
                        text = stringResource(R.string.name),
                        color = Color.Gray,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(horizontal = 1.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(20.dp))
            FoodHubTextField(
                value = email.value,
                onValueChange = { viewModel.onEmailChange(it) },
                label = {
                    Text(
                        text = stringResource(R.string.email),
                        color = Color.Gray,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(horizontal = 1.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),

                )
            Spacer(modifier = Modifier.size(20.dp))
            FoodHubTextField(
                value = password.value,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = {
                    Text(
                        text = stringResource(R.string.password),
                        color = Color.Gray,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(horizontal = 1.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible=!passwordVisible
                        }
                    ) {
                        Image(
                            painter = painterResource(if (passwordVisible)
                                R.drawable.ic_eye
                            else
                                R.drawable.ic_eye_hide),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                }
            )
            Spacer(modifier = Modifier.size(10.dp))


            Text(text = errorMessage.value ?: "", color = Color.Red)
            Button(
                onClick = viewModel::onSignUpClick,
                colors = ButtonDefaults.buttonColors(containerColor = Orange),
                modifier = Modifier.padding(25.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Box {
                    AnimatedContent(targetState = loading.value) { target ->
                        if (target) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.signUp),
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(70.dp, 8.dp)
                            )
                        }
                    }
                }

            }

            TextButton(
                onClick = {
                    viewModel.onLoginClicked()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account? ")
                        pushStringAnnotation(tag = "SIGNIN", annotation = "Sign In")
                        withStyle(
                            style = SpanStyle(
                                color = Orange
                            )
                        ) { append("Sign In") }
                    },
                    fontSize = 15.sp,
                    color = colorResource(R.color.ligth_text),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }


            Spacer(modifier = Modifier.size(25.dp))
            GroupSocialButtons(
                color = colorResource(R.color.ligth_text),
                onFacebookClick = {},
                onGoogleClick = {})
        }
    }
}




@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    //SignUpScreen()
}