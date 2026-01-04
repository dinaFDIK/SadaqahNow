package com.example.sadaqahnow.features.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sadaqahnow.R
import com.example.sadaqahnow.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    onGoogleLoginClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Warna Biru Logo Konsisten
    val LogoBlue = Color(0xFF1956B4)

    // Gradasi Konsisten dengan Onboarding
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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Logo Kecil
        Image(
            painter = painterResource(id = R.drawable.sadaqahnow),
            contentDescription = null,
            modifier = Modifier.size(90.dp)
        )

        Text(
            text = "Selamat Datang",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Masuk untuk melanjutkan sedekahmu",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Tab Selector (Bahasa Indonesia)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.White, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Masuk", color = LogoBlue, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onRegisterClick() },
                contentAlignment = Alignment.Center
            ) {
                Text("Daftar", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Input Fields (Bahasa Indonesia & Warna Kontras)
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Alamat Email", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("nama@email.com", color = Color.White.copy(alpha = 0.5f)) },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    focusedBorderColor = Color.White,
                    cursorColor = Color.White,
                    containerColor = Color.White.copy(alpha = 0.1f)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Kata Sandi", color = LogoBlue, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = LogoBlue.copy(alpha = 0.3f),
                    focusedBorderColor = LogoBlue,
                    containerColor = Color.White.copy(alpha = 0.5f)
                )
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Tombol Login Solid
        Button(
            onClick = { onLoginClick(email, password) },
            modifier = Modifier.fillMaxWidth().height(58.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LogoBlue)
        ) {
            Text("Masuk Sekarang", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Google Button
        OutlinedButton(
            onClick = onGoogleLoginClick,
            modifier = Modifier.fillMaxWidth().height(58.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, LogoBlue.copy(alpha = 0.5f))
        ) {

            Image(
                painter = painterResource(id = R.drawable.google), // Pastikan punya icon google di drawable
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("Lanjut dengan Google", color = LogoBlue)
        }
    }
}