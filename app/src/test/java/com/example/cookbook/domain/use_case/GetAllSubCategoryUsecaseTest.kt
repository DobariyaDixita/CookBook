package com.example.cookbook.domain.use_case

import com.example.cookbook.domain.model.Meal
import com.example.cookbook.domain.model.SubCategory
import com.example.cookbook.domain.repository.Repository
import com.example.cookbook.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class GetAllSubCategoryUsecaseTest {

    private var repository = mockk<Repository>()
    private lateinit var usecase: GetAllSubCategoryUsecase

    @BeforeEach
    fun setUP() {
        usecase = GetAllSubCategoryUsecase(repository)
    }

    @Test
    fun `invoke should return success with non-empty list`() = runBlocking {


        var mealList = listOf<Meal>(
            Meal(
                "1", "meal1", "thumb1"
            ),
            Meal(
                "2", "meal2", "thumb2"
            )
        )

        var categoryName = "veg"

        coEvery { repository.getAllSubCategory(categoryName) } returns Response.success(SubCategory(mealList))


        var result = usecase.invoke(categoryName).toList()

        coVerify { repository.getAllSubCategory(categoryName) }

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertTrue((result[1] as Resource.Success).data == mealList )
    }

    @Test
    fun `invoke should return error with empty list`() = runBlocking {

        var mealList = emptyList<Meal>()
        var categoryName = "veg"

        coEvery { repository.getAllSubCategory(categoryName) } returns Response.success(SubCategory(mealList))
        var result = usecase.invoke(categoryName).toList()

        coVerify { repository.getAllSubCategory(categoryName) }

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertTrue((result[1] as Resource.Error).message == "Empty data" )
    }

    @Test
    fun `invoke should return error on exception`() = runBlocking {
        // Prepare the mock repository behavior to throw an exception
        var categoryName = "veg"

        coEvery { repository.getAllSubCategory(categoryName) }throws Exception("Some error")

        // Call the usecase and collect the result
        val result = usecase.invoke(categoryName).toList()

        // Verify the repository function was called
        coVerify { repository.getAllSubCategory(categoryName) }

        // Verify the result is an error with the expected message
        assertTrue(result[0] is Resource.Loading) // Check for Resource.Loading
        assertTrue(result[1] is Resource.Error) // Check for Resource.Error
        assertEquals("Couldn't load data", (result[1] as Resource.Error).message) // Check the error message
    }

}