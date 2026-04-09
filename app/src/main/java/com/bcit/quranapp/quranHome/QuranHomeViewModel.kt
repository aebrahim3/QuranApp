package com.bcit.quranapp.quranHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcit.quranapp.dataModel.Surah
import com.bcit.quranapp.network.QuranApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * We use HiltViewModel to inject QuranApi into this ViewModel.
 * Initially, we want the Surahs to be fetched and the loading
 * screen to show. What fetchSurah() does is that it uses the getSurah()
 * method in the AppModule.kt file to get the usable data using Retrofit and
 * Gson. The resulting product gets stored in surahList. We set the
 * _surahState to Success, and Success holds the list of Surahs
 * inside surahList, which holds the ChaptersResponse object.
 * If something goes wrong, we set the _surahState to Error.
 */
@HiltViewModel
class QuranHomeViewModel @Inject constructor(
    private val quranApi: QuranApi
) : ViewModel() {

    private val _surahState = MutableStateFlow<SurahState>(SurahState.Loading)

    val surahState: StateFlow<SurahState> = _surahState

    init {
        fetchSurahs()
    }

    private fun fetchSurahs() {
        _surahState.value = SurahState.Loading
        viewModelScope.launch {
            try {
                val surahList = quranApi.getSurahs()
                _surahState.value = SurahState.Success(surahList.chapters)

            } catch (e: Exception) {
                _surahState.value = SurahState.Error(e.message ?: "Something went wrong")
            }
        }

    }

}


sealed class SurahState {
    object Loading : SurahState()
    data class Success(val surahList: List<Surah>) : SurahState()
    data class Error(val message: String) : SurahState()
}

