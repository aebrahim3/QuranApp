package com.bcit.quranapp.network

import com.bcit.quranapp.dataModel.ChaptersResponse
import retrofit2.http.GET

interface QuranApi {
    @GET("chapters")
    suspend fun getSurahs(): ChaptersResponse

}