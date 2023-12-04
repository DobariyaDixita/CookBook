package com.example.cookbook.domain.repository

import com.example.cookbook.data.remote_source.Api
import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.model.CategoryItems
import com.example.cookbook.domain.model.MealDetail
import com.example.cookbook.domain.model.SubCategory
import retrofit2.Response
import javax.inject.Inject

interface Repository {

    suspend fun getAllCategory() : Response<CategoryItems>

    suspend fun getAllSubCategory(subCategory: String) : Response<SubCategory>


    suspend fun getMeal(meal: String) : Response<MealDetail>

}