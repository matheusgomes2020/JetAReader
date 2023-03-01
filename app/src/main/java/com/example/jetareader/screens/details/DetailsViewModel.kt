package com.example.jetareader.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.data.Resource
import com.example.jetareader.model.Item
import com.example.jetareader.repository.BookRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor( private val repository: BookRepository)
    : ViewModel() {

        suspend fun getBookInfo( bookId: String ): Resource<Item> {



            return repository.getBookInfo( bookId )

        }

}