package com.example.koran.data

import com.example.koran.model.Posts

// Data class yang merepresentasikan status UI terkait dengan artikel Koran.
data class KoranUiState(
    // Properti article menyimpan informasi tentang posting artikel.
    val article: Posts,
)
