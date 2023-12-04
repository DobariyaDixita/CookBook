package com.example.cookbook.data.repository

import com.example.cookbook.data.remote_source.Api
import com.example.cookbook.domain.model.*
import com.example.cookbook.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test



class RepositoryImplTest {

    private val api: Api = mockk()
    private val repository: Repository = RepositoryImpl(api)

    @Test
    fun `test getAllCategory`() = runBlocking {
        // Prepare
        val expectedResponse: Response<CategoryItems> = mockk()
        coEvery { api.getAllCategory() } returns expectedResponse

        // Act
        val result = repository.getAllCategory()

        // Assert
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `test getAllSubCategory`() = runBlocking {
        // Prepare
        val subCategory = "someSubCategory"
        val expectedResponse: Response<SubCategory> = mockk()
        coEvery { api.getAllSubCategory(subCategory) } returns expectedResponse

        // Act
        val result = repository.getAllSubCategory(subCategory)

        // Assert
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `test getMeal`() = runBlocking {
        // Prepare
        val meal = "someMeal"
        val expectedResponse: Response<MealDetail> = mockk()
        coEvery { api.getMeal(meal) } returns expectedResponse

        // Act
        val result = repository.getMeal(meal)

        // Assert
        assertEquals(expectedResponse, result)
    }
}






