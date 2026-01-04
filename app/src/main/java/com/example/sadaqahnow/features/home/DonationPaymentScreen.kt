package com.example.sadaqahnow.features.home

import androidx.compose.foundation.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationPaymentScreen(
    campaign: DonationCampaign,
    onBackClick: () -> Unit,
    onConfirmClick: (Long) -> Unit,
    onSelectPaymentSourceClick: () -> Unit,
    selectedPaymentSource: String = "SadaqahNow Wallet"
) {
    val LogoBlue = Color(0xFF1956B4)
    var selectedAmount by remember { mutableLongStateOf(2000L) }
    val microAmounts = listOf(1000L, 2000L, 5000L, 10000L, 20000L, 50000L)

    val displayTitle = campaign.title.replace(
        "Krisis Bantuan!!! Bantu Korban Terdampak",
        "Mari bantu korban terdampak"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pilih Nominal Sedekah", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.Close, contentDescription = null) }
                }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onConfirmClick(selectedAmount) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LogoBlue)
                ) {
                    Text("Konfirmasi Sedekah", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Ringkasan Kampanye
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7F9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Favorite, contentDescription = null, tint = LogoBlue, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = displayTitle, fontSize = 14.sp, fontWeight = FontWeight.Medium, maxLines = 1)
                }
            }

            Text("Pilih Nominal Sedekah Mikro", modifier = Modifier.padding(vertical = 20.dp), fontWeight = FontWeight.Bold, fontSize = 16.sp)

            // Grid Nominal
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                microAmounts.chunked(3).forEach { rowAmounts ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        rowAmounts.forEach { amount ->
                            // MEMANGGIL FUNGSI AmountBox DI BAWAH
                            AmountBox(
                                amount = amount,
                                isSelected = selectedAmount == amount,
                                onClick = { selectedAmount = amount },
                                accentColor = LogoBlue,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Metode Pembayaran Dinamis
            Text("Metode Pembayaran", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable { onSelectPaymentSourceClick() },
                border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val iconSource = if (selectedPaymentSource == "SadaqahNow Wallet")
                        Icons.Default.AccountBalanceWallet else Icons.Default.Payments

                    Icon(iconSource, contentDescription = null, tint = LogoBlue)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = selectedPaymentSource, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "Klik untuk mengganti metode", fontSize = 11.sp, color = Color.Gray)
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
                }
            }
        }
    }
}

// INI FUNGSI YANG TADI HILANG: AmountBox
@Composable
fun AmountBox(
    amount: Long,
    isSelected: Boolean,
    onClick: () -> Unit,
    accentColor: Color,
    modifier: Modifier
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) accentColor else Color(0xFFEEEEEE)),
        color = if (isSelected) accentColor.copy(alpha = 0.05f) else Color.White
    ) {
        Box(modifier = Modifier.padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
            Text(
                text = "Rp${String.format("%,d", amount).replace(',', '.')}",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (isSelected) accentColor else Color.Black
            )
        }
    }
}