package com.org.trademe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.org.trademe.core.theme.TradeMeTheme
import com.org.trademe.navigation.NavigationRegistry
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationRegistry: NavigationRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TradeMeTheme {
                TradeMeApp(navigationRegistry)
            }
        }
    }
}