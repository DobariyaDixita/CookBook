package com.example.cookbook.presentation.Category

import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.model.CategoryItems

data class CategoryInfoState (
    val categoryList: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
