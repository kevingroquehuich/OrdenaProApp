package com.roque.ordenaproapp.ui.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.domain.usecase.product.GetCategoriesUseCase
import com.roque.domain.usecase.product.GetProductsUseCase
import com.roque.domain.usecase.product.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState

    fun loadProducts() {
        viewModelScope.launch {
            getAllProductsUseCase()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { products -> _uiState.update { it.copy(products = products, isLoading = false) } }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            viewModelScope.launch {
                getCategoriesUseCase()
                    .onStart { _uiState.update { it.copy(isLoading = true) }}
                    .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                    .collect { categories -> _uiState.update { it.copy(categories = categories, isLoading = false) } }
            }
        }
    }

    fun searchProducts(query: String, category: String? = null) {
        viewModelScope.launch {
            searchProductsUseCase(query, category).collect { products ->
                _uiState.update { it.copy(products = products) }
            }
        }
    }

}