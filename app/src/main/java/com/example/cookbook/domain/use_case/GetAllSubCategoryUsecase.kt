package com.example.cookbook.domain.use_case

import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.model.Meal
import com.example.cookbook.domain.model.SubCategory
import com.example.cookbook.domain.repository.Repository
import com.example.cookbook.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllSubCategoryUsecase  @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(subCategory: String): Flow<Resource<List<Meal>>> = channelFlow {

        try {
            send(Resource.Loading(true))
            val subcategoryList = withContext(Dispatchers.IO) {
                repository.getAllSubCategory(subCategory)
            }
            subcategoryList.body()?.meals?.let { list ->
                if (list.isNotEmpty()) {
                    send(Resource.Success(list))
                } else {
                    send(Resource.Error("Empty data"))
                }
            }


        } catch (e: Exception) {
            send(Resource.Error("Couldn't load data"))
        }

    }
}