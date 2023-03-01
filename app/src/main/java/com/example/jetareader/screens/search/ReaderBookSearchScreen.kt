package com.example.jetareader.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetareader.components.InputField
import com.example.jetareader.components.ReaderAppBar
import com.example.jetareader.model.MBook
import com.example.jetareader.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen( navController: NavController, viewModel: BookSearchViewModel =  hiltViewModel() ) {

    Scaffold(topBar = {

        ReaderAppBar(title = "Search Books" ,
            icon = Icons.Default.ArrowBack,
            navController = navController,
        showProfile = false ) {

            //navController.popBackStack()
            navController.navigate( ReaderScreens.ReaderHomeScreen.name )

        }

    }) {
        
        Surface() {
            
            Column {

                SearchForm(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                        viewModel) { query ->

                    viewModel.searchBooks( query )

                    Log.d( "TAG", "SearchScreen: $it")

                }

                Spacer(modifier = Modifier.height( 13.dp ) )

                BookList( navController )

            }
            
        }
        
    }

}

@Composable
fun BookList(navController: NavController) {

    val listOfBooks = listOf(
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "dadfa", title = " Again", authors = "All of us", notes = null),
        MBook(id = "dadfa", title = "Hello ", authors = "The world us", notes = null),
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null)
    )

    LazyColumn( modifier = Modifier.fillMaxSize() ,
        contentPadding = PaddingValues( 16.dp ) ) {


        items( items = listOfBooks ) { book ->

            BookRow( book, navController )

        }

    }

}

@Composable
fun BookRow(book: MBook,
            navController: NavController) {

    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp
    ) {

        Row( modifier = Modifier.padding( 5.dp ),
            verticalAlignment = Alignment.Top ) {

            val imageUrl =  "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80"
            Image(painter = rememberImagePainter( data = imageUrl ) ,
                contentDescription = "book image",
            modifier = Modifier
                .width(80.dp)
                .fillMaxHeight()
                .padding(end = 4.dp) )

            Column() {

                Text( text = book.title.toString(), overflow = TextOverflow.Ellipsis )

                Text( text = book.title.toString(), overflow = TextOverflow.Ellipsis )

                Text( text = "Author: ${ book.authors }" ,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.caption )

                //Todo: Add more fields later

            }

        }

    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(

    modifier: Modifier = Modifier,
    viewModel: BookSearchViewModel,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: ( String ) -> Unit = {  } ) {

    val searchQueryState = rememberSaveable {

        mutableStateOf( "" )

    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val valid = remember {

        searchQueryState.value.trim().isNotEmpty()

    }

    InputField(valueState = searchQueryState,  labelId = "Search" , enabled = true ,
    onAction = KeyboardActions {

        if ( !valid ) return@KeyboardActions
        onSearch( searchQueryState.value.trim() )
        searchQueryState.value = ""
        keyboardController?.hide()

    }
    )

}