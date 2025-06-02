package com.roque.ordenaproapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.roque.ordenaproapp.ui.screens.products.ProductScreen
import com.roque.ordenaproapp.ui.screens.products.ProductViewModel
import com.roque.ordenaproapp.ui.screens.products.detail.ProductDetailScreen
import com.roque.ordenaproapp.ui.screens.products.detail.ProductsDetailViewModel

@Composable
fun NavigationWrapper() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Products) {
        composable<Products> {
            val productViewModel: ProductViewModel = hiltViewModel()
            ProductScreen(productViewModel = productViewModel) { id ->
                navController.navigate(ProductDetail(id))
            }
        }

        composable<ProductDetail> { backStackEntry ->
            val productDetail: ProductDetail = backStackEntry.toRoute()
            val productsDetailViewModel: ProductsDetailViewModel  = hiltViewModel()
            ProductDetailScreen(
                productsDetailViewModel = productsDetailViewModel,
                productId = productDetail.id
            )
        }

    }
}