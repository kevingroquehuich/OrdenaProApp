package com.roque.ordenaproapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.roque.ordenaproapp.ui.navigation.NavigationWrapper
import com.roque.ordenaproapp.ui.screens.products.ProductScreen
import com.roque.ordenaproapp.ui.screens.products.detail.ProductDetailScreen
import com.roque.ordenaproapp.ui.theme.OrdenaProAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectNetwork()
                .penaltyLog()
                .build()
        )

        enableEdgeToEdge()
        setContent {
            OrdenaProAppTheme {
                Scaffold {
                    NavigationWrapper()
                }
            }
        }
    }
}
