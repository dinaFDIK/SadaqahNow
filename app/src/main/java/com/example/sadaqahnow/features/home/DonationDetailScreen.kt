package com.example.sadaqahnow.features.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DonationDetailScreen(
    campaign: DonationCampaign,
    onBackClick: () -> Unit,
    onDonateNowClick: () -> Unit
) {
    val LogoBlue = Color(0xFF1956B4)

    Scaffold(
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
                    onClick = onDonateNowClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LogoBlue)
                ) {
                    Text("Sedekah Sekarang", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            // 1. Gambar Header Besar
            Box(modifier = Modifier.height(280.dp).fillMaxWidth()) {
                Image(
                    painter = painterResource(id = campaign.imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                }
            }

            // 2. Judul dan Info Progres (Tanpa Info Hari)
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = campaign.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = campaign.collectedAmount,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = LogoBlue
                )

                Text(
                    text = "Terkumpul dari target",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = campaign.progress,
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                    color = LogoBlue,
                    trackColor = LogoBlue.copy(alpha = 0.2f)
                )
            }

            Divider(thickness = 8.dp, color = Color(0xFFF5F7F9))

            // 3. Info Penggalang Dana
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Informasi Penggalangan Dana", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(45.dp),
                        shape = CircleShape,
                        color = Color(0xFFF5F7F9)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = campaign.organization, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF03A9F4),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Text(text = "Identitas terverifikasi", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            Divider(thickness = 1.dp, color = Color(0xFFEEEEEE), modifier = Modifier.padding(horizontal = 16.dp))

            // 4. Cerita Penggalangan Dana
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Deskripsi Penggalangan Dana", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = campaign.description, // SEKARANG SUDAH DINAMIS
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}