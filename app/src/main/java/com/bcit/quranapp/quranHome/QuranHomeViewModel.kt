package com.bcit.quranapp.quranHome

import androidx.lifecycle.ViewModel
import com.bcit.quranapp.dataModel.Surah
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuranHomeViewModel : ViewModel() {

    private val _surahState = MutableStateFlow<SurahState>(SurahState.Loading)

    val surahState: StateFlow<SurahState> = _surahState

}


sealed class SurahState {
    object Loading : SurahState()
    data class Success(val surahList: List<Surah>) : SurahState()
    data class Error(val message: String) : SurahState()
}

