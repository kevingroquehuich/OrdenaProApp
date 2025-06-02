package com.roque.ordenaproapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roque.domain.model.Order
import com.roque.ordenaproapp.ui.screens.orders.summary.SummaryRow
import com.roque.ordenaproapp.utils.PdfInvoiceGenerator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderItemCard(
    order: Order,
    onGenerateInvoice: (Order) -> Unit,
    onInvoiceView: (Order) -> Unit,
    onInvoiceShare: (Order) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Cliente: ${order.customerName}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Ver factura") },
                            onClick = {
                                expanded = false
                                onInvoiceView(order)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Compartir factura") },
                            onClick = {
                                expanded = false
                                onInvoiceShare(order)
                            }
                        )
                    }
                }
            }

            Text(
                text = "Fecha: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(order.date))}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            order.items.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(it.title, modifier = Modifier.weight(1f))
                    Text("x${it.quantity}")
                    Text("  S/. ${"%.2f".format(it.price)}")
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            SummaryRow(label = "Subtotal", amount = "S/. ${"%.2f".format(order.subtotal)}")
            SummaryRow(label = "Impuestos", amount = "S/. ${"%.2f".format(order.taxes)}")
            SummaryRow(label = "Envío", amount = "S/. ${"%.2f".format(order.deliveryFee)}")
            SummaryRow(
                label = "Total",
                amount = "S/. ${"%.2f".format(order.total)}",
                isTotal = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onGenerateInvoice(order) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B30))
                ) {
                    Icon(Icons.Default.PictureAsPdf, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Generar Factura")
                }
            }
        }
    }
}

