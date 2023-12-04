package com.example.cookbook.presentation.Utill

import android.os.Bundle
import androidx.navigation.NavType
import com.example.cookbook.domain.model.Category
import com.google.gson.Gson

class CategoryNavType : NavType<Category>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Category? {
        @Suppress("DEPRECATION")
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Category {
        return Gson().fromJson(value, Category::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Category) {
        bundle.putParcelable(key, value)
    }
}
