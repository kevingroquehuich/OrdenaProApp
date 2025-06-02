package com.roque.ordenaproapp.utils


import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.roque.domain.model.Order
import java.io.File

class PdfActionsHelperImpl : PdfActionsHelper {

    override fun generatePdf(context: Context, order: Order): File {
        return PdfInvoiceGenerator.generateInvoicePdf(context, order)
    }

    override fun openPdf(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)
    }

    override fun sharePdf(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Compartir factura"))
    }
}