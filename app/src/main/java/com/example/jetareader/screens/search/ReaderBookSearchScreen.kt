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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetareader.components.InputField
import com.example.jetareader.components.ReaderAppBar
import com.example.jetareader.model.MBook
import com.example.jetareader.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun SearchScreen(navController: NavController = NavController( LocalContext.current )) {

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
                        .padding(16.dp) ) {

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

            val imageUrl = "data:image/jpeg;base64,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"

            Image(painter = rememberImagePainter( data = imageUrl ) ,
                contentDescription = "book image",
            modifier = Modifier.width( 80.dp )
                .fillMaxHeight()
                .padding( end = 4.dp ) )

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