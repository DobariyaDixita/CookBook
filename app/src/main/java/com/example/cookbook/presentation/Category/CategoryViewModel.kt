package com.example.cookbook.presentation.Category

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.model.CategoryItems
import com.example.cookbook.domain.repository.Repository
import com.example.cookbook.domain.use_case.GetAllCategoryUsecase
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
class CategoryViewModel @Inject constructor(
    private val getAllCategoryUsecase: GetAllCategoryUsecase
) : ViewModel()
{


    private val _state = mutableStateOf(CategoryInfoState())
    val state: State<CategoryInfoState> = _state
    private var loadingJob: Job? = null

    init {
        loadCategory()
    }

     @SuppressLint("SuspiciousIndentation")
     fun loadCategory() {
        _state.value = state.value.copy(isLoading = true)
         loadingJob?.cancel()
         loadingJob = viewModelScope.launch {
            getAllCategoryUsecase().collect() { result ->
                Log.e("listing",result.data?.size.toString())
                when (result) {
                    is Resource.Success -> {

                        result.data?.let { listings ->

                            _state.value = state.value.copy(
                                categoryList = listings,
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

        }}

    }

}
