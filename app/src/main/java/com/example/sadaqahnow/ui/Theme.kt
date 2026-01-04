package com.example.sadaqahnow.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = SadaqahBlue,
    onPrimary = Color.White,
    primaryContainer = SadaqahLightBlue,
    onPrimaryContainer = SadaqahBlue,
    surface = Color.White,
    onSurface = Color.Black,
    outline = SadaqahBorder
)

@Composable
fun SadaqahNowTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = androidx.compose.material3.Typography(), // Bisa disesuaikan nanti
        content = content
    )
}