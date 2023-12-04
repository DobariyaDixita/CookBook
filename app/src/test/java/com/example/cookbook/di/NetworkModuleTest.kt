package com.example.cookbook.di

import com.example.cookbook.data.remote_source.Api
import com.example.cookbook.data.repository.RepositoryImpl
import com.example.noteapp.di.NetworkModule
import org.junit.jupiter.api.Assertions.*

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import retrofit2.Retrofit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NetworkModuleTest {
    private lateinit var networkModule: NetworkModule

    @BeforeEach
    fun setup() {
        networkModule = NetworkModule()
    }

    @Test
    fun `test provideApi`() {
        // Create a mock instance of the Retrofit Builder
       /* val retrofitBuilder = mockk<Retrofit.Builder>()
        //val BASE_URL = "something"

        // Mock the behavior of the Retrofit Builder
        every { retrofitBuilder.baseUrl("") } returns retrofitBuilder
        every { retrofitBuilder.addConverterFactory(any()) } returns retrofitBuilder
        every { retrofitBuilder.build() } returns mockk()*/

        // Call the method under test
        val api = networkModule.provideApi()

        assertEquals(Api::class.java, api.javaClass.interfaces[0])


    }

    @Test
    fun `provideRepository should create RepositoryImpl with Api`() {
        // Create a mock instance of Api
        val api = mockk<Api>()

        // Create an instance of NetworkModule
        val networkModule = NetworkModule()

        // Call the provideRepository method
        val repository = networkModule.provideRepository(api)


        assertEquals(RepositoryImpl::class.java, repository.javaClass)

    }


}
