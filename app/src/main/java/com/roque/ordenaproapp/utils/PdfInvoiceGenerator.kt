package com.roque.ordenaproapp.utils

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.roque.domain.model.Order
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object PdfInvoiceGenerator {

    fun generateInvoicePdf(context: Context, order: Order): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        val paint = Paint()
        val titlePaint = Paint().apply {
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textSize = 24f
            color = Color.BLACK
        }

        val subtitlePaint = Paint().apply {
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            textSize = 16f
            color = Color.DKGRAY
        }

        val linePaint = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 2f
        }

        var y = 60

        // Title
        canvas.drawText("Factura de OrdenaPro", 40f, y.toFloat(), titlePaint)
        y += 40
        canvas.drawLine(40f, y.toFloat(), 555f, y.toFloat(), linePaint)
        y += 20

        // Order info
        canvas.drawText("Cliente: ${order.customerName}", 40f, y.toFloat(), subtitlePaint)
        y += 25
        canvas.drawText("Fecha: ${formatDate(order.date)}", 40f, y.toFloat(), subtitlePaint)
        y += 25
        canvas.drawText("ID Orden: ${order.id}", 40f, y.toFloat(), subtitlePaint)
        y += 30
        canvas.drawLine(40f, y.toFloat(), 555f, y.toFloat(), linePaint)
        y += 30

        // Table headers
        subtitlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("Producto", 40f, y.toFloat(), subtitlePaint)
        canvas.drawText("Cant.", 250f, y.toFloat(), subtitlePaint)
        canvas.drawText("Precio", 320f, y.toFloat(), subtitlePaint)
        canvas.drawText("Total", 450f, y.toFloat(), subtitlePaint)
        y += 20
        canvas.drawLine(40f, y.toFloat(), 555f, y.toFloat(), linePaint)
        y += 25

        // Items
        subtitlePaint.typeface = Typeface.DEFAULT
        order.items.forEach { item ->
            if (y > 750) return@forEach // simple overflow guard
            canvas.drawText(item.title.take(30), 40f, y.toFloat(), subtitlePaint)
            canvas.drawText("${item.quantity}", 250f, y.toFloat(), subtitlePaint)
            canvas.drawText("S/. ${"%.2f".format(item.price)}", 320f, y.toFloat(), subtitlePaint)
            val totalPrice = item.quantity * item.price
            canvas.drawText("S/. ${"%.2f".format(totalPrice)}", 450f, y.toFloat(), subtitlePaint)
            y += 25
        }

        y += 15
        canvas.drawLine(40f, y.toFloat(), 555f, y.toFloat(), linePaint)
        y += 25

        // Totals
        titlePaint.textSize = 18f
        canvas.drawText("Subtotal:", 350f, y.toFloat(), titlePaint)
        canvas.drawText("S/. ${"%.2f".format(order.subtotal)}", 480f, y.toFloat(), titlePaint)
        y += 25
        canvas.drawText("Impuestos:", 350f, y.toFloat(), titlePaint)
        canvas.drawText("S/. ${"%.2f".format(order.taxes)}", 480f, y.toFloat(), titlePaint)
        y += 25
        canvas.drawText("Delivery:", 350f, y.toFloat(), titlePaint)
        canvas.drawText("S/. ${"%.2f".format(order.deliveryFee)}", 480f, y.toFloat(), titlePaint)
        y += 25
        canvas.drawLine(350f, y.toFloat(), 555f, y.toFloat(), linePaint)
        y += 30

        titlePaint.textSize = 20f
        canvas.drawText("TOTAL:", 350f, y.toFloat(), titlePaint)
        canvas.drawText("S/. ${"%.2f".format(order.total)}", 480f, y.toFloat(), titlePaint)

        document.finishPage(page)

        // Guardar archivo
        val fileName = "invoice_${order.id.ifBlank { System.currentTimeMillis() }}.pdf"
        val file = File(context.cacheDir, fileName)
        document.writeTo(FileOutputStream(file))
        document.close()

        return file
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}
