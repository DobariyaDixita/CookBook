package com.example.cookbook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cookbook.domain.model.Category
import com.example.cookbook.presentation.Category.categoryScreen
import com.example.cookbook.presentation.Meal.MealDetailScreen
import com.example.cookbook.presentation.SubCategory.SubCategoryScreen
import com.example.cookbook.presentation.Utill.CategoryNavType
import com.example.cookbook.presentation.Utill.Screen
import com.example.cookbook.ui.theme.CookBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                )
                {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CategoryScreen.route
                    )
                    {
                        composable(route = Screen.CategoryScreen.route)
                        {
                            Box (modifier = Modifier.fillMaxSize()){
                                categoryScreen(
                                    navController = navController,
                                )
                            }
                        }
                        composable(
                            //route = Screen.SubCategoryScreen.route + "?strCategory={strCategory}",
                            route = "sub_category_screen/{strCategory}",
                           arguments = listOf(
                                navArgument(
                                    name = "strCategory"
                                )
                                {
                                    type = CategoryNavType()
                                },
                            )
                        )
                        {
                            @Suppress("DEPRECATION") val strCategory = it.arguments?.getParcelable<Category>("strCategory")
                                SubCategoryScreen(
                                navController = navController,
                                strCategory = strCategory
                            )
                        }
                        composable(
                            route = Screen.MealDetailScreen.route + "?meal={meal}",
                            arguments = listOf(
                                navArgument(
                                    name = "meal"
                                )
                                {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                            )
                        )
                        {
                            MealDetailScreen(
                                navController = navController,
                                )
                        }
                    }

                }
            }
        }
    }
}