package com.example.foodapp.ui.features.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.R
import com.example.foodapp.ui.GroupSocialButtons
import com.example.foodapp.ui.navigation.SignIn
import com.example.foodapp.ui.navigation.SignUp

@Composable
fun WelcomeScreen(navController: NavController) {
    val imageSize = remember {
        mutableStateOf(IntSize.Zero)
    }
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black
        ),
        startY = 100f,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcomeback),
            contentDescription = null,
            modifier = Modifier
                .onGloballyPositioned {
                    imageSize.value = it.size
                }
                .alpha(0.8f)

        )
        Box(modifier = Modifier
            .matchParentSize()
            .background(brush = brush))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.align(Alignment.TopEnd).padding(15.dp,25.dp).height(30.dp),
            contentPadding = PaddingValues(12.dp,0.dp)
        ){
            Text(
                text = stringResource(R.string.skip),
                color = colorResource(R.color.orange),
                fontSize = 13.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp,130.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome),
                color = Color.Black,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.app_name),
                color = colorResource(R.color.orange),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.welcome_tag),
                color = colorResource(R.color.ligth_text),
                fontSize = 15.sp
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(30.dp,30.dp)
        ) {

            GroupSocialButtons(onFacebookClick = {}, onGoogleClick = {})

            Button(
                onClick = {navController.navigate(SignUp)},
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.4f)),
                border = BorderStroke(1.dp,Color.White)
            ){
                Text(
                    text = stringResource(R.string.start),
                    color = Color.White,
                    fontSize = 17.sp
                )
            }


            TextButton(
                onClick = {navController.navigate(SignIn)},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.already_have_account),
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun WelcomeScreenPreview() {
//    WelcomeScreen(rememberNavController())
//}