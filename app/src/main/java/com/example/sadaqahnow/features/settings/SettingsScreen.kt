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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sadaqahnow.ui.*

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onChangePaymentClick: () -> Unit
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
            Text(text = "Pengaturan", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // --- SECTION 1: PENGATURAN PEMBAYARAN ---
            SettingsSectionTitle("Pengaturan Pembayaran")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.CreditCard, contentDescription = null, tint = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Mandiri Virtual Account", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                            Text("•••• 4532", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
                        }
                        Button(
                            onClick = onChangePaymentClick,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                        ) {
                            Text("Ubah", color = LogoBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- SECTION 2: PREFERENSI NOTIFIKASI ---
            SettingsSectionTitle("Preferensi Notifikasi")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
            ) {
                Column {
                    SwitchSettingItem("Pengingat Harian", "Ingatkan saya untuk sedekah setiap hari", true)
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    SwitchSettingItem("Peringatan Streak", "Beri tahu jika streak dalam bahaya", true)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- SECTION 3: AKUN ---
            SettingsSectionTitle("Akun")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
            ) {
                Column {
                    NavigationSettingItem("Kebijakan Privasi", accentColor = LogoBlue)
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    NavigationSettingItem("Syarat dan Ketentuan", accentColor = LogoBlue)
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    NavigationSettingItem("Keluar", isDestructive = true, onClick = onLogoutClick, accentColor = LogoBlue)
                }
            }

            Text(
                text = "Versi 1.0.0",
                modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = LogoBlue.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Color.White.copy(alpha = 0.7f),
        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
    )
}

@Composable
fun SwitchSettingItem(title: String, subtitle: String, checked: Boolean) {
    var isChecked by remember { mutableStateOf(checked) }
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            Text(text = subtitle, fontSize = 11.sp, color = Color.White.copy(alpha = 0.6f))
        }
        Switch(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF1956B4), // Biru Logo
                checkedTrackColor = Color.White,
                uncheckedThumbColor = Color.White.copy(alpha = 0.6f),
                uncheckedTrackColor = Color.White.copy(alpha = 0.2f)
            )
        )
    }
}

@Composable
fun NavigationSettingItem(title: String, accentColor: Color, isDestructive: Boolean = false, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = if (isDestructive) Color(0xFFFF5252) else Color.White,
            fontWeight = if (isDestructive) FontWeight.Bold else FontWeight.Normal
        )
        Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White.copy(alpha = 0.4f))
    }
}