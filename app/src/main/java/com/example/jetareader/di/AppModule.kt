package com.example.jetareader.di

import androidx.compose.ui.unit.Constraints
import com.example.jetareader.network.BooksApi
import com.example.jetareader.repository.BookRepository
import com.example.jetareader.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn( SingletonComponent::class )
object AppModule {

    @Singleton
    @Provides
    fun provideBookRepository( api: BooksApi ) = BookRepository( api )

    @Singleton
    @Provides
    fun provideBookApi(): BooksApi {

        return Retrofit.Builder()
            .baseUrl( Constants.BASE_URL )
            .addConverterFactory( GsonConverterFactory.create() )
            .build()
            .create( BooksApi::class.java )

    }

}