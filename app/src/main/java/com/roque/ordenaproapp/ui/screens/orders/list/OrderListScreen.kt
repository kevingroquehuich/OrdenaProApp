package com.roque.ordenaproapp.ui.screens.orders.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.roque.ordenaproapp.ui.composables.OrderItemCard
import com.roque.ordenaproapp.utils.PdfActionsHelper
import com.roque.ordenaproapp.utils.PdfInvoiceGenerator

@Composable
fun OrderListScreen(
    orderListViewModel: OrderListViewModel,
    onBackClick: () -> Unit
) {
    val state by orderListViewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        orderListViewModel.loadOrders()
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp),
                strokeWidth = 6.dp,
                color = Color(0xFFFF3B30)
            )
        }
    } else if (state.error != null) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.error!!,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(Modifier.height(16.dp))
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { onBackClick() }
            )

            Spacer(Modifier.height(24.dp))

            Text("Lista de Órdenes", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.orders) { order ->
                    val pdfFile = remember(order) { orderListViewModel.generateInvoice(context, order) }

                    OrderItemCard(
                        order = order,
                        onGenerateInvoice = {
                            orderListViewModel.openInvoice(context, pdfFile)
                        },
                        onInvoiceView = {
                            orderListViewModel.openInvoice(context, pdfFile)
                        },
                        onInvoiceShare = {
                            orderListViewModel.shareInvoice(context, pdfFile)
                        }
                    )
                }
            }
        }
    }

}
