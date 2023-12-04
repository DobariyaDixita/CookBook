package com.example.cookbook.data.repository

import com.example.cookbook.data.remote_source.Api
import com.example.cookbook.domain.model.CategoryItems
import com.example.cookbook.domain.model.MealDetail
import com.example.cookbook.domain.model.SubCategory
import com.example.cookbook.domain.repository.Repository
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl  @Inject constructor(
    private  val api: Api,
) : Repository  {
    override suspend fun getAllCategory(): Response<CategoryItems> {
       return api.getAllCategory()
    }

    override suspend fun getAllSubCategory(subCategory: String): Response<SubCategory> {
        return api.getAllSubCategory(subCategory)
    }

    override suspend fun getMeal(meal: String): Response<MealDetail> {
        return api.getMeal(meal)
    }

}