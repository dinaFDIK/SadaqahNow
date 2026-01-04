package com.example.sadaqahnow.features.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sadaqahnow.core.navigation.DonationRecord // Import data class record

@Composable
fun ProfileScreen(
    streakCount: Int,      // Mengambil data dari Navigasi
    totalDonation: Long,   // Mengambil data dari Navigasi
    onBackClick: () -> Unit,
    onViewAllHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit = {}
) {
    val LogoBlue = Color(0xFF1956B4)

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            LogoBlue.copy(alpha = 0.9f),
            Color.White
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackground)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Profil", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // User Info Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.2f),
                    border = BorderStroke(2.dp, Color.White)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(text = "Karina Salma", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = "karinaslmaa@gmail.com", fontSize = 14.sp, color = Color.White.copy(alpha = 0.7f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Gamification Stats Dinamis
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(value = streakCount.toString(), label = "Hari Streak", icon = "🔥", modifier = Modifier.weight(1f))
                StatCard(value = "3", label = "Level", icon = "⭐", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Total Donations Card Dinamis
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Total Sedekah", fontSize = 14.sp, color = Color.Gray)

                    // Format angka Rp agar ada titik ribuan
                    val formattedTotal = String.format("%,d", totalDonation).replace(',', '.')
                    Text("Rp$formattedTotal", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = LogoBlue)

                    Text("Berbagi selama $streakCount hari", fontSize = 14.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Riwayat Sedekah
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            ) {
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text("Riwayat Sedekah Terbaru", fontWeight = FontWeight.Bold, color = LogoBlue)
                }

                // Item ini akan muncul jika kamu sudah berdonasi minimal sekali (streak > 7)
                if (streakCount > 7) {
                    HistoryItem("Hari Ini", "03 Jan 2026", "Berhasil", LogoBlue)
                }

                HistoryItem("Kemarin", "02 Jan 2026", "Rp1.000", LogoBlue)
                HistoryItem("01 Jan 2026", "08:45 WIB", "Rp1.000", LogoBlue)

                Text(
                    text = "Lihat Semua Riwayat",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onViewAllHistoryClick() }
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = LogoBlue,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatCard(value: String, label: String, icon: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = label, fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = icon, fontSize = 20.sp)
        }
    }
}

@Composable
fun HistoryItem(day: String, date: String, amount: String, accentColor: Color) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = day, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = accentColor)
                Text(text = date, fontSize = 12.sp, color = accentColor.copy(alpha = 0.6f))
            }
            Text(text = amount, fontWeight = FontWeight.Bold, color = accentColor)
        }
        HorizontalDivider(color = accentColor.copy(alpha = 0.1f), modifier = Modifier.padding(horizontal = 16.dp))
    }
}