package com.roque.ordenaproapp.ui.screens.products

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.roque.domain.util.Result
import com.roque.ordenaproapp.R
import com.roque.ordenaproapp.ui.composables.CustomFilterChip
import com.roque.ordenaproapp.ui.composables.ProductCard
import com.roque.ordenaproapp.ui.composables.RoundedImage
import com.roque.ordenaproapp.ui.composables.SearchBar
import java.util.Locale

@Composable
fun ProductScreen(modifier: Modifier, viewModel: ProductViewModel = hiltViewModel()) {

    val result by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

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
                        text = "Ordena tu comida favorita!",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                RoundedImage(url = "https://t4.ftcdn.net/jpg/03/76/47/81/360_F_376478182_yPuPo2qi6rYcu9ilwGWR6gQ7QBBC8Isw.jpg")
            }
        }

        item {
            SearchBar(
                query = query,
                onQueryChange = {
                    query = it
                    viewModel.searchProducts(query, selectedCategory)
                },
                onFilterClick = { /* Filtros */ }
            )
        }

        item {
            Row(Modifier.horizontalScroll(rememberScrollState())) {
                when (val state = categories) {
                    is Result.Success -> {
                        state.data.forEach { category ->
                            CustomFilterChip(
                                label = category.name.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase() else it.toString()
                                },
                                selected = selectedCategory == category.slug,
                                onClick = {
                                    selectedCategory =
                                        if (selectedCategory == category.slug) null else category.slug
                                    viewModel.searchProducts(query, selectedCategory)
                                }
                            )
                        }
                    }

                    is Result.Error -> Text("Error al obtener categorías")
                }
            }
        }

        // Grid de productos
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        result.let { state ->
            when (state) {
                is Result.Success -> {
                    gridItems(
                        data = state.data,
                        columns = 2,
                        horizontalSpacing = 8.dp,
                        verticalSpacing = 8.dp
                    ) { product ->
                        ProductCard(product)
                    }
                }

                is Result.Error -> {
                    item { Text("Error al buscar") }
                }

                else -> {
                    item {
                        CircularProgressIndicator(Modifier.padding(16.dp))
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