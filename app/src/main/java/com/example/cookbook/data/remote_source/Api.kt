package com.example.cookbook.data.remote_source

import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.model.CategoryItems
import com.example.cookbook.domain.model.MealDetail
import com.example.cookbook.domain.model.SubCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

interface Api {

    @GET("categories.php")
    suspend fun getAllCategory() : Response<CategoryItems>

    @GET("filter.php")
    suspend fun getAllSubCategory(@Query("c") strCategory: String) : Response<SubCategory>

    //www.themealdb.com/api/json/v1/1/search.php?s=Peanut Butter Cheesecake
    //www.themealdb.com/api/json/v1/1/lookup.php?i=52893
    @GET("search.php")
    suspend fun getMeal(@Query("s") strCategory: String) : Response<MealDetail>
}