package com.example.cookbook.domain.use_case

import android.util.Log
import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.repository.Repository
import com.example.cookbook.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllCategoryUsecase @Inject constructor(
    private val repository: Repository
){
     operator fun invoke(): Flow<Resource<List<Category>>> = channelFlow {

        try {
            send(Resource.Loading(true))
            val categoryList = withContext(Dispatchers.IO) {
                repository.getAllCategory()
            }

            categoryList.body()?.categories?.let { list ->
                    if (list.isNotEmpty()) {
                        send(Resource.Success(list))
                    } else {
                        send(Resource.Error("Empty data"))
                    }
                }


             }catch (e: Exception) {
                send(Resource.Error("Couldn't load data"))
            }

        }
}
