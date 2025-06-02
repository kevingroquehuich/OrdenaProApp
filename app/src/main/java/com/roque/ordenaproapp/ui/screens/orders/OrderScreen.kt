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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
fun OrderSummaryScreen(
    selectedPayment: String,
    onSelectPayment: (String) -> Unit,
    saveCard: Boolean,
    onSaveCardToggle: (Boolean) -> Unit
) {
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
                modifier = Modifier.clickable { }
            )

            Spacer(Modifier.height(32.dp))

            OrderSummarySection()

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
                Text("Save card details for future payments")
            }
        }

        Spacer(Modifier.height(16.dp))

        CheckoutBar(
            totalPrice = 100.0,
            textButton = "Confirmar Pedido",
            colorButton = 0xFF3C2E25,
            onPayClick = {  }
        )
    }
}


@Composable
fun OrderSummarySection() {
    Text(
        text = "Order summary",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Spacer(Modifier.height(8.dp))

    SummaryRow(label = "Order", amount = "$16.48")
    SummaryRow(label = "Taxes", amount = "$0.3")
    SummaryRow(label = "Delivery fees", amount = "$1.5")

    Divider(modifier = Modifier.padding(vertical = 12.dp))

    SummaryRow(label = "Total:", amount = "$18.19", isTotal = true)

    Spacer(Modifier.height(4.dp))

    Text(
        text = "Estimated delivery time: 15 - 30mins",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SummaryRow(label: String, amount: String, isTotal: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
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
