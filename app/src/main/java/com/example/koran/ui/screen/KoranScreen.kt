package com.example.koran.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.koran.R
import com.example.koran.ui.CategoryViewModel

// Enum class KoranScreen mendefinisikan jenis-jenis layar yang mungkin ada dalam aplikasi Koran.
enum class KoranScreen(@StringRes val title: Int) {
    // Masing-masing nilai enum memiliki properti @StringRes untuk menyimpan referensi string yang digunakan sebagai judul layar.
    list(title = R.string.app_name), // Layar daftar artikel.
    Description(title = R.string.description), // Layar deskripsi artikel.
}

// Fungsi KoranAppBar adalah komponen untuk menampilkan app bar di aplikasi Koran.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KoranAppBar(
    // Parameter currentScreen menunjukkan layar saat ini yang digunakan.
    currentScreen: KoranScreen,
    // Parameter canNavigateBack menunjukkan apakah pengguna dapat kembali ke layar sebelumnya.
    canNavigateBack: Boolean,
    // Parameter navigateUp adalah lambda yang dipanggil saat tombol navigasi "Up" ditekan.
    navigateUp: () -> Unit,
    // Modifier adalah parameter yang digunakan untuk menyesuaikan tata letak dan gaya komponen.
    modifier: Modifier = Modifier
) {
    // TopAppBar adalah komponen untuk menampilkan app bar di bagian atas layar.
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) }, // Menampilkan judul app bar sesuai dengan layar saat ini.
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ), // Menentukan warna app bar.
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                // Menampilkan tombol navigasi "Back" jika pengguna dapat kembali.
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

// Fungsi KoranApp adalah komponen utama dari aplikasi Koran.
@Composable
fun KoranApp(
    // ViewModel untuk mengelola data kategori artikel.
    categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    // NavController untuk mengelola navigasi antar layar.
    navController: NavHostController = rememberNavController()
) {
    // Mendapatkan informasi dari back stack entry untuk menentukan layar saat ini.
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = KoranScreen.valueOf(
        backStackEntry?.destination?.route ?: KoranScreen.list.name
    )

    // Scaffold adalah komponen untuk mengatur layout utama aplikasi.
    Scaffold(
        topBar = {
            // Menampilkan app bar di bagian atas aplikasi.
            KoranAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        // Mengambil UI state dari ViewModel.
        val uiState by categoryViewModel.uiState.collectAsState()

        // NavHost adalah komponen untuk mengatur navigasi antar layar.
        NavHost(
            navController = navController,
            startDestination = KoranScreen.list.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Menampilkan layar daftar artikel.
            composable(route = KoranScreen.list.name) {
                val categoryViewModel1: ListArticleViewModel1 =
                    viewModel(factory = ListArticleViewModel1.Factory)
                HomeScreen(
                    listArticleUiState1 = categoryViewModel1.listArticleUiState1,
                    retryAction = categoryViewModel1::getListArticle,
                    onNextButtonClicked = {
                        categoryViewModel.setIdBook(it)
                        navController.navigate(KoranScreen.Description.name)
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
            // Menampilkan layar deskripsi artikel.
            composable(route = KoranScreen.Description.name) {
                ListScreen(
                    article = uiState.article,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(categoryViewModel, navController)
                    },
                )
            }
        }
    }
}

// Fungsi cancelOrderAndNavigateToStart digunakan untuk membatalkan pesanan dan kembali ke layar daftar artikel.
private fun cancelOrderAndNavigateToStart(
    viewModel: CategoryViewModel,
    navController: NavHostController
) {
    viewModel.resetState() // Mereset status ViewModel.
    navController.popBackStack(KoranScreen.list.name, inclusive = false) // Kembali ke layar daftar artikel.
}
