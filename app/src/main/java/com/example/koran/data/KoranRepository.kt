package com.example.koran.data

import com.example.koran.model.ListArikel
import com.example.koran.network.KoranApiService

// Interface untuk mendefinisikan fungsi getListArticle yang bersifat suspending (dapat dipanggil dari coroutine) dan mengembalikan ListArikel.
interface KoranRepository {
    suspend fun getListArticle(): ListArikel
}

// Kelas implementasi dari KoranRepository yang bergantung pada KoranApiService.
class DefaultKoranRepository(
    private val koranApiService: KoranApiService
) : KoranRepository {
    // Fungsi suspending yang mengambil daftar artikel dari KoranApiService.
    override suspend fun getListArticle(): ListArikel {
        // Mencoba untuk mendapatkan respons dari KoranApiService.
        return try {
            val response = koranApiService.getArticle()
            // Mencetak respons untuk debug atau keperluan logging.
            println("Response: $response")
            // Mengembalikan respons dari KoranApiService.
            response
        } catch (e: Exception) {
            // Menangkap exception jika terjadi kesalahan.
            println("Error: ${e.message}")
            // Melempar kembali exception untuk dikelola oleh pemanggil fungsi.
            throw e
        }
    }
}