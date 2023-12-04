package com.example.cookbook.domain.use_case

import com.example.cookbook.domain.model.Category
import com.example.cookbook.domain.model.CategoryItems
import com.example.cookbook.domain.repository.Repository
import com.example.cookbook.util.Resource
import io.mockk.*
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class GetAllCategoryUsecaseTest {

    // Declare the dependencies and use MockK's `mockk` function to create mock instances
    private val repository: Repository = mockk()
    private lateinit var usecase: GetAllCategoryUsecase

    @BeforeEach
    fun setup() {
        // Create an instance of the usecase with the mock repository
        usecase = GetAllCategoryUsecase(repository)
    }

    @Test
    fun `invoke should return success with non-empty list`() = runBlocking {
        // Prepare the mock repository behavior
        val categoryList = listOf(
            Category("1", "Category 1", "Description 1", "Thumb 1"),
            Category("2", "Category 2", "Description 2", "Thumb 2")
        )
        coEvery { repository.getAllCategory() } returns Response.success(CategoryItems(categoryList))

        // Call the usecase and collect the result
        val result = usecase.invoke().toList()

        // Verify the repository function was called
        coVerify { repository.getAllCategory() }

        // Verify the result is a success with the expected category list
        assertTrue(result[0] is Resource.Loading) // Check for Resource.Loading
        assertTrue(result[1] is Resource.Success) // Check for Resource.Success
        assertTrue((result[1] as Resource.Success).data == categoryList) // Check the category list
    }

    @Test
    fun `invoke should return error with empty list`() = runBlocking {
        val categoryList = emptyList<Category>()
        coEvery { repository.getAllCategory() } returns Response.success(CategoryItems(categoryList))

        // Call the usecase and collect the result
        val result = usecase.invoke().toList()

        // Verify the repository function was called
        coVerify { repository.getAllCategory() }

        assertTrue(result[0] is Resource.Loading) // Check for Resource.Loading
        assertTrue(result[1] is Resource.Error) // Check for Resource.Error
        assertEquals("Empty data", (result[1] as Resource.Error).message) // Check the error message
    }

    @Test
    fun `invoke should return error on exception`() = runBlocking {
        // Prepare the mock repository behavior to throw an exception
        coEvery { repository.getAllCategory() } throws Exception("Some error")

        // Call the usecase and collect the result
        val result = usecase.invoke().toList()

        // Verify the repository function was called
        coVerify { repository.getAllCategory() }

        // Verify the result is an error with the expected message
        assertTrue(result[0] is Resource.Loading) // Check for Resource.Loading
        assertTrue(result[1] is Resource.Error) // Check for Resource.Error
        assertEquals("Couldn't load data", (result[1] as Resource.Error).message) // Check the error message
    }
}






