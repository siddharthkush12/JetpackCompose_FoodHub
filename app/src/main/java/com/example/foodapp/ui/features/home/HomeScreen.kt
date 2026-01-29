package com.example.foodapp.ui.features.home


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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodapp.R
import com.example.foodapp.data.models.Category
import com.example.foodapp.data.models.Restaurant
import com.example.foodapp.ui.navigation.RestaurantDetail
import com.example.foodapp.ui.theme.Orange


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val categoryState = viewModel.categoryState.collectAsStateWithLifecycle()
        val restaurantState = viewModel.restaurantState.collectAsStateWithLifecycle()

        when (val state = categoryState.value) {
            is HomeViewModel.CategoryState.Loading -> {
                Text(text = "Loading")
            }

            is HomeViewModel.CategoryState.Empty -> {
                Text(text = "No categories found")
            }

            is HomeViewModel.CategoryState.Error -> {
                Text(text = state.message)
            }

            is HomeViewModel.CategoryState.Success -> {
                CategoriesList(
                    categories = state.data,
                    onCategorySelected = {
                        navController.navigate("category/${it.id}")
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        when (val state = restaurantState.value) {
            is HomeViewModel.RestaurantState.Loading -> {
                Text(text = "Loading")
            }

            is HomeViewModel.RestaurantState.Empty -> {
                Text(text = "No restaurants found")
            }

            is HomeViewModel.RestaurantState.Error -> {
                Text(text = state.message)
            }

            is HomeViewModel.RestaurantState.Success -> {
                RestaurantList(
                    restaurants = state.data,
                    onRestaurantSelected = {
                        navController.navigate(
                            RestaurantDetail(
                                restaurantId = it._id,
                                restaurantName = it.name,
                                restaurantImageUrl = it.imageUrl
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun CategoriesList(
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit
) {
    LazyRow {
        items(categories) { category ->
            CategoryItem(
                category = category,
                onCategorySelected = onCategorySelected
            )
        }
    }
}


@Composable
fun CategoryItem(
    category: Category,
    onCategorySelected: (Category) -> Unit
) {
    Surface(
        modifier = Modifier
            .width(85.dp)
            .height(125.dp)
            .padding(8.dp)
            .clickable { onCategorySelected(category) },

        shape = RoundedCornerShape(35.dp),
        color = Color.White,
        shadowElevation = 8.dp,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AsyncImage(
                model = category.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Inside
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = category.name.split(" ").first(),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
            )
        }
    }
}




@Composable
fun RestaurantList(
    restaurants: List<Restaurant>,
    onRestaurantSelected: (Restaurant) -> Unit
) {
    Box {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Featured Restaurants", fontSize = 20.sp)

                TextButton(
                    onClick = {}
                ) {
                    Text(text = "View All >", fontSize = 17.sp, color = Orange)
                }
            }

            LazyRow {
                items(restaurants) { restaurant ->
                    RestaurantItem(
                        restaurant = restaurant,
                        onRestaurantSelected = onRestaurantSelected
                    )
                }
            }
        }
    }
}




@Composable
fun RestaurantItem(
    restaurant: Restaurant,
    onRestaurantSelected: (Restaurant) -> Unit
) {
    Surface(
        modifier = Modifier
            .width(300.dp)
            .height(270.dp)
            .padding(8.dp)
            .clickable { onRestaurantSelected(restaurant) },

        shape = RoundedCornerShape(25.dp),
        color = Color.White,
        shadowElevation = 3.dp,
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = restaurant.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(Color.White, CircleShape)
                        .padding(8.dp, 2.dp),
                ) {
                    Text(text = "4.5 ★", fontSize = 17.sp)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(0.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.heart),
                        contentDescription = null,
                        modifier = Modifier.size(68.dp)
                    )
                }
            }



            Spacer(modifier = Modifier.height(3.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
            ) {
                Text(
                    text = restaurant.name,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row {
                    Image(
                        painter = painterResource(R.drawable.bike),
                        contentDescription = null,
                        modifier = Modifier
                            .size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Free delivery", fontSize = 14.sp)

                    Spacer(modifier = Modifier.width(20.dp))

                    Image(
                        painter = painterResource(R.drawable.timer),
                        contentDescription = null,
                        modifier = Modifier
                            .size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "10-15 mins", fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .background(Color.LightGray)
                            .padding(6.dp, 2.dp),

                        ) {
                        Text("Burger", fontSize = 12.sp, color = colorResource(R.color.ligth_text))
                    }
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .background(Color.LightGray)
                            .padding(6.dp, 2.dp),

                        ) {
                        Text(
                            "Fast Food",
                            fontSize = 12.sp,
                            color = colorResource(R.color.ligth_text)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .background(Color.LightGray)
                            .padding(6.dp, 2.dp),

                        ) {
                        Text("Chicken", fontSize = 12.sp, color = colorResource(R.color.ligth_text))
                    }
                }
            }
        }
    }
}


