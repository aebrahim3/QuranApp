package com.bcit.quranapp.ayahsPage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import com.bcit.quranapp.dataModel.Ayah
import com.bcit.quranapp.ayahsPage.AyahsPageViewModel

/**
 * If ayahState is successful, then parameter ayahList of AyahState.Success
 * is stored in ayahs. In ViewModel, ayahList.verses becomes the new ayahList
 * parameter of AyahState.Success. So, now ayahState.ayahList now actually
 * accesses the list of ayahs and not the response object. The confusion
 * came from the fact that there were 2 different ayahLists, one as a parameter
 * of AyahState.Success and the other a response object, or wrapper, that holds
 * list of verses.
 *
 *
 */
@Composable
fun AyahsPageScreen(
    onAyahClick: (Ayah) -> Unit,
    viewModel: AyahsPageViewModel = hiltViewModel()
) {
    val ayahState by viewModel.ayahState.collectAsState()

    when (ayahState) {
        is AyahState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        }

        is AyahState.Success -> {
            val ayahs = (ayahState as AyahState.Success).ayahList
            LazyColumn {
                items(ayahs) { ayah ->
                    Column {
                        Row {
                            Text(text = ayah.number.toString(), fontSize = 8.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = ayah.arabicText, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = ayah.translation.first().text, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                }


            }

        }

        is AyahState.Error -> {
            val errorMessage = (ayahState as AyahState.Error).message
            Text(text = errorMessage)

        }
    }
}
