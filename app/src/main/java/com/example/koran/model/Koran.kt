package com.example.koran.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Data class ListArikel merepresentasikan respons yang berisi daftar artikel.
@Serializable
data class ListArikel(
    // Properti success menunjukkan keberhasilan atau kegagalan permintaan.
    @SerialName("success") val success: Boolean,
    // Properti message menyimpan pesan yang berkaitan dengan respons, biasanya digunakan untuk pesan kesalahan jika success adalah false.
    @SerialName("message") val message: String?,
    // Properti data berisi data sebenarnya yang diambil dari respons, termasuk daftar artikel.
    @SerialName("data") val data: Data
)

// Data class Data berisi informasi terkait dengan daftar artikel.
@Serializable
data class Data(
    // Properti link menyimpan tautan terkait dengan artikel.
    @SerialName("link") val link: String,
    // Properti description menyimpan deskripsi singkat tentang artikel.
    @SerialName("description") val description: String,
    // Properti title menyimpan judul dari artikel.
    @SerialName("title") val title: String,
    // Properti image menyimpan URL gambar terkait dengan artikel.
    @SerialName("image") val image: String,
    // Properti posts berisi daftar posting artikel yang terkait.
    @SerialName("posts") val posts: List<Posts>
)

// Data class Posts merepresentasikan sebuah postingan artikel.
@Serializable
data class Posts(
    // Properti link menyimpan tautan ke artikel tersebut.
    @SerialName("link") val link: String,
    // Properti title menyimpan judul dari artikel tersebut.
    @SerialName("title") val title: String,
    // Properti pubDate menyimpan tanggal publikasi artikel.
    @SerialName("pubDate") val pubDate: String,
    // Properti description menyimpan deskripsi singkat tentang artikel tersebut.
    @SerialName("description") val description: String,
    // Properti thumbnail menyimpan URL thumbnail gambar yang terkait dengan artikel tersebut.
    @SerialName("thumbnail") val thumbnail: String
)