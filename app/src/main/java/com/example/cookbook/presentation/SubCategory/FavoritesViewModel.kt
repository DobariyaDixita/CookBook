package com.example.cookbook.presentation.SubCategory

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.cookbook.domain.model.Meal
import com.example.cookbook.domain.model.MealWithFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel() {
    private val favoriteMeals = mutableStateMapOf<String, Boolean>()

    fun toggleMealFavorite(meal: Meal, isFavorite: Boolean) {
        favoriteMeals[meal.idMeal] = isFavorite
    }

    fun isMealFavorite(meal: Meal): Boolean {
        return favoriteMeals[meal.idMeal] ?: false
    }
}