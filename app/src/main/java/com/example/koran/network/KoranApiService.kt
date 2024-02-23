package com.example.koran.network

import com.example.koran.model.ListArikel
import retrofit2.http.GET

// Interface KoranApiService mendefinisikan kontrak untuk berkomunikasi dengan layanan API Koran.
interface KoranApiService {
    // Fungsi untuk mengambil daftar artikel terbaru dari API Koran.
    // Anotasi @GET("/cnn/terbaru/") menunjukkan bahwa ini adalah permintaan HTTP GET ke endpoint "/cnn/terbaru/".
    // Fungsi ini bersifat suspending, yang berarti dapat dipanggil dari coroutine tanpa memblokir thread utama.
    @GET("/cnn/terbaru/")
    suspend fun getArticle(): ListArikel
}