package com.example.cookbook.presentation.Category

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.cookbook.domain.model.Category
import com.google.gson.Gson


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun categoryListItems(
     navController: NavController,
    modifier: Modifier = Modifier,
    category: Category,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White,
        elevation = 8.dp,
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        onClick = {

            val json = Uri.encode(Gson().toJson(category))
            navController.navigate("sub_category_screen/$json")
        }
    ) {

        Box(
            modifier = modifier
                .fillMaxSize()
        ) {


            GlideImage(
                model = category.strCategoryThumb,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = "Category Image"
            )

            Text(
                text = category.strCategory,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .background(color = Color.Black.copy(alpha = 0.3f)),
                color = Color.White,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center


            )
        }
    }

}


