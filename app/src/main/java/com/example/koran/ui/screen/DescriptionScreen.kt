package com.example.koran.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.koran.R
import com.example.koran.model.Posts

// Fungsi ListScreen adalah sebuah composable function yang digunakan untuk menampilkan informasi terkait dengan sebuah artikel.

@Composable
fun ListScreen(
    // Properti article adalah sebuah objek dari data class Posts yang menyimpan informasi tentang artikel yang akan ditampilkan.
    article: Posts,
    // Properti onCancelButtonClicked adalah sebuah lambda yang akan dipanggil saat tombol cancel ditekan. Nilai defaultnya adalah sebuah lambda kosong.
    onCancelButtonClicked: () -> Unit = {},
    // Properti onNextButtonClicked adalah sebuah lambda yang akan dipanggil saat tombol next ditekan. Nilai defaultnya adalah sebuah lambda kosong.
    onNextButtonClicked: () -> Unit = {},
    // Properti modifier adalah sebuah objek Modifier yang digunakan untuk menyesuaikan tata letak dan gaya komponen.
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        // Spacer digunakan untuk membuat ruang kosong sebelum konten utama.
        Spacer(modifier = Modifier.height(40.dp))
        // Box digunakan untuk membuat kontainer persegi panjang dengan gaya tertentu.
        Box(
            modifier = Modifier
                .width(180.dp)
                .height(240.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // AsyncImage adalah komponen untuk menampilkan gambar secara asinkron.
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(article.thumbnail) // Mengambil URL gambar dari properti thumbnail artikel.
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.loading_img), // Menampilkan gambar placeholder jika terjadi kesalahan.
                placeholder = painterResource(id = R.drawable.loading_img) // Menampilkan gambar placeholder selama gambar sedang dimuat.
            )
        }

        // Menambahkan ruang kosong di antara elemen-elemen.
        Spacer(modifier = Modifier.height(20.dp))

        // Menampilkan judul artikel.
        Text(
            text = article.title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black.copy(alpha = 0.8f),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                horizontal = 30.dp,
                vertical = 6.dp
            ),
        )

        // Menampilkan tanggal publikasi artikel.
        Text(
            text = "Date : " + article.pubDate,
            style = MaterialTheme.typography.labelSmall.copy(fontStyle = FontStyle.Italic),
            color = Color.Black.copy(alpha = 0.8f),
            modifier = Modifier.padding(
                horizontal = 30.dp,
                vertical = 4.dp
            ),
        )

        // Menampilkan deskripsi artikel.
        Text(
            text = buildAnnotatedString {
                withStyle(style = ParagraphStyle(textAlign = TextAlign.Justify)) {
                    append("   " + article.description)
                }
            },
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black.copy(alpha = 0.8f),
            modifier = Modifier.padding(
                horizontal = 30.dp,
                vertical = 4.dp,
            )
        )

        // Menampilkan referensi tautan artikel.
        Text(
            text = "Referensi : " + article.link,
            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
            color = Color.Black.copy(alpha = 0.8f),
            modifier = Modifier.padding(
                horizontal = 30.dp,
                vertical = 4.dp
            ),
        )
    }
}