package com.example.jetareader.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.data.DataOrException
import com.example.jetareader.data.Resource
import com.example.jetareader.model.Item
import com.example.jetareader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor( private val repository: BookRepository ):
            ViewModel(){

    var list: List<Item> by mutableStateOf( listOf() )

    val listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>>
    = mutableStateOf( DataOrException( null, true, Exception( "" ) ) )

    init {
        searchBooks( "android" )
    }

    private fun loadBooks() {

        searchBooks( "android" )

    }

     fun searchBooks(query: String) {

        viewModelScope.launch( Dispatchers.Default )  {

            if ( query.isEmpty() ) {

                return@launch

            }

            try {

                when( val response = repository.getBooks( query ) ) {

                    is Resource.Success -> {

                        list = response.data!!

                    }

                    is Resource.Error -> {

                        Log.e( "Network", "searchBooks: Failed getting books", )

                    }

                    else -> {  }

                }

            }catch ( exception: Exception ) {

                Log.d( "Network", "searchBooks: ${ exception.message.toString() }")

            }

        }

    }

 }
