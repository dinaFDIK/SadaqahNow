package com.example.sadaqahnow.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.* // Mengimpor mutableStateOf, remember, dll
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// --- DAFTAR IMPORT FITUR ---
import com.example.sadaqahnow.features.auth.OnboardingScreen
import com.example.sadaqahnow.features.auth.LoginScreen
import com.example.sadaqahnow.features.auth.RegisterScreen
import com.example.sadaqahnow.features.home.SadaqahNowHomeScreen
import com.example.sadaqahnow.features.home.DonationSuccessScreen
import com.example.sadaqahnow.features.home.DonationDetailScreen
import com.example.sadaqahnow.features.home.DonationPaymentScreen
import com.example.sadaqahnow.features.home.DonationCategoryScreen
import com.example.sadaqahnow.features.home.DonationCampaign
import com.example.sadaqahnow.features.profile.ProfileScreen
import com.example.sadaqahnow.features.settings.SettingsScreen
import com.example.sadaqahnow.features.settings.PaymentSourceScreen
import com.example.sadaqahnow.navigation.SadaqahNowBottomBar

// Data class untuk riwayat donasi
data class DonationRecord(val title: String, val amount: String, val date: String)

object Screen {
    const val Onboarding = "onboarding"
    const val Login = "login"
    const val Register = "register"
    const val Home = "home"
    const val Success = "success/{amount}"
    const val Profile = "profile"
    const val Settings = "settings"
    const val PaymentSource = "payment_source"
}

@Composable
fun SadaqahNowNavGraph(
    navController: NavHostController = rememberNavController()
) {
    // --- STATE GLOBAL AGAR DATA SINKRON ---
    var streakCount by remember { mutableIntStateOf(7) }
    var totalDonation by remember { mutableLongStateOf(210000L) }
    var donationHistoryList by remember { mutableStateOf(listOf<DonationRecord>()) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in listOf(Screen.Home, Screen.Profile, Screen.Settings)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                SadaqahNowBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Onboarding,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Onboarding) {
                OnboardingScreen(onGetStartedClick = { navController.navigate(Screen.Login) })
            }

            composable(Screen.Login) {
                LoginScreen(
                    onLoginClick = { _, _ ->
                        navController.navigate(Screen.Home) {
                            popUpTo(Screen.Onboarding) { inclusive = true }
                        }
                    },
                    onGoogleLoginClick = { },
                    onRegisterClick = { navController.navigate(Screen.Register) }
                )
            }

            composable(Screen.Register) {
                RegisterScreen(
                    onRegisterClick = { _, _, _ -> navController.navigate(Screen.Login) },
                    onLoginClick = { navController.popBackStack() }
                )
            }

            composable(Screen.Home) {
                SadaqahNowHomeScreen(
                    streakCount = streakCount,
                    onDonateClick = { /* Dihandle oleh tombol lingkaran di Home */ },
                    onProfileClick = { navController.navigate(Screen.Profile) },
                    onCategoryClick = { categoryName ->
                        navController.navigate("donation_category/$categoryName")
                    }
                )
            }

            composable(
                route = Screen.Success,
                arguments = listOf(navArgument("amount") { type = NavType.LongType })
            ) { backStackEntry ->
                val amount = backStackEntry.arguments?.getLong("amount") ?: 1000L
                DonationSuccessScreen(
                    amount = amount,
                    onDoneClick = { navController.popBackStack(Screen.Home, false) }
                )
            }

            composable(Screen.Profile) {
                ProfileScreen(
                    streakCount = streakCount,
                    totalDonation = totalDonation,
                    onBackClick = { navController.popBackStack() },
                    onViewAllHistoryClick = { },
                    onSettingsClick = { navController.navigate(Screen.Settings) }
                )
            }

            composable(Screen.Settings) {
                SettingsScreen(
                    onBackClick = { navController.popBackStack() },
                    onLogoutClick = {
                        navController.navigate(Screen.Login) { popUpTo(0) }
                    },
                    onChangePaymentClick = { navController.navigate(Screen.PaymentSource) }
                )
            }

            composable(Screen.PaymentSource) {
                PaymentSourceScreen(
                    onBackClick = { navController.popBackStack() },
                    onSourceSelected = { sourceName ->
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selected_source", sourceName)
                        navController.popBackStack()
                    }
                )
            }

            composable("donation_category/{categoryName}") { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                DonationCategoryScreen(
                    categoryName = categoryName,
                    onBackClick = { navController.popBackStack() },
                    onCampaignClick = { campaign ->
                        navController.navigate("donation_detail/${campaign.title}")
                    }
                )
            }

            composable("donation_detail/{campaignTitle}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("campaignTitle") ?: ""
                val selectedCampaign = getCampaignByTitle(title)

                DonationDetailScreen(
                    campaign = selectedCampaign,
                    onBackClick = { navController.popBackStack() },
                    onDonateNowClick = {
                        navController.navigate("donation_payment/${selectedCampaign.title}")
                    }
                )
            }

            composable("donation_payment/{campaignTitle}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("campaignTitle") ?: ""
                val campaign = getCampaignByTitle(title)

                val selectedSource by backStackEntry.savedStateHandle
                    .getStateFlow("selected_source", "SadaqahNow Wallet")
                    .collectAsState()

                DonationPaymentScreen(
                    campaign = campaign,
                    selectedPaymentSource = selectedSource,
                    onBackClick = { navController.popBackStack() },
                    onSelectPaymentSourceClick = {
                        navController.navigate(Screen.PaymentSource)
                    },
                    onConfirmClick = { amount ->
                        // --- PROSES LOGIKA DONASI BERHASIL ---
                        streakCount++
                        totalDonation += amount

                        navController.navigate("success/$amount") {
                            popUpTo(Screen.Home) { saveState = true }
                        }
                    }
                )
            }
        }
    }
}

