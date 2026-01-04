package com.example.sadaqahnow.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sadaqahnow.ui.*

@Composable
fun DonationSuccessScreen(
    amount: Long, // Tambahkan parameter ini
    onDoneClick: () -> Unit
) {
    // Format nominal agar rapi (contoh: 5.000)
    val formattedAmount = String.format("%,d", amount).replace(',', '.')

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = SadaqahBlue,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text("Donation Successful!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(
            "Your Rp$formattedAmount has been sent.", // Gunakan variabel di sini
            fontSize = 16.sp,
            color = SadaqahGray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onDoneClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SadaqahBlue)
        ) {
            Text("Back to Home")
        }
    }
}