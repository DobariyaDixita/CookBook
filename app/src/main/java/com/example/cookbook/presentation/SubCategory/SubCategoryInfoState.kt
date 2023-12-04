package com.example.cookbook.presentation.SubCategory

import com.example.cookbook.domain.model.Meal

data class SubCategoryInfoState(
    val subCategoryList: List<Meal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)