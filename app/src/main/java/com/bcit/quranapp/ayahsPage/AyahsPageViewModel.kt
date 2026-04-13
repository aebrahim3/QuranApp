package com.bcit.quranapp.ayahsPage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcit.quranapp.dataModel.Ayah
import com.bcit.quranapp.dataModel.Tafsir
import com.bcit.quranapp.network.QuranApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * We use HiltViewModel to inject QuranApi and savedStateHandle
 * into this ViewModel. We use savedStateHandle here because
 * we cannot inject an Int in the constructor of this ViewModel,
 * which is created by Hilt. If we tried to define the variable after
 * the constructor (in fetchAyahs()), then we would have to call fetchAyahs()
 * in the Screen and use backStackEntry.arguments to get the value from
 * the composable. Calling fetchAyahs() in the screen diverges from MVVM
 * separation concern.
 *
 * To try to call fetchAyahs() in the ViewModel plus defining the variable
 * (and retrieving the value with backStackEntry.arguments)
 * would not work because init {fetchAyahs()} gets called before the screen
 * has a chance to pass down the value to the ViewModel.
 *
 * Using SavedStateHandle solves that issue because SavedStateHandle automatically
 * saves the navigation value passed down from the screen. Then we just have
 * to inject the SavedStateHandle and do a key-lookup to get the intended value.
 * All this happens before init {fetchAyahs()}.
 *
 * Overall, SavedStateHandle is the much better option, as it automatically
 * saves navigation values and we can easily inject into the ViewModel.
 *
 *

 */
@HiltViewModel
class AyahsPageViewModel @Inject constructor(
    private val quranApi: QuranApi,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _ayahState = MutableStateFlow<AyahState>(AyahState.Loading)
    val ayahState: StateFlow<AyahState> = _ayahState

    private val _tafsirState = MutableStateFlow<TafsirState>(TafsirState.Loading)
    val tafsirState: StateFlow<TafsirState> = _tafsirState

    val chapterNumber = savedStateHandle.get<Int>("chapterNumber") ?: 1

    init {
        fetchAyahs()
    }

    private fun fetchAyahs() {
        _ayahState.value = AyahState.Loading
        viewModelScope.launch {
            try {
                val ayahList = quranApi.getAyahs(chapterNumber)
                _ayahState.value = AyahState.Success(ayahList.verses)
            } catch (e: Exception) {
                _ayahState.value = AyahState.Error(e.message ?: "Something went wrong")
            }

        }


    }


    fun fetchTafsir(ayahNumber: Int) {
        _tafsirState.value = TafsirState.Loading
        viewModelScope.launch {
            try {
                val tafsir = quranApi.getTafsir(
                    168,
                    "$chapterNumber:$ayahNumber"
                )
                _tafsirState.value = TafsirState.Success(tafsir)
            } catch (e: Exception) {
                _tafsirState.value = TafsirState.Error(e.message ?: "Something went wrong")
            }


        }

    }
}

    sealed class AyahState {
        object Loading : AyahState()
        data class Success(val ayahList: List<Ayah>) : AyahState()
        data class Error(val message: String) : AyahState()

    }

    sealed class TafsirState {
        object Loading : TafsirState()
        data class Success(val tafsir: Tafsir) : TafsirState()
        data class Error(val message: String) : TafsirState()
    }



