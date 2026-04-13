package com.bcit.quranapp.network

import com.bcit.quranapp.dataModel.ChaptersResponse
import com.bcit.quranapp.dataModel.Tafsir
import com.bcit.quranapp.dataModel.VersesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuranApi {
    @GET("chapters")
    suspend fun getSurahs(): ChaptersResponse

    /**
     * The endpoint needs to include chapter number, since
     * we are going by chapter. Without it, the JSON sender
     * won't know which chapter we want. The @Path is crucial
     * to this endpoint, and any endpoints where we have to
     * specify something, so in this case, chapter.
     * The @Query is not always necessary to the endpoint, but
     * it customizes the response by adding additional data or
     * specifying preferences, such as which translation to include
     * or how many results to return per page. Without @Query,
     * the API falls back to its default values.
     *
     *  Now when a QuranApi instance calls getAyahs(...), a GET
     *  request with the @Path and @Query specifications gets sent.
     *
     */
    @GET("verses/by_chapter/{chapter_number}")
    suspend fun getAyahs(
        @Path("chapter_number") chapterNumber: Int,
        @Query("translations") translation: Int = 203,
        @Query("fields") text: String = "text_uthmani",
        @Query("per_page") perPage: Int = 286
    ) : VersesResponse

    @GET("tafsirs/{tafsir_id}/by_ayah/{ayah_key}")
    suspend fun getTafsir(
        @Path("tafsir_id") tafsirId: Int = 168,
        @Path("ayah_key") ayahKey: String

    ) : Tafsir



}