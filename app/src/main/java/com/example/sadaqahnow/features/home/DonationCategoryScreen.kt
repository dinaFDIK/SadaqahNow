package com.example.sadaqahnow.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sadaqahnow.R

/**
 * MODEL DATA (Pastikan urutan ini sama dengan pemanggilan di bawah)
 */
data class DonationCampaign(
    val title: String,
    val organization: String,
    val collectedAmount: String,
    val remainingDays: Int,
    val progress: Float,
    val imageRes: Int,
    val description: String // Parameter yang menyebabkan error tadi
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationCategoryScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onCampaignClick: (DonationCampaign) -> Unit
) {
    val LogoBlue = Color(0xFF1956B4)
    var searchQuery by remember { mutableStateOf("") }

    val allCampaigns = when (categoryName) {
        "Bencana" -> listOf(
            DonationCampaign("Krisis Bantuan!!! Bantu Korban Terdampak Banjir Aceh Tamiang", "BAZNAS Hub", "Rp4M", 182, 0.45f, R.drawable.sumatera, "Bantuan darurat untuk warga Aceh Tamiang."),
            DonationCampaign("Bantu Korban Banjir Kalimantan Selatan", "Rumah Zakat Kalimantan Selatan", "Rp100jt", 89, 0.75f, R.drawable.kalsel, "Logistik untuk pengungsi banjir Kalsel."),
            DonationCampaign("Bersatu, Bantu Sumatera Pulih", "Rumah Wakaf", "Rp750jt", 89, 0.75f, R.drawable.aceh_tamiang, "Rekonstruksi fasilitas umum di Sumatera.")
        )
        "Kemanusiaan" -> listOf(
            DonationCampaign("Sisihkan Rezeki Bagi Makanan Gratis Untuk Anak Panti", "Salam Setara Amanah Nusantara", "Rp250jt", 15, 0.90f, R.drawable.panti, "Nutrisi harian untuk anak panti asuhan."),
            DonationCampaign("Beasiswa Kelana Cerdas: Wujudkan Mimpi Masa Depan", "Kelana Foundation", "Rp350jt", 45, 0.60f, R.drawable.kelana, "Bantuan biaya sekolah siswa prasejahtera."),
            DonationCampaign("Tolong Pasien Tidur Terbengkalai di Rumah Sakit", "Sekawan Foundation", "Rp100jt", 45, 0.60f, R.drawable.sosial, "Biaya pengobatan pasien dhuafa.")
        )
        "Rumah Ibadah" -> listOf(
            DonationCampaign("Wakaf Quran Pelosok, Satu Ayat Seribu Kebaikan", "Yayasan Bangun Umat Nurani", "Rp10jt", 60, 0.30f, R.drawable.quran, "Pengadaan Mushaf Quran untuk pelosok."),
            DonationCampaign("Bantu Gereja GEPEMBRI Balai Sebut", "Yayasan Cahaya Pelita Timur", "Rp5jt", 60, 0.30f, R.drawable.gereja, "Renovasi gereja di pedalaman Kalimantan."),
            DonationCampaign("Sedekah Jariyah Bangun Masjid Pelosok", "Harapan Amal Mulia", "Rp50jt", 120, 0.50f, R.drawable.masjid, "Pembangunan masjid permanen untuk warga desa.")
        )
        "Lingkungan" -> listOf(
            DonationCampaign("Dukung Penjaga Hutan Adat Papua", "WWF-Indonesia", "Rp100jt", 20, 0.70f, R.drawable.papua, "Proteksi hutan Papua dari pembalakan liar."),
            DonationCampaign("Bersihkan Sampah, Hijaukan Bumi", "Program Sahabat Dhuafa Banyuwangi", "Rp5jt", 20, 0.70f, R.drawable.sampah, "Aksi pembersihan sampah di area pesisir."),
            DonationCampaign("Sedekah Pohon, Lahan Kritis Terdampak Bencana", "Rengganis Indonesia Foundation", "Rp70jt", 7, 0.95f, R.drawable.pohon, "Penghijauan lahan kritis pasca kebakaran.")
        )
        else -> listOf(
            DonationCampaign("Kasih Kucing Liar Terlantar Makan Layak", "Animal Hope", "Rp2jt", 30, 0.20f, R.drawable.kucing, "Program feeding rutin kucing jalanan."),
            DonationCampaign("Patungan biaya steril kucing dan anjing jalanan", "Animal Hope", "Rp1jt", 30, 0.20f, R.drawable.combo, "Sterilisasi hewan untuk cegah overpopulasi."),
            DonationCampaign("Tumor Kanker Membesar,Cecel Butuh Operasi Segera", "Animal Hope", "Rp2jt", 30, 0.20f, R.drawable.anjing, "Bantuan medis darurat untuk anjing Cecel.")
        )
    }

    val filteredCampaigns = allCampaigns.filter {
        it.title.contains(searchQuery, ignoreCase = true) || it.organization.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(categoryName, color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LogoBlue)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color(0xFFF5F7F9))
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("Cari kampanye atau yayasan...", fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = LogoBlue) },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = LogoBlue,
                    unfocusedBorderColor = Color.LightGray,
                    containerColor = Color.White
                ),
                singleLine = true
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredCampaigns) { campaign ->
                    CampaignCard(campaign, LogoBlue, onCampaignClick)
                }
            }
        }
    }
}

@Composable
fun CampaignCard(
    campaign: DonationCampaign,
    accentColor: Color,
    onCardClick: (DonationCampaign) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = { onCardClick(campaign) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp).height(IntrinsicSize.Min)) {
            Image(
                painter = painterResource(id = campaign.imageRes),
                contentDescription = null,
                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.fillMaxHeight()) {
                Text(text = campaign.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 2)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(text = campaign.organization, fontSize = 11.sp, color = Color.Gray)
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF03A9F4), modifier = Modifier.size(12.dp).padding(start = 2.dp))
                }
                Spacer(modifier = Modifier.weight(1f))
                LinearProgressIndicator(progress = campaign.progress, modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape), color = accentColor, trackColor = accentColor.copy(alpha = 0.2f))
                Column(modifier = Modifier.padding(top = 4.dp)) {
                    Text("Terkumpul", fontSize = 10.sp, color = Color.Gray)
                    Text(text = campaign.collectedAmount, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = accentColor)
                }
            }
        }
    }
}