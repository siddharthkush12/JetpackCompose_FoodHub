package com.example.foodapp

import android.R
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.foodapp.data.FoodApi
import com.example.foodapp.data.FoodHubSession
import com.example.foodapp.ui.features.auth.WelcomeScreen
import com.example.foodapp.ui.features.auth.signin.SignInScreen
import com.example.foodapp.ui.features.auth.signup.SignUpScreen
import com.example.foodapp.ui.features.home.HomeScreen
import com.example.foodapp.ui.features.restaurantsDetail.RestaurantDetailScreen
import com.example.foodapp.ui.navigation.Home
import com.example.foodapp.ui.navigation.RestaurantDetail
import com.example.foodapp.ui.navigation.SignIn
import com.example.foodapp.ui.navigation.SignUp
import com.example.foodapp.ui.navigation.WelcomeScreen
import com.example.foodapp.ui.theme.FoodappTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var showSplashScreen = true

    @Inject
    lateinit var foodApi: FoodApi

    @Inject
    lateinit var session: FoodHubSession
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                showSplashScreen
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    1f, 0.0f
                )
                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    1f, 0.0f
                )
                val animatorSet = AnimatorSet().apply {
                    playTogether(zoomX, zoomY)
                    duration = 500
                    interpolator = OvershootInterpolator(3f)
                    doOnEnd {
                        screen.remove()
                    }
                }
                animatorSet.start()
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodappTheme {
                val startPoint = if (session.getToken() != null) Home else WelcomeScreen
                val navController = rememberNavController()
                SharedTransitionLayout {
                    NavHost(
                        navController = navController,
                        startDestination = startPoint,
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))
                        }
                    ) {
                        composable<WelcomeScreen> {
                            WelcomeScreen(navController)
                        }
                        composable<SignUp> {
                            SignUpScreen(navController)
                        }
                        composable<SignIn> {
                            SignInScreen(navController)
                        }
                        composable<Home> {
                            HomeScreen(navController)
                        }
                        composable<RestaurantDetail> {
                            val route = it.toRoute<RestaurantDetail>()
                            RestaurantDetailScreen(
                                navController,
                                name = route.restaurantName,
                                imageUrl = route.restaurantImageUrl,
                                restaurantId = route.restaurantId
                            )
                        }
                    }
                }
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            showSplashScreen = false
        }

    }
}

