package com.roque.ordenaproapp.ui.screens.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.domain.model.Category
import com.roque.domain.model.Product
import com.roque.domain.usecase.product.GetCategoriesUseCase
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
    private val searchProductsUseCase: SearchProductsUseCase,
    private val categoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<Result<List<Product>>>(Result.Success(emptyList()))
    val products: StateFlow<Result<List<Product>>> = _products

    private val _categories = MutableStateFlow<Result<List<Category>>>(Result.Success(emptyList()))
    val categories: StateFlow<Result<List<Category>>> = _categories

    init {
        loadProducts()
        loadCategories()
    }

    fun loadProducts() {
        viewModelScope.launch {
            val result = getAllProductsUseCase()
            _products.value = result
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            val result = categoriesUseCase()
            _categories.value = result
        }
    }

    fun searchProducts(query: String, category: String? = null) {
        viewModelScope.launch {
            //_products.value = Result.Loading
            _products.value = searchProductsUseCase(query, category)
        }
    }

}