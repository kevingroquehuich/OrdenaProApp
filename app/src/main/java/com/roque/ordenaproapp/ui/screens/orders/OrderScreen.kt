package com.roque.ordenaproapp.ui.screens.orders

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roque.ordenaproapp.R
import com.roque.ordenaproapp.ui.composables.CheckoutBar
import com.roque.ordenaproapp.ui.composables.OrderSuccessDialog


@Composable
fun OrderSummaryScreen(
    orderViewModel: OrderViewModel,
    selectedPayment: String,
    onSelectPayment: (String) -> Unit,
    saveCard: Boolean,
    onSaveCardToggle: (Boolean) -> Unit,
    customerName: String,
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {

    val uiState by orderViewModel.uiState.collectAsState()
    val pricing by orderViewModel.pricing.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        orderViewModel.getCartItems()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                Spacer(Modifier.height(16.dp))

                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.clickable { onBack() }
                )

                Spacer(Modifier.height(32.dp))

                OrderSummarySection(
                    subtotal = pricing.subtotal,
                    taxes = pricing.taxes,
                    deliveryFee = pricing.deliveryFee,
                    total = pricing.total
                )

                Spacer(Modifier.height(40.dp))

                Text(
                    text = "Payment methods",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(16.dp))

                PaymentMethodCard(
                    brandLogo = R.drawable.ic_mastercard_logo,
                    title = "Credit card",
                    cardNumber = "5105 **** **** 0505",
                    selected = selectedPayment == "credit",
                    onClick = { onSelectPayment("credit") },
                    backgroundColor = Color(0xFF3C2E25),
                    contentColor = Color.White
                )

                Spacer(Modifier.height(12.dp))

                PaymentMethodCard(
                    brandLogo = R.drawable.ic_visa_logo,
                    title = "Debit card",
                    cardNumber = "3566 **** **** 0505",
                    selected = selectedPayment == "debit",
                    onClick = { onSelectPayment("debit") },
                    backgroundColor = Color(0xFFF5F5F5),
                    contentColor = Color.Black
                )

                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = saveCard,
                        onCheckedChange = onSaveCardToggle,
                        colors = CheckboxDefaults.colors(checkedColor = Color.Red)
                    )
                    Text("Guardar los datos de la tarjeta para futuros pagos?")
                }
            }

            Spacer(Modifier.height(16.dp))

            CheckoutBar(
                totalPrice = pricing.total,
                textButton = "Confirmar Pedido",
                colorButton = 0xFF3C2E25,
                onPayClick = {
                    orderViewModel.confirmOrder(customerName)
                }
            )
        }

        when (val state = uiState) {
            is OrderUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    strokeWidth = 6.dp,
                    color = Color(0xFFFF3B30)
                )
            }

            is OrderUiState.Success -> {
                OrderSuccessDialog(
                    onDismiss = {
                        orderViewModel.resetState()
                        onNavigateToHome()
                    }
                )
            }

            is OrderUiState.Error -> {
                LaunchedEffect(state.message) {
                    snackbarHostState.showSnackbar(state.message)
                    orderViewModel.resetState()
                }
            }

            else -> Unit
        }
    }

}


@Composable
fun OrderSummarySection(
    subtotal: Double,
    taxes: Double,
    deliveryFee: Double,
    total: Double
) {
    Text(
        text = "Resumen del pedido",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Spacer(Modifier.height(8.dp))

    SummaryRow(label = "Subtotal", amount = "S/. ${"%.2f".format(subtotal)}")
    SummaryRow(label = "Impuestos", amount = "S/. ${"%.2f".format(taxes)}")
    SummaryRow(label = "Tarifa de entrea", amount = "S/. ${"%.2f".format(deliveryFee)}")

    Divider(modifier = Modifier.padding(vertical = 12.dp))

    SummaryRow(label = "Total:", amount = "S/. ${"%.2f".format(total)}", isTotal = true)

    Spacer(Modifier.height(4.dp))

    Text(
        text = "Tiempo estimado de entrega: 15 - 30mins",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SummaryRow(label: String, amount: String, isTotal: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (isTotal) MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold) else MaterialTheme.typography.bodyMedium,
            fontSize = 18.sp
        )
        Text(
            text = amount,
            style = if (isTotal) MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold) else MaterialTheme.typography.bodyMedium,
            fontSize = 18.sp,
        )
    }
}

@Composable
fun PaymentMethodCard(
    @DrawableRes brandLogo: Int,
    title: String,
    cardNumber: String,
    selected: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    contentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = brandLogo),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = contentColor)
            Text(text = cardNumber, color = contentColor.copy(alpha = 0.8f))
        }

        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = Color.White, unselectedColor = Color.Gray)
        )
    }
}
