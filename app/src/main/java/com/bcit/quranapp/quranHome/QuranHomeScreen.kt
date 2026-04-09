package com.bcit.quranapp.quranHome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bcit.quranapp.dataModel.Surah

@Composable
fun QuranHomeScreen(
    onSurahClick: (Surah) -> Unit,
    viewModel: QuranHomeViewModel = hiltViewModel()
) {
    val surahState by viewModel.surahState.collectAsState()

    when (surahState) {
        is SurahState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is SurahState.Success -> {
            val surahs = (surahState as SurahState.Success).surahList
            LazyColumn {
                items(surahs) { surah ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Text(text = surah.number.toString(),
                            fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(16.dp))

                        Column (modifier = Modifier.weight(1f)){
                            Text(text = surah.arabicName,
                                fontSize = 16.sp)
                            Text(text = surah.englishName,
                            fontSize = 12.sp)
                            Text(text = surah.englishNameTranslation.name,
                            fontSize = 12.sp)
                            Text(text = surah.numberOfAyahs.toString(),
                                fontSize = 12.sp)
                        }

                        Column(modifier = Modifier.padding(start = 8.dp),
                            horizontalAlignment = Alignment.End) {
                            Text(text = surah.revelationPlace,
                                fontSize = 12.sp)
                            Text(text = surah.revelationOrder.toString(),
                                fontSize = 12.sp)
                        }
                    }

                }

            }
        }

        is SurahState.Error -> {
            val errorMessage = (surahState as SurahState.Error).message
            Text(text = errorMessage)
        }
    }

}

