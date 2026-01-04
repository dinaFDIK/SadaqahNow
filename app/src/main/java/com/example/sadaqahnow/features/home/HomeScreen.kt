package com.example.sadaqahnow.features.home

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SadaqahNowHomeScreen(
    streakCount: Int, // Menerima data streak dinamis dari Navigasi
    onDonateClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCategoryClick: (String) -> Unit
) {
    val LogoBlue = Color(0xFF1956B4)
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(LogoBlue.copy(alpha = 0.9f), Color.White)
    )

    var isProcessing by remember { mutableStateOf(false) }

    // --- LOGIKA QUOTES OTOMATIS (15 DETIK) ---
    val quotes = listOf(
        "Sedekah paling utama adalah saat engkau sehat dan bugar.",
        "Harta tidak akan berkurang karena sedekah.",
        "Sedekah itu menghapus dosa sebagaimana air memadamkan api.",
        "Tangan di atas lebih baik daripada tangan di bawah."
    )
    var currentQuoteIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(15000) // Sesuai permintaan awal: 15 detik
            currentQuoteIndex = (currentQuoteIndex + 1) % quotes.size
        }
    }

    // Simulasi loading saat klik tombol sedekah utama
    LaunchedEffect(isProcessing) {
        if (isProcessing) {
            delay(1500)
            isProcessing = false
            onDonateClick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackground)
    ) {
        // --- TOP BAR ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("SadaqahNow", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
            Surface(
                modifier = Modifier.size(40.dp).clickable { onProfileClick() },
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.2f),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- KATEGORI ---
            Text(
                "Pilih Kategori Kebaikan",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
            )

            val categories = listOf(
                CategoryData("Bencana", Icons.Default.Warning),
                CategoryData("Kemanusiaan", Icons.Default.Groups),
                CategoryData("Rumah Ibadah", Icons.Default.Mosque),
                CategoryData("Lingkungan", Icons.Default.Park),
                CategoryData("Hewan", Icons.Default.Pets)
            )

            // Grid Kategori Baris 1
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                categories.take(3).forEach { category ->
                    CategoryItem(category, LogoBlue, onClick = { onCategoryClick(category.name) })
                }
            }
            // Grid Kategori Baris 2
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                categories.drop(3).forEach { category ->
                    CategoryItem(category, LogoBlue, onClick = { onCategoryClick(category.name) })
                }
            }

            // --- QUOTES ---
            Card(
                modifier = Modifier.fillMaxWidth().height(80.dp).padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.FormatQuote, contentDescription = null, tint = Color.White.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.width(12.dp))
                    AnimatedContent(
                        targetState = quotes[currentQuoteIndex],
                        transitionSpec = { fadeIn() + slideInVertically() togetherWith fadeOut() + slideOutVertically() },
                        label = "QuoteAnimation"
                    ) { text ->
                        Text(
                            text = "\"$text\"",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- GAMIFICATION CARD DINAMIS ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween, // Memberikan ruang maksimal antar Column
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Bagian Kiri: Streak
                    Column {
                        Text(
                            text = "Streak Saat Ini",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "$streakCount Hari 🔥",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Bagian Kanan: Level (Sekarang Rata Kanan)
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Level",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = "Level 3",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- TOMBOL DONASI LINGKARAN ---
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                    .padding(12.dp)
                    .background(Color.White.copy(alpha = 0.1f), CircleShape)
                    .clickable(enabled = !isProcessing) { isProcessing = true },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.size(140.dp),
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    if (isProcessing) {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = LogoBlue, modifier = Modifier.size(30.dp))
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Text("Sedekah", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = LogoBlue)
                            Text("Sekarang", fontSize = 16.sp, color = LogoBlue.copy(alpha = 0.7f))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CategoryItem(
    category: CategoryData,
    accentColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable { onClick() }
    ) {
        Surface(
            modifier = Modifier.size(65.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = category.name,
                    tint = accentColor,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            fontSize = 11.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

data class CategoryData(val name: String, val icon: ImageVector)