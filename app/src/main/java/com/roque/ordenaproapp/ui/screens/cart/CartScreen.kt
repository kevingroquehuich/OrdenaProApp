package com.roque.ordenaproapp.ui.screens.cart

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roque.ordenaproapp.ui.composables.CartItemCard
import com.roque.ordenaproapp.ui.composables.CheckoutBar

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onConfirmOrder: () -> Unit,
    onBack: () -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val total = cartItems.sumOf { it.price * it.quantity }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Spacer(Modifier.height(16.dp))
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.clickable { onBack() }
        )

        Spacer(Modifier.height(24.dp))

        Text("Carrito de Compras", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        if (cartItems.isEmpty()) {
            Text("Tu carrito está vacío.")
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onRemove = { cartViewModel.removeItem(item) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            CheckoutBar(
                totalPrice = total,
                textButton = "Ordenar ahora",
                colorButton = 0xFFFF3B30,
                onPayClick = { onConfirmOrder() }
            )
        }
    }
}
