package com.example.foodapp.ui.features.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodapp.R
import com.example.foodapp.data.models.Category
import com.example.foodapp.ui.theme.Orange


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        when (val state = uiState.value) {
            is HomeViewModel.HomeScreenState.Loading -> {
                Text(text = "Loading")
            }

            is HomeViewModel.HomeScreenState.Success -> {
                CategoriesList(
                    categories = state.categories,
                    onCategorySelected = {
                        navController.navigate("category/${it.id}")
                    }
                )
            }

            is HomeViewModel.HomeScreenState.Empty -> {
                Text(text = "No categories found")
            }

            is HomeViewModel.HomeScreenState.Error -> {
                Text(text = state.message)
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
        shadowElevation = 8.dp,       // 👈 shadow
        tonalElevation = 2.dp         // 👈 smooth Material look
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


@Preview(showSystemUi = true)
@Composable
fun output1(){
    Surface(
        modifier = Modifier
            .width(85.dp)
            .height(120.dp)
            .padding(8.dp)
            .clickable {  },

        shape = RoundedCornerShape(35.dp),
        color = Color.White,
        shadowElevation = 8.dp,       // 👈 shadow
        tonalElevation = 2.dp         // 👈 smooth Material look
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
    
        ) {

            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Inside
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "namesfa".split(" ").first(),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
            )
        }
    }
}