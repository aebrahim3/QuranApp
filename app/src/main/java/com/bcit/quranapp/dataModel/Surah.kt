package com.bcit.quranapp.dataModel

data class Surah(
    val number: Int,
    val arabicName: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Int,
    val revelationType: String
)
