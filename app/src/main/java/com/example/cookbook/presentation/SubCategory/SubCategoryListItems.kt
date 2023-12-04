package com.example.cookbook.presentation.SubCategory

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.cookbook.domain.model.Meal
import com.example.cookbook.presentation.Utill.Screen


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun SubCategoryListItems(
    modifier: Modifier = Modifier,
    navController: NavController,
    meal: Meal,
    isFavorite: Boolean,
    onFavoriteToggle: (Boolean) -> Unit
)
{

    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White,
        elevation = 8.dp,
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        onClick = {
            navController.navigate(
                Screen.MealDetailScreen.route +
                        "?meal=${meal.strMeal}"
            )
        }
    ) {

        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {


            Box(
                modifier = Modifier.height(150.dp),
            ) {

                GlideImage(
                    model = meal.strMealThumb,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Category Image",

                    )


                  /*  IconButton(
                        onClick = {},
                        modifier = Modifier
                            .background(color = Color.Black.copy(alpha = 0.4f))
                            .align(Alignment.TopEnd),
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "favourite",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )

                    }*/

                IconButton(
                    onClick = { onFavoriteToggle(!isFavorite) },
                    modifier = Modifier
                        .background(color = Color.Black.copy(alpha = 0.4f))
                        .align(Alignment.TopEnd),
                ) {
                    val icon = if (isFavorite) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = "favorite",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                }

            Box(modifier = Modifier.height(62.dp)) {
                Text(
                    text = meal.strMeal,
                    modifier = Modifier
                        .padding(8.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }


        }
    }
}