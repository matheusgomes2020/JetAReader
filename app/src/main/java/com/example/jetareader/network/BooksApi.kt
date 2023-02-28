package com.example.jetareader.network

import com.example.jetareader.model.Book
import com.example.jetareader.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {

    @GET("Volumes")
    suspend fun getAllBooks( @Query("q") query: String ): Book

    @GET("Volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String ): Item

}