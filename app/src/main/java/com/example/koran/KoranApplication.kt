package com.example.koran

import android.app.Application
import com.example.koran.data.AppContainer
import com.example.koran.data.DefaultAppContainer

// Kelas KoranApplication adalah kelas Application yang merupakan titik masuk utama untuk aplikasi Android.

class KoranApplication : Application() {

    // Variabel container digunakan untuk menyimpan instance dari AppContainer yang digunakan untuk mengelola dependensi.
    lateinit var container: AppContainer

    // Fungsi onCreate() dipanggil saat aplikasi dibuat dan diinisialisasi.
    override fun onCreate() {
        super.onCreate()
        // Menginisialisasi container dengan instance dari DefaultAppContainer.
        container = DefaultAppContainer()
    }
}
