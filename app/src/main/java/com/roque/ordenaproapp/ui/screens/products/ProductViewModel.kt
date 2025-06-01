package com.roque.ordenaproapp.ui.screens.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.domain.model.Product
import com.roque.domain.usecase.product.GetProductsUseCase
import com.roque.domain.usecase.product.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.roque.domain.util.Result
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<Result<List<Product>>>(Result.Success(emptyList()))
    val products: StateFlow<Result<List<Product>>> = _products

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            val result = getAllProductsUseCase()
            Log.e("Products", result.toString())
            _products.value = result
        }
    }

    fun searchProducts(query: String, category: String? = null) {
        viewModelScope.launch {
            //_products.value = Result.Loading
            _products.value = searchProductsUseCase(query, category)
        }
    }

}