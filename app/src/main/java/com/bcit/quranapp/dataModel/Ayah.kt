package com.bcit.quranapp.dataModel

/**
 * All these properties pop up on the card whether the user clicks on it or not. Tafsir and audio are
 * on demand. That is why they are not included in the data class. If they were fetched every time
 * the ayahs got fetched, that would waste resource and time, especially if the user does not
 * use those features.
 */
data class Ayah(
    val number: Int,
    val arabicText: String,
    val translation: String
)
