package com.example.cookbook.domain.use_case

import com.example.cookbook.domain.model.Meal
import com.example.cookbook.domain.model.MealIngredient
import com.example.cookbook.domain.repository.Repository
import com.example.cookbook.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMealUsecase @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(meal: String): Flow<Resource<List<MealIngredient>>> = channelFlow {

        try {
            send(Resource.Loading(true))
            val mealList = withContext(Dispatchers.IO) {
                repository.getMeal(meal)
            }
            mealList.body()?.meals?.let { list ->
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