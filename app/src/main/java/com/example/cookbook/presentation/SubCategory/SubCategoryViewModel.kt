package com.example.cookbook.presentation.SubCategory

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.model.Meal
import com.example.cookbook.domain.repository.Repository
import com.example.cookbook.domain.use_case.GetAllSubCategoryUsecase
import com.example.cookbook.presentation.Category.CategoryInfoState
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
class SubCategoryViewModel @Inject constructor(
    private val getAllSubCategoryUsecase: GetAllSubCategoryUsecase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    //var state by mutableStateOf(SubCategoryInfoState())

    private val _state = mutableStateOf(SubCategoryInfoState())
    val state: State<SubCategoryInfoState> = _state
    private var loadingJob: Job? = null

    init {
        loadSubCategory()
    }

    private fun loadSubCategory() {
        _state.value = state.value.copy(isLoading = true)
        val categoryName = savedStateHandle.get<Category>("strCategory")
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            getAllSubCategoryUsecase(categoryName?.strCategory!!).collect() { result ->

                when (result) {
                    is Resource.Success -> {

                        result.data?.let { listings ->
                            _state.value = state.value.copy(
                                subCategoryList = listings,
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


    /*private val _subCategory = mutableStateListOf<Meal>()
    val subCategory: List<Meal>
        get() = _subCategory
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                loadSubCategory()
            }

        }
    }

    @SuppressLint("SuspiciousIndentation")
    private suspend fun loadSubCategory() {

            try {
                //val categoryName = savedStateHandle.get<String>("strCategory").orEmpty()
                val categoryName = savedStateHandle.get<Category>("strCategory")
                val data = repository.getAllSubCategory(categoryName?.strCategory!!)

                    if(data.isSuccessful)
                    {
                        _subCategory.addAll(data.body()?.meals!!)
                    }
                    else
                    {
                        Log.e("data",data.message())
                    }


            } catch (ex: Exception) {
                Log.e("Exception",ex.message.toString())
            }
        }*/


}