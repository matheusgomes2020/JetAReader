package com.example.jetareader.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.jetareader.components.FABContent
import com.example.jetareader.components.ReaderAppBar
import com.example.jetareader.components.TitleSection
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
        
    }
    
}

@Composable
fun ReadingRightNoteArea( books: List<MBook>,
                          navController: NavController ) {



}

@Preview
@Composable
fun ListCard( book: MBook = MBook( "asfd", "Running", "Me and You", "Hello world"),
              onPressDetails: (String) -> Unit = {  } ) {

    val context = LocalContext.current

    val resources = context.resources

    val spacing = 10.dp

    val displayMetrics = resources.displayMetrics

    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
        elevation = 6.dp,
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable { onPressDetails.invoke(book.title.toString()) } ) {

        Column( modifier = Modifier.width( screenWidth.dp - ( spacing * 2 ) ) ,
            horizontalAlignment = Alignment.Start ) {

            Row( horizontalArrangement = Arrangement.Center ) {

                Image(painter = rememberImagePainter(data = "") ,
                    contentDescription = "book image",
                modifier = Modifier
                    .height(140.dp)
                    .width(100.dp)
                    .padding(4.dp))
                
                Spacer( modifier = Modifier.width( 50.dp  ) )

                Column( modifier = Modifier.padding( top = 25.dp ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    
                    Icon(imageVector = Icons.Rounded.FavoriteBorder ,
                        contentDescription = "Fav Icon" ,
                        modifier = Modifier.padding( bottom = 1.dp ) )

                    BookingRating( score = 3.5 )
                    
                }
                
            }

            Text( text = "Book title", modifier = Modifier.padding( 4.dp ),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis )

            Text( text = "Authors All...", modifier = Modifier.padding( 4.dp ),
                style = MaterialTheme.typography.caption )
            
        }

    }


}

@Composable
fun BookingRating(score: Double = 4.5 ) {

    Surface(modifier = Modifier
        .height(70.dp)
        .padding(4.dp),
        shape = RoundedCornerShape( 56.dp ),
        elevation = 6.dp,
        color = Color.White) {

        Column(modifier = Modifier.padding( 4.dp )) {

            Icon(imageVector = Icons.Filled.StarBorder, contentDescription = "Start",
            modifier = Modifier.padding( 3.dp ) )

            Text(text = score.toString(), style = MaterialTheme.typography.subtitle1 )
            
        }

    }

}
