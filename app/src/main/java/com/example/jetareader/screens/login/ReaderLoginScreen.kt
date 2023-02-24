package com.example.jetareader.screens.login

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetareader.components.EmailInput
import com.example.jetareader.components.PassWordInput
import com.example.jetareader.components.ReaderLogo

@Composable
fun ReaderLoginScreen( navController: NavController) {


    Surface( modifier = Modifier.fillMaxSize() ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top ) {

            ReaderLogo()

            UserForm( loading = false, isCreateAccount = false) { email, password ->
                Log.d( "Form", "ReaderLoginScreen: $email $password")
            }

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


