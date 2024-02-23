package com.example.koran.data

import com.example.koran.network.KoranApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val koranRepository: KoranRepository
}

class DefaultAppContainer : AppContainer {
    // Ini adalah URL dasar dari API yang akan digunakan.
    private val BASE_URL = "https://api-berita-indonesia.vercel.app"

    // Interceptor ini digunakan untuk logging permintaan dan respons HTTP.
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Inisialisasi Retrofit untuk berkomunikasi dengan API.
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
        .build()

    // Inisialisasi layanan Retrofit yang akan digunakan untuk memanggil API Koran.
    private val retrofitService: KoranApiService by lazy {
        retrofit.create(KoranApiService::class.java)
    }

    // Mengimplementasikan KoranRepository dengan DefaultKoranRepository
    // yang bergantung pada layanan Retrofit untuk komunikasi dengan API Koran.
    override val koranRepository: KoranRepository by lazy {
        DefaultKoranRepository(retrofitService)
    }
}