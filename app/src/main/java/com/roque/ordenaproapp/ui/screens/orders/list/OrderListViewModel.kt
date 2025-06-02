package com.roque.ordenaproapp.ui.screens.orders.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.domain.model.Order
import com.roque.domain.usecase.order.GetOrdersUseCase
import com.roque.ordenaproapp.utils.PdfActionsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val pdfActionsHelper: PdfActionsHelper
): ViewModel() {

    private val _uiState = MutableStateFlow(OrderListUiState())
    val uiState: StateFlow<OrderListUiState> = _uiState


    fun loadOrders() {
        viewModelScope.launch {
            getOrdersUseCase()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { orders -> _uiState.update { it.copy(orders = orders, isLoading = false) } }
        }
    }

    fun generateInvoice(context: Context, order: Order): File {
        return pdfActionsHelper.generatePdf(context, order)
    }

    fun openInvoice(context: Context, file: File) {
        pdfActionsHelper.openPdf(context, file)
    }

    fun shareInvoice(context: Context, file: File) {
        pdfActionsHelper.sharePdf(context, file)
    }
}