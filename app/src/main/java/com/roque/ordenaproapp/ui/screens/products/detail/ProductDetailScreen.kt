package com.roque.ordenaproapp.ui.screens.products.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.roque.domain.model.CartItem
import com.roque.ordenaproapp.ui.screens.cart.CartViewModel

@Composable
fun ProductDetailScreen(
    productsDetailViewModel: ProductsDetailViewModel,
    cartViewModel: CartViewModel,
    productId: String,
    onBack: () -> Unit
) {

    var quantity by remember { mutableStateOf(1) }
    val state by productsDetailViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        productsDetailViewModel.loadProduct(productId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    strokeWidth = 6.dp,
                    color = Color(0xFFFF3B30)
                )
            }
        } else if (state.error != null) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.error!!,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {

            state.product?.let { product ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {

                    Spacer(Modifier.height(16.dp))
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.clickable { onBack() }
                    )

                    Spacer(Modifier.height(32.dp))

                    AsyncImage(
                        model = product.thumbnail,
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFF9800))
                        Text(" ${product.rating }", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.width(8.dp))
                        Text("– 26 mins", color = Color.Gray)
                    }

                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(Modifier.height(24.dp))

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 16.dp, vertical = 44.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { if (quantity > 1) quantity-- }) {
                            Box(
                                modifier = Modifier
                                    .background(Color.Red, RoundedCornerShape(12.dp))
                                    .padding(4.dp)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Minus", tint = Color.White)
                            }
                        }
                        Text("$quantity", Modifier.padding(horizontal = 8.dp))
                        IconButton(onClick = { quantity++ }) {
                            Box(
                                modifier = Modifier
                                    .background(Color.Red, RoundedCornerShape(12.dp))
                                    .padding(4.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Plus", tint = Color.White)
                            }
                        }
                    }

                    Spacer(Modifier.width(24.dp))

                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF3C2E25))
                            .clickable {
                                val item = CartItem(
                                    productId = product.id,
                                    title = product.title,
                                    price = product.price,
                                    thumbnail = product.thumbnail,
                                    quantity = quantity
                                )
                                cartViewModel.addItem(item)
                                onBack()
                            }
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Text("Agregar", color = Color.White, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.width(24.dp))
                            Text(text = "S/. ${"%.2f".format((product.price*quantity))}", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
