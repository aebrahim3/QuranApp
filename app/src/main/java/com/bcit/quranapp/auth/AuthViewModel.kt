package com.bcit.quranapp.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


/**
 * Goal is to access Firestore, which stores the relevant data of the user. While
 * the screen is loading, a Google picker opens for the user to login. Logging in
 * successfully results in a token that is used for access to Firebase.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    val isLoggedIn get() = auth.currentUser != null

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val credentialManager = CredentialManager.create(context)

                // We want to sign in through Google. Also, the accounts that
                // that show up in the picker should not be restricted, but
                // rather all device accounts will be displayed. We also
                // give Google our client ID to make clear to Google what
                // what app is seeking its token.
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("AIzaSyAdyXHnsfKwA95fH2ENusKogiLYqWx7ihs")
                    .build()

                //Build a request in which is specified that
                // we want to sign in through Google and that
                // all device accounts should be shown. The
                // request also contains the clientId, which
                // is used to make clear to Google what app
                // is seeking its token.
                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                // Builds a getCredentialResponse() object, which
                // contains the request (in which is the specification
                // that all Google accounts will be displayed and the client ID.
                // We also specify what screen the picker will be on top of,
                // which is this screen.
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                // We build a GoogleIdTokenCredential object, which takes
                // in the raw data of the credential object and cleans it
                // up to be used by Firebase.
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(result.credential.data)

                // We create something to be used by firebaseCredential, which
                // takes in the token.
                val firebaseCredential = GoogleAuthProvider.getCredential(
                    googleIdTokenCredential.idToken,
                    null
                )

                // We use the newly made credential to get access to Firebase.
                // If successful, set _authState to Success. If not, send
                // error message.
                auth.signInWithCredential(firebaseCredential).await()
                _authState.value = AuthState.Success
            } catch (e: GetCredentialException) {
                _authState.value = AuthState.Error(e.message ?: "Sign in failed")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }
}
    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        object Success : AuthState()
        data class Error(val message: String) : AuthState()
    }


