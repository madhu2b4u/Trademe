package com.org.trademe.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun TradeMeTheme(content: @Composable () -> Unit) {
    val colorScheme = lightColorScheme(
        primary = TradeMeColors.Tasman500,
        secondary = TradeMeColors.Feijoa500,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onSurface = TradeMeColors.BluffOyster800,
        onSurfaceVariant = TradeMeColors.BluffOyster600
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}