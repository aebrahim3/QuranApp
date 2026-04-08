package com.bcit.quranapp.dto

/**
 * Refers to the "chapters" heading in the
 * JSON file. It contains a list of chapters.
 */
data class ChaptersResponse(
    val chapters: List<ChapterDto>
)
