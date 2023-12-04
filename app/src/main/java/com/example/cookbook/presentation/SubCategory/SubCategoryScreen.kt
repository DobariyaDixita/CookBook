package com.example.cookbook.presentation.SubCategory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.model.Meal

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SubCategoryScreen(
    navController: NavController,
    strCategory: Category?,
    viewModel: SubCategoryViewModel = hiltViewModel()
) {
    var scaffoldState = rememberScaffoldState()
    //var mealsList = viewModel.subCategory
    var state = viewModel.state.value

    var favoritesViewModel:FavoritesViewModel = hiltViewModel()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                IconButton(
                    onClick = {
                        navController.navigateUp()
                    },
                )
                {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back"
                    )

                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Food List ${strCategory?.strCategory}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )


            }
        }
    ) { it ->



        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            item {
                subCategoryHeader(navController, strCategory)
            }
            item {
                subcategoryGrid(
                    subCategoryList = state.subCategoryList,
                    navController = navController,
                    favoritesViewModel
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if(state.isLoading) {
                CircularProgressIndicator()
            } else if(state.error != null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colors.error
                )
            }
        }


    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun subCategoryHeader(
    navController: NavController,
    strCategory: Category?
) {
    Column(modifier = Modifier.fillMaxSize()) {




        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp,
            backgroundColor = Color(0xff009ea4).copy(alpha = 0.8f),
            modifier = Modifier
                .height(200.dp)
                .padding(20.dp),
        ) {

            GlideImage(
                model = strCategory?.strCategoryThumb,
                contentScale = ContentScale.Crop,
                contentDescription = "Category Image",
                alpha = 0.3f
            )
            Row(
                modifier = Modifier.weight(5f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                GlideImage(
                    model = strCategory?.strCategoryThumb,
                    contentDescription = "Category Image",
                    modifier = Modifier
                        .weight(2f)
                        .padding(10.dp)
                )
                Text(
                    text = strCategory?.strCategoryDescription!!,
                    color = Color.White,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(3f)
                        .padding(10.dp)
                )

            }
        }

        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "Food List",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun subcategoryGrid(
    subCategoryList: List<Meal>,
    navController: NavController,
    favoritesViewModel: FavoritesViewModel
) {
    val columns = 2
    val rows = subCategoryList.size / columns + if (subCategoryList.size % columns == 0) 0 else 1

    for (rowIndex in 0 until rows) {
        Row(Modifier.fillMaxWidth()) {
            for (columnIndex in 0 until columns) {
                val index = rowIndex * columns + columnIndex
                if (index < subCategoryList.size) {
                    val subCategory = subCategoryList[index]
                    val isFavorite = favoritesViewModel.isMealFavorite(subCategory)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .clickable {

                            }
                    ) {
                        SubCategoryListItems(
                            navController = navController,
                            meal = subCategory,
                            isFavorite = isFavorite,
                            onFavoriteToggle = { isFavorite ->
                                favoritesViewModel.toggleMealFavorite(subCategory, isFavorite)
                            }
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}