package com.example.cookbook.presentation.Meal

import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.model.MealIngredient

data class MealInfoState(
    val mealIngredient: MealIngredient? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
