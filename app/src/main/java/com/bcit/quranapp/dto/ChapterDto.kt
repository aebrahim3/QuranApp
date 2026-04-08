package com.bcit.quranapp.dto

/**
 * Each chapter in the JSON file has the following properties:
 * id,
 * name_arabic,
 * name_complex,
 * translated_name,
 * verses_count,
 * revelation_place,
 * revelation_order,
 * bismillah_pre
 * Some properties are excluded, such as name_simple.
 */
data class ChapterDto(
    val id: Int,
    val name_arabic: String,
    val name_complex: String,
    val translated_name: TranslatedNameDto,
    val verses_count: Int,
    val revelation_place: String,
    val revelation_order: Int,
    val bismillah_pre: Boolean
)
