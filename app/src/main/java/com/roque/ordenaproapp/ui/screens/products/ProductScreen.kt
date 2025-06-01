package com.roque.ordenaproapp.ui.screens.products

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roque.domain.util.Result

@Composable
fun ProductScreen(viewModel: ProductViewModel = hiltViewModel()) {

    val result by viewModel.products.collectAsState()
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.searchProducts(query, selectedCategory)
            },
            label = { Text("Buscar productos...") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        // Ejemplo de filtro de categorías fijas
        Row(Modifier.horizontalScroll(rememberScrollState())) {
            listOf("smartphones", "laptops", "fragrances").forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = {
                        selectedCategory = if (selectedCategory == category) null else category
                        viewModel.searchProducts(query, selectedCategory)
                    },
                    label = { Text(category) },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        when (val state = result) {
            is Result.Success -> {
                LazyColumn {
                    items(state.data) { product ->
                        Text(product.title, modifier = Modifier.padding(8.dp))
                    }
                }
            }
            is Result.Error -> Text("Error al buscar")
            //Result.Loading -> CircularProgressIndicator()
        }
    }
}
