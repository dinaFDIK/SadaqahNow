package com.example.sadaqahnow.features.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sadaqahnow.ui.*

@Composable
fun PaymentSourceScreen(
    onBackClick: () -> Unit,
    onSourceSelected: (String) -> Unit
) {
    // Warna Biru Logo Konsisten
    val LogoBlue = Color(0xFF1956B4)

    // Gradasi Konsisten dengan Screen Sebelumnya
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            LogoBlue.copy(alpha = 0.9f),
            Color.White
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackground) // Menggunakan gradasi logo
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
            Text(text = "Hubungkan Pembayaran", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Pilih metode pembayaran utama untuk sedekah mikro harianmu.",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // --- SEKSI E-WALLET ---
            PaymentCategoryTitle("E-Wallet")
            PaymentMethodItem("GoPay", "Pembayaran cepat & aman", "G", onSourceSelected, LogoBlue)
            PaymentMethodItem("OVO", "Isi ulang mudah", "O", onSourceSelected, LogoBlue)
            PaymentMethodItem("Dana", "Pilihan populer", "D", onSourceSelected, LogoBlue)
            MoreOptionsItem("Lihat E-Wallet lainnya") { /* Logika Lainnya */ }

            Spacer(modifier = Modifier.height(24.dp))

            // --- SEKSI MOBILE BANKING ---
            PaymentCategoryTitle("Mobile Banking")
            PaymentMethodItem("Mandiri Livin'", "Mendukung auto-debit", "M", onSourceSelected, LogoBlue)
            PaymentMethodItem("BCA Mobile", "Koneksi stabil & andal", "B", onSourceSelected, LogoBlue)
            PaymentMethodItem("BNI Mobile", "Integrasi sederhana", "B", onSourceSelected, LogoBlue)
            MoreOptionsItem("Lihat Bank lainnya") { /* Logika Lainnya */ }

            Spacer(modifier = Modifier.height(32.dp))

            // Kotak Informasi (Transparan Modern)
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = LogoBlue, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "SadaqahNow akan menarik Rp1.000 secara otomatis setiap hari dari sumber yang kamu pilih.",
                        fontSize = 13.sp,
                        color = LogoBlue,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PaymentCategoryTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Color.White.copy(alpha = 0.7f),
        modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
    )
}

@Composable
fun PaymentMethodItem(
    name: String,
    description: String,
    initial: String,
    onSelect: (String) -> Unit,
    accentColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable { onSelect(name) },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(10.dp),
                color = Color.White
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = initial, fontWeight = FontWeight.ExtraBold, color = accentColor, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                Text(text = description, fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
            }

            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.White.copy(alpha = 0.5f))
        }
    }
}

@Composable
fun MoreOptionsItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(end = 4.dp)
        )
        Icon(Icons.Default.ExpandMore, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
    }
}