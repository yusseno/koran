package com.example.koran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.koran.ui.screen.KoranApp
import com.example.koran.ui.theme.KoranTheme

// Kelas MainActivity adalah kelas yang mewakili aktivitas utama dalam aplikasi Android.

class MainActivity : ComponentActivity() {

    // Fungsi onCreate() dipanggil saat aktivitas dibuat dan diinisialisasi.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengatur konten tampilan activity menggunakan Compose.
        setContent {
            // Menerapkan tema Koran untuk seluruh tampilan dalam activity.
            KoranTheme {
                // Menempatkan tampilan dalam Surface yang mengisi seluruh ukuran activity.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Menampilkan aplikasi Koran di dalam Surface.
                    KoranApp()
                }
            }
        }
    }
}
