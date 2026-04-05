package com.bcit.quranapp.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * UI for Login. Passes LocalContext (information about local environment) down
 * from signInWithGoogle(), using it to create a new instance of CredentialManager, and wrapping
 * it in a GetCredentialRequest, in order to obtain a token for Firebase. This second
 * sentence refers to ViewModel actions, but it is part of context, which originates
 * in this screen.
 *
 */
@Composable
fun LoginScreen(
    // Specifies what kind of function. onLoginSuccess is further
    // elaborated on in the MainActivity.kt file.
    onLoginSuccess: () -> Unit,
    // We use hiltViewModel() because only it can grab the injected instance from
    // AppModule.kt. We need that instance in AuthViewModel, as it manages
    // authentication; it lets you sign in, sign out, and get the current
    // user.
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    // should be collectAsState() because it is a StateFlow in a composable.
    val authState by viewModel.authState.collectAsState()

    // We use LaunchedEffect because the code inside it should run only once.
    // We don't want to go back to the home screen every time the composable
    // recomposes.
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) onLoginSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to QuranBase", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Sign in to save you progress", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(48.dp))

        Button(onClick = { viewModel.signInWithGoogle(context) }) {
            Text("Sign in with Google")
        }
        if (authState is AuthState.Loading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        if (authState is AuthState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text((authState as AuthState.Error).message, color = MaterialTheme.colorScheme.error)

        }



    }




}
