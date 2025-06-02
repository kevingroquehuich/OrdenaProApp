package com.roque.ordenaproapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.roque.ordenaproapp.ui.screens.cart.CartScreen
import com.roque.ordenaproapp.ui.screens.cart.CartViewModel
import com.roque.ordenaproapp.ui.screens.orders.list.OrderListScreen
import com.roque.ordenaproapp.ui.screens.orders.list.OrderListViewModel
import com.roque.ordenaproapp.ui.screens.orders.summary.OrderSummaryScreen
import com.roque.ordenaproapp.ui.screens.orders.summary.OrderSummaryViewModel
import com.roque.ordenaproapp.ui.screens.products.list.ProductScreen
import com.roque.ordenaproapp.ui.screens.products.list.ProductViewModel
import com.roque.ordenaproapp.ui.screens.products.detail.ProductDetailScreen
import com.roque.ordenaproapp.ui.screens.products.detail.ProductsDetailViewModel

@Composable
fun NavigationWrapper() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Products) {
        composable<Products> {
            val productViewModel: ProductViewModel = hiltViewModel()
            val cartViewModel: CartViewModel = hiltViewModel()
            ProductScreen(
                productViewModel = productViewModel,
                cartViewModel = cartViewModel,
                navigateToDetail = { id ->
                    navController.navigate(ProductDetail(id))
                },
                navigateToCart = { navController.navigate(Cart) },
                navigateToOrders = { navController.navigate(OrderList) }
            )
        }

        composable<ProductDetail> { backStackEntry ->
            val productDetail: ProductDetail = backStackEntry.toRoute()
            val productsDetailViewModel: ProductsDetailViewModel = hiltViewModel()
            val cartViewModel: CartViewModel = hiltViewModel()
            ProductDetailScreen(
                productsDetailViewModel = productsDetailViewModel,
                cartViewModel = cartViewModel,
                productId = productDetail.id,
                onBack = { navController.popBackStack() }
            )
        }

        composable<Cart> {
            val cartViewModel: CartViewModel = hiltViewModel()
            CartScreen(
                cartViewModel = cartViewModel,
                onConfirmOrder = { navController.navigate(OrderSummary) },
                onBack = { navController.popBackStack() }
            )
        }

        composable<OrderSummary> {
            val orderSummaryViewModel: OrderSummaryViewModel = hiltViewModel()
            OrderSummaryScreen(
                orderSummaryViewModel = orderSummaryViewModel,
                selectedPayment = "credit",
                onSelectPayment = {},
                saveCard = true,
                onSaveCardToggle = {},
                customerName = "Keving Hanz Roque Huich",
                onBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Products) {
                    popUpTo<Products>{ inclusive = true }
                } }
            )
        }

        composable<OrderList> {
            val orderListViewModel: OrderListViewModel = hiltViewModel()
            OrderListScreen(orderListViewModel = orderListViewModel)
        }

    }
}