package com.example.koran.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.koran.KoranApplication
import com.example.koran.data.KoranRepository
import com.example.koran.model.ListArikel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Sebuah sealed interface yang merepresentasikan berbagai status UI untuk daftar artikel.
sealed interface ListArticleUiState1 {
    // Status UI ketika berhasil mendapatkan daftar artikel.
    data class Success(val listArikel: ListArikel) : ListArticleUiState1
    // Status UI ketika terjadi kesalahan.
    object Error : ListArticleUiState1
    // Status UI ketika sedang dalam proses pengambilan daftar artikel.
    object Loading : ListArticleUiState1
}

// Kelas ViewModel yang bertanggung jawab untuk berinteraksi dengan repositori dan mengelola status UI untuk daftar artikel.
class ListArticleViewModel1(private val koranRepository: KoranRepository) : ViewModel() {
    // Mutable state yang menyimpan status UI untuk daftar artikel.
    var listArticleUiState1: ListArticleUiState1 by mutableStateOf(ListArticleUiState1.Loading)
        private set

    // Blok inisialisasi, dieksekusi saat kelas dibuat.
    init {
        // Memanggil fungsi getListArticle() untuk menginisialisasi daftar artikel.
        getListArticle()
    }

    // Fungsi untuk mendapatkan daftar artikel dari repositori.
    fun getListArticle() {
        viewModelScope.launch {
            // Mengubah status UI menjadi Loading saat proses pengambilan dimulai.
            listArticleUiState1 = ListArticleUiState1.Loading
            // Mengambil daftar artikel dari repositori dan mengubah status UI sesuai hasilnya.
            listArticleUiState1 = try {
                ListArticleUiState1.Success(koranRepository.getListArticle())
            } catch (e: IOException) {
                ListArticleUiState1.Error
            } catch (e: HttpException) {
                ListArticleUiState1.Error
            }
        }
    }

    // Companion object untuk menyediakan factory ViewModelProvider.
    companion object {
        // Factory ViewModelProvider.Factory yang menyediakan ViewModel dari kelas ListArticleViewModel1.
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Mendapatkan instance aplikasi dari ViewModelProvider.Factory dan mengakses repositori dari kontainer aplikasi.
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KoranApplication)
                val koranRepository = application.container.koranRepository
                // Membuat dan menginisialisasi instance ListArticleViewModel1 dengan repositori yang diberikan.
                ListArticleViewModel1(koranRepository = koranRepository)
            }
        }
    }
}