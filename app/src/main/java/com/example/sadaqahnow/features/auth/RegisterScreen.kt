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
fun RegisterScreen(
    onRegisterClick: (String, String, String) -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Warna Biru Logo Konsisten
    val LogoBlue = Color(0xFF1956B4)

    // Gradasi Konsisten dengan Onboarding & Login
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
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Logo Aplikasi
        Image(
            painter = painterResource(id = R.drawable.sadaqahnow),
            contentDescription = null,
            modifier = Modifier.size(90.dp)
        )

        Text(
            text = "Daftar Akun",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Mulai langkah kebaikanmu hari ini",
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
                    .clickable { onLoginClick() },
                contentAlignment = Alignment.Center
            ) {
                Text("Masuk", color = Color.White)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.White, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Daftar", color = LogoBlue, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Form Pendaftaran (Bahasa Indonesia)
        Column(modifier = Modifier.fillMaxWidth()) {
            InputField(
                label = "Nama Lengkap",
                value = name,
                onValueChange = { name = it },
                accentColor = LogoBlue,
                placeholder = "Masukkan nama lengkap"
            )
            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                label = "Alamat Email",
                value = email,
                onValueChange = { email = it },
                accentColor = LogoBlue,
                placeholder = "nama@email.com"
            )
            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                label = "Kata Sandi",
                value = password,
                onValueChange = { password = it },
                isPassword = true,
                accentColor = LogoBlue,
                placeholder = "Minimal 8 karakter"
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Tombol Daftar Solid
        Button(
            onClick = { onRegisterClick(name, email, password) },
            modifier = Modifier.fillMaxWidth().height(58.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LogoBlue)
        ) {
            Text("Buat Akun Sekarang", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    accentColor: Color,
    placeholder: String,
    isPassword: Boolean = false
) {
    // Menentukan warna label berdasarkan posisi (apakah di area biru atau putih)
    // Di sini kita buat konsisten agar tetap terbaca jelas
    val labelColor = if (label == "Nama Lengkap") Color.White else accentColor

    Column {
        Text(label, color = labelColor, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = labelColor.copy(alpha = 0.5f)) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = labelColor.copy(alpha = 0.4f),
                focusedBorderColor = labelColor,
                containerColor = Color.White.copy(alpha = 0.3f)
            )
        )
    }
}