package com.example.sadaqahnow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
// Import tema dan navigasi dari sub-folder yang benar
import com.example.sadaqahnow.ui.SadaqahNowTheme
import com.example.sadaqahnow.core.navigation.SadaqahNowNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Mengaplikasikan tema nuansa biru yang sudah dibuat di folder ui/
            SadaqahNowTheme {
                // Surface menyediakan background dasar sesuai skema warna tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Memanggil pusat navigasi untuk menampilkan screen pertama (Onboarding)
                    SadaqahNowNavGraph()
                }
            }
        }
    }
}