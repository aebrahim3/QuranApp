package com.bcit.quranapp.di

import com.bcit.quranapp.network.QuranApi
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * This instance sends a GET request to the API.
     * It builds a QuranApi instance based on the interface and
     * pre-programs it to tell Retrofit to send a GET request to the API and
     * return a ChaptersResponse object.
     * It hands over the JSON data to GSON,
     * which converts it to a ChaptersResponse object.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.quran.com/api/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * This instance makes it easier to getSurahs() because we just
     * have to tack on it getSurahs(). It tells Retrofit to send a GET
     * request to the API and return a ChaptersResponse object.
     */
    @Provides
    @Singleton
    fun provideQuranApi(retrofit: Retrofit): QuranApi = retrofit.create(QuranApi::class.java)
}

