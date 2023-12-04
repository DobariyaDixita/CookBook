package com.example.cookbook.presentation.Meal

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.model.Meal
import com.example.cookbook.domain.model.MealDetail
import com.example.cookbook.domain.model.MealIngredient
import com.example.cookbook.domain.repository.Repository
import com.example.cookbook.domain.use_case.GetMealUsecase
import com.example.cookbook.presentation.SubCategory.SubCategoryInfoState
import com.example.cookbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val getMealUsecase: GetMealUsecase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(MealInfoState())
    val state: State<MealInfoState> = _state

    //var state by mutableStateOf(MealInfoState())

    private var loadingJob: Job? = null
    init {
        loadMeal()
    }

    private fun loadMeal() {
        _state.value = state.value.copy(isLoading = true)
        val mealDetail = savedStateHandle.get<String>("meal").orEmpty()
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            getMealUsecase(mealDetail).collect() { result ->

                when (result) {
                    is Resource.Success -> {

                        result.data?.let { listings ->
                            _state.value = state.value.copy(
                                mealIngredient = listings[0],
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {

                        _state.value = state.value.copy(error = result.message, isLoading = false)
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = result.isLoading)
                    }
                }
            }
        }

    }


  /*  private val _mealDetail = mutableStateListOf<MealIngredient>()
    val mealDetail: List<MealIngredient>
        get() = _mealDetail


    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                loadMealDetail()
            }

        }
    }

    private suspend fun loadMealDetail() {

        try {
            val mealDetail = savedStateHandle.get<String>("meal").orEmpty()


            val data = repository.getMeal(mealDetail)


            if (data.isSuccessful) {
                _mealDetail.addAll(data.body()?.meals!!)
            } else {
                Log.e("data", data.message())
            }
        } catch (ex: Exception) {
            Log.e("Exception", ex.message.toString())
        }
    }*/


}