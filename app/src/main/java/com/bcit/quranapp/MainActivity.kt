package com.bcit.quranapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bcit.quranapp.auth.AuthViewModel
import com.bcit.quranapp.auth.LoginScreen
import com.bcit.quranapp.ayahsPage.AyahsPageScreen
import com.bcit.quranapp.quranHome.QuranHomeScreen
import com.bcit.quranapp.ui.theme.QuranAppTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuranAppTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = hiltViewModel()

                val startDestination = if (authViewModel.isLoggedIn) "home" else "login"

                NavHost(navController = navController, startDestination = startDestination) {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("home") {
                        QuranHomeScreen(
                            onSurahClick = { surah ->
                                navController.navigate("ayahs/${surah.number}")
                            }
                        )
                    }
                    composable("ayahs/{chapterNumber}") {
                        AyahsPageScreen(

                        )
                    }
                }
            }
        }
    }
}



