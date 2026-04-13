package com.bcit.quranapp.dataModel

import com.bcit.quranapp.dto.ChapterVersesDto
import com.bcit.quranapp.dto.TranslatedNameDto
import com.google.gson.annotations.SerializedName

data class Tafsir(
    @SerializedName("verses")
    val versesChapter: ChapterVersesDto,
    val translated_name: TranslatedNameDto,
    val text : String

)


