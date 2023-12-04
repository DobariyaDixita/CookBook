package com.example.cookbook.presentation.Meal

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.cookbook.domain.model.MealIngredient


@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun MealDetailScreen(
    navController: NavController,
    viewModel1: MealViewModel = hiltViewModel()
) {


    var state = viewModel1.state.value
   // val state by remember { mutableStateOf(viewModel1.state.value) }

    var scaffoldState = rememberScaffoldState()
    var context = LocalContext.current
    var ingredientList = ArrayList<String>()
    var measureList = ArrayList<String>()



    state.mealIngredient?.let { mealIngredient ->
        ingredientList.addAll(
            listOfNotNull(
                mealIngredient.strIngredient1,
                mealIngredient.strIngredient2,
                mealIngredient.strIngredient3,
                mealIngredient.strIngredient4,
                mealIngredient.strIngredient5,
                mealIngredient.strIngredient6,
                mealIngredient.strIngredient7,
                mealIngredient.strIngredient8,
                mealIngredient.strIngredient9,
                mealIngredient.strIngredient10,
                mealIngredient.strIngredient11,
                mealIngredient.strIngredient12,
                mealIngredient.strIngredient13,
                mealIngredient.strIngredient14,
                mealIngredient.strIngredient15,
                mealIngredient.strIngredient16,
                mealIngredient.strIngredient17,
                mealIngredient.strIngredient18,
                mealIngredient.strIngredient19,
                mealIngredient.strIngredient20
            )
        )

        measureList.addAll(
            listOfNotNull(
                mealIngredient.strMeasure1,
                mealIngredient.strMeasure2,
                mealIngredient.strMeasure3,
                mealIngredient.strMeasure4,
                mealIngredient.strMeasure5,
                mealIngredient.strMeasure6,
                mealIngredient.strMeasure7,
                mealIngredient.strMeasure8,
                mealIngredient.strMeasure9,
                mealIngredient.strMeasure10,
                mealIngredient.strMeasure11,
                mealIngredient.strMeasure12,
                mealIngredient.strMeasure13,
                mealIngredient.strMeasure14,
                mealIngredient.strMeasure15,
                mealIngredient.strMeasure16,
                mealIngredient.strMeasure17,
                mealIngredient.strMeasure18,
                mealIngredient.strMeasure19,
                mealIngredient.strMeasure20
            )
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) { it ->


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.mealIngredient?.let { mealDetail ->
               item {
                    mealHeader(navController, mealDetail)
                }

                item {
                    mealSource(mealDetail, context)
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 0.dp, max = 9999.dp) // Set a fixed height
                    ) {
                        mealIngredientList(measureList, ingredientList, mealDetail)
                    }
                }
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




@Composable
fun mealHeader(navController: NavController, mealDetail: MealIngredient) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
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

        Spacer(modifier = Modifier.width(10.dp))


        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = mealDetail.strMeal,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = mealDetail.strCategory,
                    fontSize = 18.sp
                )

                Text(
                    text = " | ${mealDetail.strArea}",
                    fontSize = 18.sp
                )


            }
        }


    }

    Spacer(modifier = Modifier.height(40.dp))
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun mealSource(mealDetail: MealIngredient, context: Context) {
    Row() {

        Column(modifier = Modifier.background(Color.Transparent)) {
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
                backgroundColor = Color(0xffffffe1e1),
                onClick = {
                    val urlIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(mealDetail.strYoutube)
                    )
                    context.startActivity(urlIntent)
                }


            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {


                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "back",
                        tint = Color(0xffffec2d2d)
                    )



                    Spacer(modifier = Modifier.width(10.dp))


                    Text(
                        text = "Watch On Youtube",
                        fontSize = 16.sp,
                        color = Color(0xffffec2d2d)
                    )


                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            Card(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
                backgroundColor = Color(0xffFFDA9C),
                onClick = {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, mealDetail.strSource)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }


            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {


                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "back",
                        tint = Color(0xffA66B08)
                    )



                    Spacer(modifier = Modifier.width(10.dp))


                    Text(
                        text = "Share Recipe",
                        fontSize = 16.sp,
                        color = Color(0xffA66B08)
                    )


                }
            }

            Spacer(modifier = Modifier.height(15.dp))
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
                backgroundColor = Color(0xffC1F8CE),
                onClick = {
                    if(mealDetail.strSource!=null)
                    {
                        val urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(mealDetail.strSource)
                        )
                        context.startActivity(urlIntent)
                    }

                }


            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {


                    Icon(
                        imageVector = Icons.Default.Link,
                        contentDescription = "back",
                        tint = Color(0xffff2a7e2f)
                    )



                    Spacer(modifier = Modifier.width(10.dp))


                    Text(
                        text = "Source",
                        fontSize = 16.sp,
                        color = Color(0xffff2a7e2f)
                    )
                }

            }
        }


        GlideImage(
            model = mealDetail.strMealThumb,
            modifier = Modifier
                .padding(top = 60.dp)
                .height(200.dp)
                .width(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "Category Image"
        )


    }



    Spacer(modifier = Modifier.height(20.dp))
}


@Composable
fun mealIngredientList(
    measureList: ArrayList<String>,
    ingredientList: ArrayList<String>,
    mealDetail: MealIngredient
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color(0xFFfdd835),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    ) {


        Column(
            // Apply verticalScroll modifier
            modifier = Modifier
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {


            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Ingredients:",
                modifier = Modifier.padding(start = 20.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp

            )

            Spacer(modifier = Modifier.height(20.dp))


            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(Modifier.weight(1f)) {
                    LazyColumn() {
                        items(ingredientList) { item ->
                            if (item.isNotEmpty()) {
                                Text(
                                    text = item,
                                )
                            }
                        }
                    }
                }

                Box(Modifier.weight(1f)) {
                    LazyColumn() {
                        items(measureList) { item ->
                            if (item.isNotEmpty()) {
                                Text(
                                    text = item,
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Ingredients:",
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp

            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = mealDetail.strInstructions,
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.body1
            )

            Spacer(modifier = Modifier.height(20.dp))


        }


    }

}





