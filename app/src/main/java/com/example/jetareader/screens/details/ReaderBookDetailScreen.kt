package com.example.jetareader.screens.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetareader.components.ReaderAppBar
import com.example.jetareader.data.Resource
import com.example.jetareader.model.Item
import com.example.jetareader.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookDetailsScreen(navController: NavController,
                      bookId: String,
                      viewModel: DetailsViewModel = hiltViewModel() ) {

    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book Details",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    }) {

        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading() ) {

                    value = viewModel.getBookInfo( bookId )

                }.value

                if ( bookInfo.data == null ) {

                    LinearProgressIndicator()

                }else {

                    Text(text = "Book: ${ bookInfo.data.volumeInfo.title }" )

                }

                Log.d( "Deets", "BookDetailsScreen: ${bookInfo.data.toString()}" )
                


            }

        }

    }
}
            