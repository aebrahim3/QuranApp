package com.bcit.quranapp.dataModel

import com.bcit.quranapp.dto.TranslatedNameDto
import com.google.gson.annotations.SerializedName

data class Surah(
    @SerializedName("id")
    val number: Int,
    @SerializedName("name_arabic")
    val arabicName: String,
    @SerializedName("name_complex")
    val englishName: String,
    @SerializedName("translated_name")
    val englishNameTranslation: TranslatedNameDto,
    @SerializedName("verses_count")
    val numberOfAyahs: Int,
    @SerializedName("revelation_place")
    val revelationPlace: String,
    @SerializedName("revelation_order")
    val revelationOrder: Int,
    @SerializedName("bismillah_pre")
    val bismillahPre: Boolean
)
