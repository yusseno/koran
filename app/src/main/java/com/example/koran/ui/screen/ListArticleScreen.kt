package com.example.koran.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.koran.R
import com.example.koran.model.Data
import com.example.koran.model.ListArikel
import com.example.koran.model.Posts
import com.example.koran.ui.theme.KoranTheme

// Fungsi HomeScreen adalah komponen untuk menampilkan layar utama aplikasi yang menampilkan daftar kategori artikel.

@Composable
fun HomeScreen(
    // listArticleUiState1 adalah status UI yang berisi informasi tentang daftar artikel.
    listArticleUiState1: ListArticleUiState1,
    // retryAction adalah lambda yang dipanggil saat tombol "Coba Lagi" ditekan.
    retryAction: () -> Unit,
    // onNextButtonClicked adalah lambda yang dipanggil saat sebuah artikel dipilih.
    onNextButtonClicked: (Posts) -> Unit,
    // Modifier adalah parameter yang digunakan untuk menyesuaikan tata letak dan gaya komponen.
    modifier: Modifier = Modifier,
    // PaddingValues adalah parameter yang digunakan untuk menentukan padding untuk konten komponen.
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    // Memilih tindakan berdasarkan status UI.
    when (listArticleUiState1) {
        // Jika status adalah Loading, tampilkan layar LoadingScreen.
        is ListArticleUiState1.Loading -> LoadingScreen(modifier = modifier.size(200.dp))
        // Jika status adalah Success, tampilkan daftar kategori artikel.
        is ListArticleUiState1.Success -> {
            val data = listArticleUiState1.listArikel.data
            CategoryListScreen(
                data = data,
                modifier = modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    ),
                onNextButtonClicked = onNextButtonClicked,
                contentPadding = contentPadding
            )
        }
        // Jika status adalah selain Loading dan Success, tampilkan layar ErrorScreen.
        else -> ErrorScreen(retryAction = retryAction, modifier = modifier)
    }
}

// Fungsi LoadingScreen adalah komponen untuk menampilkan layar loading.
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    // Menampilkan gambar loading.
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "Loading",
        modifier = modifier
    )
}

// Fungsi ErrorScreen adalah komponen untuk menampilkan layar kesalahan.
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    // Menampilkan pesan kesalahan dan tombol "Coba Lagi".
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gagal Memuat Data")
        Button(onClick = retryAction) {
            Text("Coba Lagi")
        }
    }
}

// Fungsi CategoryCard adalah komponen untuk menampilkan kartu kategori artikel.
@Composable
fun CategoryCard(data: List<Posts>, onNextButtonClicked: (Posts) -> Unit, modifier: Modifier = Modifier) {
    // Menampilkan kartu untuk setiap artikel dalam data.
    data.forEach { article ->
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {onNextButtonClicked(article)}, // Menambahkan aksi ketika kartu diklik.
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CategoryListRow(article)
            }
        }
    }
}

// Fungsi CategoryListRow adalah komponen untuk menampilkan baris daftar kategori artikel.
@Composable
fun CategoryListRow(data: Posts) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // Menampilkan gambar artikel.
            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(data.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.loading_img),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            // Menampilkan judul artikel.
            Text(
                text = data.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(6.dp))
            // Menampilkan tanggal publikasi artikel.
            Text(
                text = data.pubDate,
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                color = Color.Gray,
            )
        }
    }
}

// Fungsi CategoryListScreen adalah komponen untuk menampilkan layar daftar kategori artikel.
@Composable
fun CategoryListScreen(
    // data berisi informasi tentang daftar artikel.
    data: Data,
    // onNextButtonClicked adalah lambda yang dipanggil saat sebuah artikel dipilih.
    onNextButtonClicked: (Posts) -> Unit,
    // Modifier adalah parameter yang digunakan untuk menyesuaikan tata letak dan gaya komponen.
    modifier: Modifier = Modifier,
    // contentPadding adalah parameter yang digunakan untuk menentukan padding untuk konten komponen.
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
// LazyColumn adalah komponen untuk menampilkan daftar
    // daftar yang bisa digulir.
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Menampilkan setiap item dalam daftar artikel.
        itemsIndexed(data.posts) { index, post ->
            // Menampilkan kartu kategori untuk setiap artikel.
            CategoryCard(data = data.posts, onNextButtonClicked = onNextButtonClicked, modifier = Modifier.fillMaxWidth())
        }
    }
}

// Fungsi LoadingScreenPreview adalah komponen untuk menampilkan tampilan pratinjau layar loading.
@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    // Tema yang digunakan adalah tema aplikasi Koran.
    KoranTheme() {
        // Mock data yang digunakan untuk tampilan pratinjau.
        val mockData = ListArikel(
            success = true,
            message = "Data loaded successfully",
            data = Data(
                link = "link_data_1",
                description = "Description Data 1",
                title = "Title Data 1",
                image = "image_data_1",
                posts = listOf(
                    Posts(
                        link = "link_post_1",
                        title = "Title Post 1",
                        pubDate = "PubDate Post 1",
                        description = "Description Post 1",
                        thumbnail = "thumbnail_post_1"
                    ),
                    Posts(
                        link = "link_post_2",
                        title = "Title Post 2",
                        pubDate = "PubDate Post 2",
                        description = "Description Post 2",
                        thumbnail = "thumbnail_post_2"
                    )
                )
            )
        )
        // Menampilkan layar daftar kategori artikel dengan menggunakan mock data.
        CategoryListScreen(data = mockData.data, onNextButtonClicked = {}, Modifier.fillMaxSize())
    }
}
