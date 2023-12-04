package com.example.cookbook.domain.use_case

import com.example.cookbook.domain.model.Meal
import com.example.cookbook.domain.model.MealDetail
import com.example.cookbook.domain.model.MealIngredient
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
class GetMealUsecaseTest {

    private var repository = mockk<Repository>()
    private lateinit var usecase: GetMealUsecase

    @BeforeEach
    fun setUP() {
        usecase = GetMealUsecase(repository)
    }

    @Test
    fun `invoke should return success with non-empty list`() = runBlocking {


        val mealList = listOf(
            MealIngredient(
                "1",
                "Meal 1",
                "Ingredient 1",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "", "", "", "", "", "", "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "", "", ""
            ),
        )

        var mealName = "pizza"

        coEvery { repository.getMeal(any()) } returns Response.success(MealDetail(mealList))


        var result = usecase.invoke(mealName).toList()

        coVerify { repository.getMeal(mealName) }

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertTrue((result[1] as Resource.Success).data == mealList)
    }

    @Test
    fun `invoke should return error with empty list`() = runBlocking {

        var mealList = emptyList<MealIngredient>()
        var mealName = "pizza"

        coEvery { repository.getMeal(any()) } returns Response.success(MealDetail(mealList))
        var result = usecase.invoke(mealName).toList()

        coVerify { repository.getMeal(mealName) }

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertTrue((result[1] as Resource.Error).message == "Empty data")
    }

    @Test
    fun `invoke should return error on exception`() = runBlocking {
        // Prepare the mock repository behavior to throw an exception
        var mealName = "pizza"

        coEvery { repository.getMeal(any()) } throws Exception("Some error")

        // Call the usecase and collect the result
        val result = usecase.invoke(mealName).toList()

        // Verify the repository function was called
        coVerify { repository.getMeal(mealName) }

        // Verify the result is an error with the expected message
        assertTrue(result[0] is Resource.Loading) // Check for Resource.Loading
        assertTrue(result[1] is Resource.Error) // Check for Resource.Error
        assertEquals(
            "Couldn't load data",
            (result[1] as Resource.Error).message
        ) // Check the error message
    }
}