package com.example.noteapp.di

import androidx.lifecycle.ViewModelProvider
import com.example.cookbook.data.remote_source.Api
import com.example.cookbook.data.repository.RepositoryImpl
import com.example.cookbook.domain.repository.Repository
import com.example.cookbook.domain.utill.MyConstants.BASE_URL
import com.example.cookbook.presentation.SubCategory.FavoritesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideApi(): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: Api): Repository {
        return RepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideFavoritesViewModel(): FavoritesViewModel =
        ViewModelProvider.NewInstanceFactory().create(FavoritesViewModel::class.java)

}