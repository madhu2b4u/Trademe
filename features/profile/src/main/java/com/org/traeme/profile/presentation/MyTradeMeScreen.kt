package com.org.traeme.profile.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.org.trademe.core.theme.TradeMeColors
import com.org.trademe.profile.R

@Composable
fun MyTradeMeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.my_trade_me),
            style = MaterialTheme.typography.headlineMedium,
            color = TradeMeColors.BluffOyster800
        )
    }
}