package com.roque.ordenaproapp.ui.screens.products

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.roque.domain.model.Product
import com.roque.ordenaproapp.R
import com.roque.ordenaproapp.ui.composables.CustomFilterChip
import com.roque.ordenaproapp.ui.composables.ProductCard
import com.roque.ordenaproapp.ui.composables.RoundedImage
import com.roque.ordenaproapp.ui.composables.SearchBar

@Composable
fun ProductScreen(
    productViewModel: ProductViewModel,
    navigateToDetail: (String) -> Unit
) {

    val state by productViewModel.uiState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
        productViewModel.loadCategories()
    }

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

                RoundedImage(url = "https://t4.ftcdn.net/jpg/03/76/47/81/360_F_376478182_yPuPo2qi6rYcu9ilwGWR6gQ7QBBC8Isw.jpg")
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
                    onShoppingCartClick = {}
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