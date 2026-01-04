package com.example.sadaqahnow.features.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sadaqahnow.R
import com.example.sadaqahnow.ui.*

@Composable
fun OnboardingScreen(onGetStartedClick: () -> Unit) {
    // Warna Biru Logo Anda
    val LogoBlue = Color(0xFF1956B4)

    // Gradasi dari Biru Logo (Atas) ke Putih (Bawah)
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            LogoBlue.copy(alpha = 0.9f), // Biru Logo di atas
            Color.White                 // Putih di bawah
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))

        // Logo Aplikasi
        Image(
            painter = painterResource(id = R.drawable.sadaqahnow),
            contentDescription = "Logo SadaqahNow",
            modifier = Modifier.size(110.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Judul dengan warna putih agar kontras di area biru atas
        Text(
            text = "SadaqahNow",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Text(
            text = "Sedekah Mikro Harian",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.9f)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Kartu Fitur menggunakan Putih Bersih agar "pop" di tengah gradasi
        OnboardingFeatureCard("Donasi Mikro", "Berbagi mulai dari Rp1.000 setiap hari.", Icons.Default.Favorite, LogoBlue)
        OnboardingFeatureCard("Bangun Konsistensi", "Jaga streak sedekahmu tanpa putus.", Icons.Default.Whatshot, LogoBlue)
        OnboardingFeatureCard("Naik Level & Reward", "Kumpulkan poin dan buka pencapaian.", Icons.Default.Star, LogoBlue)

        Spacer(modifier = Modifier.weight(1f))

        // Tombol "Mulai Sekarang" dengan Biru Logo Solid agar terlihat sangat resmi/official
        Button(
            onClick = onGetStartedClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LogoBlue,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = "Mulai Sekarang",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun OnboardingFeatureCard(title: String, desc: String, icon: ImageVector, accentColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        // Menggunakan putih solid dengan sedikit transparansi agar bersih
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = CircleShape,
                color = accentColor.copy(alpha = 0.1f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    fontSize = 16.sp
                )
                Text(
                    text = desc,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
            }
        }
    }
}