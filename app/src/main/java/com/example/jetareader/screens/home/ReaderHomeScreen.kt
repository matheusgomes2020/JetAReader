package com.example.jetareader.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetareader.components.*
import com.example.jetareader.model.MBook
import com.example.jetareader.navigation.ReaderScreens
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.time.format.TextStyle

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun Home(  navController: NavController = NavController( LocalContext.current ) ) {
    
    Scaffold( topBar = {

                       ReaderAppBar(title = "A.Reader", navController = navController )

    },
    floatingActionButton = {
        
        FABContent{}
        
    }) {
        
        //content
        Surface( modifier = Modifier.fillMaxSize()) {
            
            //home content
            HomeContent( navController )
            
        }
        
    }
    
}

@Composable
fun HomeContent( navController: NavController ) {

    val listOfBooks = listOf(
          MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "dadfa", title = " Again", authors = "All of us", notes = null),
        MBook(id = "dadfa", title = "Hello ", authors = "The world us", notes = null),
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null)
                            )
        //me @gmail.com

    val email = FirebaseAuth.getInstance().currentUser?.email

    val currentUserName = if ( !email.isNullOrEmpty() )
        FirebaseAuth.getInstance().currentUser?.email?.split( "@" )
            ?.get( 0 ) else
                "N/A"
    
    Column(Modifier.padding(2.dp) ,
        verticalArrangement = Arrangement.Top ) {
        
        Row( modifier = Modifier.align( alignment = Alignment.Start ) ) {

            TitleSection( label = "Your reading \n " + " activity right now..." )
            
            Spacer(modifier = Modifier.fillMaxWidth( 0.7f ))

            Column {

                Icon(imageVector = Icons.Filled.AccountCircle ,
                    contentDescription = "Profile" ,
                modifier = Modifier
                    .clickable {

                        navController.navigate(ReaderScreens.ReaderStatsScreen.name)

                    }
                    .size(45.dp),
                tint = MaterialTheme.colors.secondaryVariant)

                Text(text = currentUserName!!,
                modifier = Modifier.padding( 2.dp ),
                style = MaterialTheme.typography.overline,
                color = Color.Red,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip )

                Divider()

            }
            
        }

        ReadingRightNoteArea(books = listOf() ,
            navController = navController )

        TitleSection( label = "Reading List" )

        BookListArea( listOfBooks = listOfBooks , navController = navController )
        
    }
    
}

@Composable
fun BookListArea(listOfBooks: List<MBook>,
                 navController: NavController) {

    HorizontalScroollableComponent( listOfBooks ) {

        Log.d( "TAG", "BookListArea: $it" )

        //Todo: on card clicked navigate to details

    }

}

@Composable
fun HorizontalScroollableComponent(listOfBooks: List<MBook>, onCardPressed: (String) -> Unit) {

    val scrollState = rememberScrollState()

    Row( modifier = Modifier
        .fillMaxWidth()
        .height(280.dp)
        .horizontalScroll(scrollState) ) {

        for ( book in listOfBooks ) {

            ListCard( book ) {

                onCardPressed( it )

            }

        }

    }

}

@Composable
fun ReadingRightNoteArea( books: List<MBook>,
                          navController: NavController ) {

    ListCard()


}



