package com.example.jetareader.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetareader.R
import com.example.jetareader.components.EmailInput
import com.example.jetareader.components.PassWordInput
import com.example.jetareader.components.ReaderLogo

@Composable
fun ReaderLoginScreen( navController: NavController) {

    val showLoginForm = rememberSaveable{

        mutableStateOf( true )

    }

    Surface( modifier = Modifier.fillMaxSize() ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top ) {

            ReaderLogo()

            if ( showLoginForm.value ) UserForm( loading = false, isCreateAccount = false) { email, password ->
                //Todo FB login
            }
            else {

                UserForm( loading = false, isCreateAccount = true ) { email, password ->
                    //Todo: create FB account
                }
            }
        }

        Spacer( modifier = Modifier.height( 15.dp ) )

        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val text = if ( showLoginForm.value ) "Sign up" else "Login"

            Text( text = "New User?")

            Text( text,
            modifier = Modifier
                .clickable {

                    showLoginForm.value = !showLoginForm.value

                }
                .padding(start = 5.dp),
            fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondaryVariant )

        }

    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(

    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: ( String, String ) -> Unit = { email, pwd -> }

        ) {

    val email = rememberSaveable { mutableStateOf("") }
    val passWord = rememberSaveable { mutableStateOf("") }
    val passWordVisibility = rememberSaveable { mutableStateOf(false) }
    val passWordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, passWord.value) {

        email.value.trim().isNotEmpty() && passWord.value.trim().isNotEmpty()

    }

    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        if ( isCreateAccount ) Text(text = stringResource(id = R.string.create_acct ) ,
                                    modifier = Modifier.padding( 4.dp ) ) else Text( "" )

        EmailInput(
            emailState = email, enabled = !loading,
            onAction = KeyboardActions {

                passWordFocusRequest.requestFocus()

            },
        )

        PassWordInput(
            modifier = Modifier.focusRequester(passWordFocusRequest),
            passWordState = passWord,
            labelId = "Password",
            enabled = !loading,
            passWordVisibility = passWordVisibility,
            onAction = KeyboardActions {

                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), passWord.value.trim())

            })

        SubmitButton(

            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ) {

            onDone(email.value.trim(), passWord.value.trim())
            keyboardController?.hide()
        }


    }
}

@Composable
fun SubmitButton(textId: String ,
                  loading: Boolean ,
                  validInputs: Boolean,
                onClick: () -> Unit ) {

    Button(
        onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape) {

        if ( loading ) CircularProgressIndicator( modifier = Modifier.size( 25.dp ) )
        else Text( text = textId, modifier = Modifier.padding( 5.dp ) )

        }

}


