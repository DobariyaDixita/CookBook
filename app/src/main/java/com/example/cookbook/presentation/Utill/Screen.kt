package com.example.cookbook.presentation.Utill

sealed class Screen(val route:String)
{
    object CategoryScreen:Screen("category_screen")
    object SubCategoryScreen:Screen("sub_category_screen")
    object MealDetailScreen:Screen("meal_detail_screen")
}