package com.example.koran.ui

import androidx.lifecycle.ViewModel
import com.example.koran.data.KoranUiState
import com.example.koran.model.Posts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Kelas CategoryViewModel adalah bagian dari arsitektur MVVM (Model-View-ViewModel) yang bertanggung jawab untuk mengelola data terkait kategori artikel.

class CategoryViewModel() : ViewModel() {

    // MutableStateFlow yang menyimpan status UI terkait kategori artikel.
    private val _uiState = MutableStateFlow(KoranUiState(article = Posts("", "", "", "", "")))

    // StateFlow yang mengubah status UI menjadi immutable.
    val uiState: StateFlow<KoranUiState> = _uiState.asStateFlow()

    // Fungsi untuk mengatur id artikel yang dipilih.
    fun setIdBook(kt: Posts) {
        // Mengupdate nilai _uiState dengan id artikel yang dipilih.
        _uiState.update { currentState ->
            currentState.copy(
                article = kt,
            )
        }
    }

    // Fungsi untuk mereset status UI ke nilai awal.
    fun resetState() {
        // Mengubah nilai _uiState kembali ke nilai awal dengan id artikel yang kosong.
        _uiState.value = KoranUiState(article = Posts("", "", "", "", ""))
    }
}