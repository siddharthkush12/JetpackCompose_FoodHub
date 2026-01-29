package com.example.foodapp.ui.features.restaurantsDetail

import android.view.Menu
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodapp.R
import com.example.foodapp.data.models.RestaurantMenu
import com.example.foodapp.ui.theme.Orange



@Composable
fun RestaurantDetailScreen(
    navController: NavController,
    name: String,
    imageUrl: String,
    restaurantId: String,
    viewModel: RestaurantDetailViewModel = hiltViewModel()
) {

    val restaurantDetailState = viewModel.restaurantDetailState.collectAsStateWithLifecycle()
    LaunchedEffect(restaurantId) {
        viewModel.loadRestaurantDetailMenu(restaurantId)
    }


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            RestaurantDetailHeader(
                name = name,
                imageUrl = imageUrl,
                onBackButton = { navController.popBackStack() },
                onHeartButton = { }
            )
        }

        item(span = { GridItemSpan(2) }) {
            Text(
                text = "Menu Items",
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp, 0.dp)
            )
        }


        when (val state = restaurantDetailState.value) {
            is RestaurantDetailViewModel.RestaurantDetailState.Loading -> {
                item(span = { GridItemSpan(2) }) {
                    Text("Loading...")
                }
            }

            is RestaurantDetailViewModel.RestaurantDetailState.Empty -> {
                item(span = { GridItemSpan(2) }) {
                    Text("No menu found")
                }
            }

            is RestaurantDetailViewModel.RestaurantDetailState.Error -> {
                item(span = { GridItemSpan(2) }) {
                    Text(state.message)
                }
            }

            is RestaurantDetailViewModel.RestaurantDetailState.Success -> {
                items(state.restaurantDetailMenu) { menu ->
                    RestaurantMenuItemsCard(menu)
                }
            }
        }



        item(span = { GridItemSpan(2) }) {
            Spacer(
                modifier = Modifier.padding(10.dp, 0.dp)
            )
        }
    }
}


@Composable
fun RestaurantDetailHeader(
    name: String,
    imageUrl: String,
    onBackButton: () -> Unit,
    onHeartButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)

    ) {
        Box {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomEnd = 20.dp,
                            bottomStart = 20.dp
                        )
                    ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, 30.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(
                    onClick = {onBackButton()}

                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp, 25.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(70.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.heart),
                        contentDescription = null,
                    )
                }
            }
        }


        Column(
            modifier = Modifier.padding(15.dp, 10.dp)
        ) {
            Text(
                text = name,
                fontSize = 25.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.starrr),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = "4.5"
                )
                Spacer(modifier = Modifier.size(5.dp))
                TextButton(
                    onClick = {}
                ) {
                    Text(
                        text = "See Review",
                        color = Orange,
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                }
            }
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = "Brown the beaf better, Lean ground beef - I like to use 85% leon angus. Gorlic- use fresh chopped spices - chilli powder, cumin, garlic powder, salt, black pepper and cayenne. ",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun RestaurantMenuItemsCard(
    menu: RestaurantMenu
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(210.dp)
            .clickable { },

        shape = RoundedCornerShape(25.dp),
        color = Color.White,
        shadowElevation = 3.dp,
        tonalElevation = 1.dp
    ) {
        Column {
            AsyncImage(
                model = menu.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = menu.name,
                modifier = Modifier.padding(10.dp, 0.dp)
            )

            Text(
                text = "SBaker",
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.padding(10.dp, 0.dp)
            )
        }
    }
}
