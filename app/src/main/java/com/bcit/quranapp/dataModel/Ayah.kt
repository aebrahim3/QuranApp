package com.bcit.quranapp.dataModel

import com.bcit.quranapp.dto.TafsirsDto
import com.bcit.quranapp.dto.TranslationDto
import com.google.gson.annotations.SerializedName

/**
 * All these properties pop up on the card whether the user clicks on it or not. Tafsir and audio are
 * on demand. That is why they are not included in the data class. If they were fetched every time
 * the ayahs got fetched, that would waste resource and time, especially if the user does not
 * use those features.
 */
data class Ayah(
    @SerializedName("verse_number")
    val number: Int,
    @SerializedName("text_uthmani")
    val arabicText: String,
    @SerializedName("translations")
    val translation: List<TranslationDto>,
)