fun getCampaignByTitle(title: String): DonationCampaign {
    val allCampaigns = listOf(
        // --- BENCANA ---
        DonationCampaign(
            "Krisis Bantuan!!! Bantu Korban Terdampak Banjir Aceh Tamiang",
            "BAZNAS Hub", "Rp4M", 182, 0.45f, com.example.sadaqahnow.R.drawable.sumatera,
            "17 kabupaten/Kota di Provinsi Aceh dilanda bencana banjir hingga tanah longsor (Pusdalops BNPB).\n" +
                    "Akibat bencana ini lebih datri 96 warga meninggal dunia, sementara sejumlah lainnya masih dalam pencarian. Akses jalan terputus, rumah-rumah rusak berat, dan berbagai fasilitas umum juga mengalami kerusakan. Ribuan warga terdampak dan terpaksa mengungsi\n" +
                    "Mari kita bantu ringankan beban saudara-saudara kita yang terdampak bencana di Aceh\n."
        ),
        DonationCampaign(
            "Bantu Korban Banjir Kalimantan Selatan",
            "Rumah Zakat Kalimantan Selatan", "Rp100jt", 89, 0.75f, com.example.sadaqahnow.R.drawable.kalsel,
            "Kabupaten Banjar, Balangan, Hulu Sungai Selatan, dan sekitarnya kini terendam banjir. Jalan terputus, aktivitas lumpuh, dan ribuan keluarga harus bertahan di tengah kondisi serba terbatas.\n" +
                    "Di balik angka-angka ini, ada anak-anak yang kedinginan, lansia yang kesulitan bergerak, ibu hamil yang membutuhkan layanan kesehatan, serta keluarga yang kehilangan tempat berlindung. Kebutuhan dasar kini menjadi hal paling mendesak.\n" +
                    "Namun, upaya ini tidak dapat berjalan sendiri.\n" +
                    "Mari hadirkan harapan untuk Kalimantan Selatan.\n" +
                    "Setiap donasi yang Orangbaik titipkan hari ini akan berubah menjadi makanan hangat, air bersih, layanan kesehatan, dan tempat beristirahat bagi saudara-saudara kita yang terdampak banjir.\n" +
                    "Bantu sekarang. Ringankan beban mereka hari ini.\n."
        ),
        DonationCampaign(
            "Bersatu, Bantu Sumatera Pulih",
            "Rumah Wakaf", "Rp750jt", 89, 0.75f, com.example.sadaqahnow.R.drawable.aceh_tamiang,
            "Lebih dari 13 kabupaten/Kota di Sumatera Utara dilanda bencana banjir dan longsor sejak akhir November 2025\n" +
                    "Akibat bencana ini menyebabkan ratusan warga meninggal dunia, sementara sejumlah lainnya masih dalam pencarian. Akses jalan terputus, rumah-rumah terendam, dan berbagai fasilitas umum mengalami kerusakan. Ribuan warga terdampak dan mengungsi.\n" +
                    "Mari kita bantu ringankan beban saudara-saudara kita yang terdampak bencana di Sumatera Utara\n"
        ),

        // --- KEMANUSIAAN ---
        DonationCampaign(
            "Sisihkan Rezeki Bagi Makanan Gratis Untuk Anak Panti",
            "Salam Setara Amanah Nusantara", "Rp250jt", 15, 0.90f, com.example.sadaqahnow.R.drawable.panti,
            "Saat kita dapat makan dengan berbagai lauk pauk setiap hari, ratusan saudara kita di luar sana harus menahan lapar karena tak punya uang.\n" +
                    "Bukan memikirkan menu makanan apa yang akan dipilih, ratusan anak-anak santri dhuafa, anak-anak yatim dan kaum dhuafa lebih sering mempertanyakan “bisakah aku makan esok hari?”\n" +
                    "Melihat kondisi tersebut, kami berinisiatif untuk membuat gerakan berbagi makanan dan sembako gratis melalui program Gerakan Sedekah Makanan.\n" +
                    "Namun untuk mewujudkan itu semua, diperlukan ribuan tangan orang baik untuk bergabung dan ikut dalam misi kebaikan ini.\n"
        ),
        DonationCampaign(
            "Beasiswa Kelana Cerdas: Wujudkan Mimpi Masa Depan",
            "Kelana Foundation", "Rp350jt", 45, 0.60f, com.example.sadaqahnow.R.drawable.kelana,
            "Dengan ikut berdonasi hari ini, kamu sedang membukakan pintu masa depan bagi mereka. Satu tas sekolah bisa membawa harapan. Satu pasang sepatu bisa mempercepat langkah. Dan satu beasiswa, bisa mengubah hidup seluruh keluarga.\n" +
                    "Mari jadi bagian dari perjalanan ini.\n" +
                    "Mari jadi alasan mereka tetap sekolah.\n"
        ),
        DonationCampaign(
            "Tolong Pasien Tidur Terbengkalai di Rumah Sakit",
            "Sekawan Foundation", "Rp100jt", 45, 0.60f, com.example.sadaqahnow.R.drawable.sosial,
            "Rumah Singgah Sekawan ini merupakan sebagai tempat penginapan sementara untuk para pasien yang bersifat non-formal, dimana pasien menginap untuk sementara waktu sebelum selesainya rawat jalan yang dilakuakan oleh pasien. \n" +
                    "Saat ini Rumah singgah membutuhkan alat-alat medis dan keperluan inap lainnya untuk para pasien yang tinggal sementara di rumah singgah. yuk donasikan hartamu untuk membantu para pasien dari pelosok daerah!\n" +
                    "Berikan donasimu terbaikmu untuk perjuangan mereka.\n"
        ),

        // --- RUMAH IBADAH ---
        DonationCampaign(
            "Wakaf Quran Pelosok, Satu Ayat Seribu Kebaikan",
            "Yayasan Bangun Umat Nurani", "Rp10jt", 60, 0.30f, com.example.sadaqahnow.R.drawable.quran,
            "Melalui gerakan “Wakaf Al-Qur’an Pelosok: Satu Ayat, Seribu Kebaikan”, kita bisa menghadirkan Al-Qur’an baru untuk mereka yang haus akan ilmu Allah. Setiap mushaf yang kita wakafkan bukan sekadar buku, tapi sumber cahaya yang menuntun langkah dan akhlak generasi muda di pelosok negeri.\n" +
                    "Satu mushaf mungkin terlihat sederhana bagi kita, tapi bagi santri di pelosok, itu adalah harta yang sangat berharga. Melalui wakaf Al-Qur’an, kita bisa membuka jalan bagi mereka untuk terus belajar, memahami, dan mengamalkan kalam Allah.\n" +
                    "Mari bersama-sama berwakaf Al-Qur’an hari ini. Karena dari satu ayat, kita bisa menebar seribu kebaikan yang terus mengalir tanpa henti.\n"
        ),
        DonationCampaign(
            "Bantu Gereja GEPEMBRI Balai Sebut",
            "Yayasan Cahaya Pelita Timur", "Rp5jt", 60, 0.30f, com.example.sadaqahnow.R.drawable.gereja,
            "Jemaat Gereja GEPEMBRI Balai Sebut membutuhkan dana dengan total Rp 12.000.000 Nantinya, dana tersebut akan digunakan untuk membeli alat musik keyboard gereja sebesar Rp 8.000.000 dan untuk membeli 1 Set sound sistem sebesar Rp. 4.000.000\n" +
                    "Oleh karena itu,Bapak Gembala Bersama jemaat membutuhkan dukungan dari Bapak / saudara terkasih, Besar harapan dengan bantuan bapak Ibu sobat orang baik Gereja GEPEMBRI Balai Sebut bisa memiliki alat musik keyboard dan sound sistem sehingga meningkatkan kualitas pelayanan Gereja dengan demikian ibadah menjadi semakin \"hidup\" dan kemuliaan Tuhan dinyatakan melalui irama musik penuh dengan hadirat Tuhan\n"
        ),
        DonationCampaign(
            "Sedekah Jariyah Bangun Masjid Pelosok",
            "Harapan Amal Mulia", "Rp50jt", 120, 0.50f, com.example.sadaqahnow.R.drawable.masjid,
            "Di pelosok Cianjur Selatan, terdapat masjid kecil bernama Masjid Al-Iqro. Bangunan semi permanen ini kondisinya sudah sangat memprihatinkan. Kayu-kayu penyangga mulai lapuk, bilik bambu berlubang dan menghitam karena rembesan air, sementara atapnya bocor di sana-sini. Setiap kali hujan turun, rasa waswas menyelimuti jamaah yang sedang beribadah di dalamnya.\n" +
                    "mari kita bersama-sama membantu memperbaiki dan membangun masjid di pelosok negeri. Sesuai dengan hadits Rasulullah SAW “Barang siapa membangun masjid karena Allah, maka Allah bangunkan untuknya rumah di surga.” (HR. Bukhari dan Muslim).\n"
        ),

        // --- LINGKUNGAN ---
        DonationCampaign(
            "Dukung Penjaga Hutan Adat Papua",
            "WWF-Indonesia", "Rp100jt", 20, 0.70f, com.example.sadaqahnow.R.drawable.papua,
            "Kini, saatnya kita ikut ambil bagian. Dukungan kecil Anda akan membantu: \n" +
                    "•\tPelatihan masyarakat adat agar usaha mereka berkembang  \n" +
                    "•\tPenanaman dan monitoring ribuan pohon sagu, bambu, dan mangrove \n" +
                    "•\tPemasaran produk lokal seperti kopi, sagu, dan kerajinan kulit kayu \n" +
                    "Setiap kontribusi kita adalah investasi untuk hutan Papua dan generasi penerusnya. Mari kita pastikan burung cenderawasih tetap bernyanyi, hutan tetap hijau, dan masyarakat adat hidup sejahtera di tanah mereka. \n" +
                    "Berdonasi hari ini – karena sekecil apa pun dukungan Anda, dampaknya besar bagi hutan dan kehidupan di Papua. \n"
        ),
        DonationCampaign(
            "Bersihkan Sampah, Hijaukan Bumi",
            "Program Sahabat Dhuafa Banyuwangi", "Rp5jt", 20, 0.70f, com.example.sadaqahnow.R.drawable.sampah,
            "Setiap donasi yang kamu berikan akan sangat berati untuk menjaga lingkungan ini dari sampah. Karena setiap donasi akan dipergunakan untuk :\n" +
                    "Pembelian alat kebersihan, Pengadaan tempat sampah permanen di area publik, Kegiatan bersih-bersih lingkungan bersama relawan dan warga, Edukasi pengelolaan sampah & kampanye peduli lingkungan\n" +
                    "Mari wujudkan lingkungan yang bersih, sehat, dan nyaman untuk generasi mendatang.\n" +
                    "“Satu langkah kecilmu hari ini akan menjadi napas segar bagi bumi esok hari.”\n"
        ),
        DonationCampaign(
            "Sedekah Pohon, Lahan Kritis Terdampak Bencana",
            "Rengganis Indonesia Foundation", "Rp70jt", 7, 0.95f, com.example.sadaqahnow.R.drawable.pohon,
            "Program sedekah bibit pohon ini memastikan hutan kembali berdiri, sumber air pulih, dan bumi bisa bernapas lagi. Ini bukan sekadar aksi menanam, tetapi langkah menjaga masa depan bersama.\n" +
                    "Ini bukan hanya tentang menanam pohon. Ini tentang memulihkan tanah luka, mengembalikan harapan, dan menguatkan Semeru untuk masa depan.\n" +
                    "Satu pohon hari ini, satu kehidupan esok hari, Indonesia hijau kembali dimulai dari kamu.\n"
        ),

        // --- HEWAN ---
        DonationCampaign(
            "Kasih Kucing Liar Terlantar Makan Layak",
            "Animal Hope", "Rp2jt", 30, 0.20f, com.example.sadaqahnow.R.drawable.kucing,
            "Saya membutuhkan bantuan untuk membeli pakan kering dan basah, dengan kebutuhan dana sebesar Rp1.250.000.\n" +
                    "Rp1.150.000 Dana ini akan digunakan untuk pembelian pakan kucing\n" +
                    "Rp100.000 Untuk biaya operasional\n" +
                    "Sekecil apa pun bantuan Anda sangat berarti bagi kehidupan mereka.\n"
        ),
        DonationCampaign(
            "Patungan biaya steril kucing dan anjing jalanan",
            "Animal Hope", "Rp1jt", 30, 0.20f, com.example.sadaqahnow.R.drawable.combo,
            "untuk melunasi biaya steril keseluruhan di klinik sebesar Rp 2.700.000.\n" +
                    "Oleh karena itu, saya butuh bantuan teman-teman untuk patungan biaya steril mereka. Supaya saya tetap bisa membawa hewan yang membutuhkan pertolongan ke klinik hewan. Besar harapan saya dapat tetap menolong hewan-hewan yang membutuhkan.\n"
        ),
        DonationCampaign(
            "Tumor Kanker Membesar,Cecel Butuh Operasi Segera",
            "Animal Hope", "Rp2jt", 30, 0.20f, com.example.sadaqahnow.R.drawable.anjing,
            "Saya membuka penggalangan dana ini dengan harapan Cecel bisa menjalani operasi tepat waktu, bebas dari rasa sakit, dan mendapatkan kesempatan hidup yang lebih layak di usia senjanya.\n" +
                    "Setiap donasi, sekecil apa pun, sangat berarti dan dapat memberi Cecel kesempatan untuk hidup lebih panjang.\n"
        )
    )

    return allCampaigns.find { it.title == title } ?: allCampaigns[0]
}