package com.roque.ordenaproapp.ui.screens.products.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.roque.ordenaproapp.R
import com.roque.ordenaproapp.ui.composables.CustomFilterChip
import com.roque.ordenaproapp.ui.composables.ProductCard
import com.roque.ordenaproapp.ui.composables.RoundedImage
import com.roque.ordenaproapp.ui.composables.SearchBar
import com.roque.ordenaproapp.ui.screens.cart.CartViewModel
import kotlinx.coroutines.delay

@Composable
fun ProductScreen(
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    navigateToDetail: (String) -> Unit,
    navigateToCart: () -> Unit,
    navigateToOrders: () -> Unit
) {

    val state by productViewModel.uiState.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val totalQuantity = cartItems.sumOf { it.quantity }
    val hasItems = totalQuantity > 0

    var bumpTrigger by remember { mutableStateOf(0) }
    val scale by animateFloatAsState(
        targetValue = if (bumpTrigger > 0) 1.15f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "cart-fab-bump"
    )

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
        productViewModel.loadCategories()
    }

    LaunchedEffect(totalQuantity) {
        if (hasItems) {
            bumpTrigger++
            delay(150)
            bumpTrigger = 0
        }
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = hasItems,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                FloatingActionButton(
                    onClick = navigateToCart,
                    modifier = Modifier.scale(scale),
                    containerColor = Color(0xFFFF3B30),
                    contentColor = Color.White
                ) {
                    BadgedBox(badge = {
                        Badge {
                            Text(cartItems.sumOf { it.quantity }.toString())
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Ir al carrito"
                        )
                    }
                }
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo),
                            contentDescription = "Logo",
                            colorFilter = ColorFilter.tint(Color(0xFFFF3B30)),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "Ordena tus productos favoritos!",
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                   RoundedImage(url = "https://static.vecteezy.com/system/resources/previews/026/408/485/non_2x/man-lifestyle-portrait-hipster-serious-t-shirt-isolated-person-white-background-american-smile-confident-fashion-photo.jpg")
                }
            }

            if (state.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(60.dp),
                            strokeWidth = 6.dp,
                            color = Color(0xFFFF3B30)
                        )
                    }
                }
            } else if (state.error != null) {
                item {
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
                }
            } else {
                item {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                            productViewModel.searchProducts(searchQuery, selectedCategory)
                        },
                        navigateToOrders = { navigateToOrders() }
                    )
                }

                item {
                    Row(Modifier.horizontalScroll(rememberScrollState())) {

                        state.categories.forEach { category ->
                            CustomFilterChip(
                                label = category.name.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase() else it.toString()
                                },
                                selected = selectedCategory == category.name,
                                onClick = {
                                    selectedCategory =
                                        if (selectedCategory == category.name) null else category.name
                                    productViewModel.searchProducts(searchQuery, selectedCategory)
                                }
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                gridItems(
                    data = state.products,
                    columns = 2,
                    horizontalSpacing = 8.dp,
                    verticalSpacing = 8.dp
                ) { product ->
                    ProductCard(product = product) {
                        navigateToDetail(product.id)
                    }
                }
            }
        }
    }
}


fun <T> LazyListScope.gridItems(
    data: List<T>,
    columns: Int,
    horizontalSpacing: Dp,
    verticalSpacing: Dp,
    itemContent: @Composable (T) -> Unit
) {
    val rows = (data.size + columns - 1) / columns
    items(rows) { rowIndex ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
            modifier = Modifier.fillMaxWidth()
        ) {
            for (columnIndex in 0 until columns) {
                val itemIndex = rowIndex * columns + columnIndex
                if (itemIndex < data.size) {
                    Box(modifier = Modifier.weight(1f)) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        Spacer(modifier = Modifier.height(verticalSpacing))
    }
}