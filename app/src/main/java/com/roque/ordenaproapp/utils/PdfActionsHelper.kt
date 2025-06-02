package com.roque.ordenaproapp.utils

import android.content.Context
import com.roque.domain.model.Order
import java.io.File

interface PdfActionsHelper {
    fun generatePdf(context: Context, order: Order): File
    fun openPdf(context: Context, file: File)
    fun sharePdf(context: Context, file: File)
}