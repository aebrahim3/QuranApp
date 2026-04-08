package com.bcit.quranapp.dto

/**
 * Since translated_name is nested, it is its own
 * object, containing immutable values.
 */
data class TranslatedNameDto(
    val language_name: String,
    val name: String
)
