package com.roque.ordenaproapp.ui.screens.products.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.domain.usecase.product.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            getProductByIdUseCase(productId)
                .onStart { _uiState.update { it.copy(isLoading = true) }}
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) }}
                .collect { product -> _uiState.update { it.copy(product = product, isLoading = false) }}
        }
    }
}